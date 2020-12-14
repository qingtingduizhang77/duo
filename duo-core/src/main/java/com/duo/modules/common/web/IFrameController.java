package com.duo.modules.common.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.BaseController;
import com.duo.core.SpringContextHolder;
import com.duo.core.utils.*;
import com.duo.modules.common.service.CommonQueryService;
import com.duo.modules.system.entity.SystemAttachment;
import com.duo.modules.system.entity.WfData;
import com.duo.modules.system.entity.WfDataNodeuser;
import com.duo.modules.tool.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description： EasyUI支持
 * @author [ Yonsin ] on [2019年1月31日上午10:39:37]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
@Controller
@RequestMapping("/iframe")
public class IFrameController extends BaseController{
    @Autowired
    private CommonQueryService commonQueryService;
    //在配置文件中配置的文件保存路径
    private static SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormatProperties");


    @GetMapping
    @RequestMapping("/form-window")
    public String formWindow(Model model,HttpServletRequest request,@RequestParam("funId") String funId,  @RequestParam(value="pageType",defaultValue = "form") String pageType,
                             @RequestParam(value="height",defaultValue = "640") String height) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String DataId=(String)requestMap.get("DataId");
        //当前用户信息
        model.addAttribute("CURRENTUSERNAME", ShiroUtils.getUserName());
        model.addAttribute("CURRENTUSERID",ShiroUtils.getUserId());
        model.addAttribute("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        model.addAttribute("CURRENTDEPTID",ShiroUtils.getDeptId());
        model.addAttribute("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        model.addAttribute("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        model.addAttribute("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        model.addAttribute("CURRENTPROJECTID",ShiroUtils.getProjectId());
        model.addAttribute("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String  formUrl="";
        if(!StringUtils.isEmpty(funInfo.getForm_html())) formUrl="../project"+funInfo.getForm_html();
        model.addAttribute("formUrl", formUrl);//  form文件
        StringBuffer formeventHtml=new StringBuffer("");//Form事件按钮条
        //事件信息
        List<ToolFunctionEvent> listEvents=layoutService.getFunctionEvent(funId);
        for(ToolFunctionEvent event:listEvents){
            if(event.getIs_show().equals("1")&&(event.getPage_type().indexOf(";"+pageType+";")>-1)) //设置为显示，并且支持的页面类型含有当前pageType
                formeventHtml.append("<button  type='button' id='"+event.getEvent_id()+"' name='"+event.getEvent_code()+"' class='btn btn-default' "+(event.getBo_function()==null?"":"bo-function=\""+event.getBo_function()+"\" " )+
                        "  data-action=\"{selection:'"+event.getSelection_type()+"',audit:'"+event.getPermission_type()+"'}\"   "+(event.getDefault_disabled()==null?"":event.getDefault_disabled())+ " onclick=\""+event.getJs_function()+"\"><i class='"+event.getIco_img()+"'></i> "+event.getEvent_name()+" </button>");

        }
        model.addAttribute("eventFormBar",formeventHtml.toString());//Form事件按钮条
        requestMap.put("DataId",DataId);
        model.addAttribute("rowJson", JSONObject.toJSONString(commonQueryService.findBySQLRow(funId,requestMap)));
        return "iframe/form-window";
    }



    @GetMapping
    @RequestMapping("/check-window")
    public String checkWindow(Model model,HttpServletRequest request,@RequestParam("funId") String funId) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String dataId=(String)requestMap.get("DataId");
        String wfID=(String)requestMap.get("wfID");
        String wfUserid=(String)requestMap.get("wfUserid");
        String isView=(String)requestMap.get("isView");
        model.addAttribute("isView", StringUtils.emptyToDefault(isView,"0"));//查询模式
        model.addAttribute("wfID", wfID);
        model.addAttribute("wfUserid", wfUserid);
        //当前用户信息
        model.addAttribute("CURRENTUSERNAME", ShiroUtils.getUserName());
        model.addAttribute("CURRENTUSERID",ShiroUtils.getUserId());
        model.addAttribute("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        model.addAttribute("CURRENTDEPTID",ShiroUtils.getDeptId());
        model.addAttribute("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        model.addAttribute("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        model.addAttribute("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        model.addAttribute("CURRENTPROJECTID",ShiroUtils.getProjectId());
        model.addAttribute("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String  formUrl="";
        if(!StringUtils.isEmpty(funInfo.getForm_html())) formUrl="../project"+funInfo.getForm_html();
        model.addAttribute("formUrl", formUrl);//  form文件
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        //获取附件列表
        Example example = new Example(SystemAttachment.class);
        example.setOrderByClause("add_date");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("data_id",dataId);
        List<SystemAttachment> filelist=layoutService.getMapper("SystemAttachment").selectByExample(example);
        StringBuffer filehtml=new StringBuffer("");
        int i=1;
        for(SystemAttachment file:filelist){
            filehtml.append("<tr><td>").append(i++).append("</td><td>").append(file.getFile_name()).append("</td><td>").append(file.getFile_size())
                    .append("</td><td>").append(DateUtils.formatDate(file.getAdd_date(),"yyyy-MM-dd HH:mm:ss"))
                    .append("</td><td>").append(file.getUpload_user()).append("</td><td><button  class='btn btn-primary' >下载</button></td></tr>");
        }
        model.addAttribute("checkfileList", filehtml.toString());
        //获取审批历史
        // 和已审批节点
        WfData query=new WfData();
        query.setData_id(dataId);
        WfData wfd=(WfData)layoutService.getMapper("WfData").selectOne(query);

        requestMap.put("DataId",wfd.getRecord_id());
        model.addAttribute("rowJson", JSON.toJSON(commonQueryService.findBySQLRow(funId,requestMap)));
        StringBuffer historyhtml = new StringBuffer("");
        StringBuffer historyNodehtml = new StringBuffer("[");
        Map<String,WfDataNodeuser> historyMp=new HashMap<String,WfDataNodeuser>();
        if(wfd!=null) {
            Example exampleh = new Example(WfDataNodeuser.class);
            exampleh.setOrderByClause("sign_date");
            Example.Criteria criteriah = exampleh.createCriteria();
            criteriah.andEqualTo("data_id", wfd.getData_id());
            List<WfDataNodeuser> historylist = layoutService.getMapper("WfDataNodeuser").selectByExample(exampleh);
            for (WfDataNodeuser wfu : historylist) {
                historyhtml.append("<tr><td>").append(wfu.getStep_no()).append("</td><td>").append(wfu.getNode_name())
                        .append("</td><td>").append(wfu.getUser_name()+(wfu.getIs_sponsor().equals("1")?"<br>(主办)":(wfu.getIs_operator().equals("1")?"<br>(经办)":"<br>(知会)")))
                        .append("</td><td>").append(wfu.getData_status()).append("</td><td>").append((wfu.getIs_agree()==null?"":wfu.getIs_agree().equals("1")?"<b>同意。<b>":"<span style='color:red;font-weight:600;'>不同意。</span>")+StringUtils.emptyToDefault(wfu.getSign_advise(),""))
                        .append("</td><td>").append(DateUtils.formatDate(wfu.getAdd_date(), "yyyy-MM-dd HH:mm:ss")).append("</td><td>").append(DateUtils.formatDate(wfu.getSign_date(), "yyyy-MM-dd HH:mm:ss"))
                        .append("</td><td>").append((DateUtils.getSecondOfTwoDate(wfu.getAdd_date(),wfu.getSign_date(),"-"))).append("</td></tr>");
                if(wfu.getIs_sponsor().equals("1")&&wfu.getData_status().equals("1")) historyMp.put(wfu.getNode_id(),wfu);
            }
            for (Map.Entry<String, WfDataNodeuser> entry : historyMp.entrySet()) {
                if(historyNodehtml.length()>10)historyNodehtml.append(",");
                WfDataNodeuser u=entry.getValue();
                historyNodehtml.append("{node_id:'"+u.getNode_id()+"',node_name:'"+u.getNode_name()+"',user_name:'"+u.getUser_name()+"',user_id:'"+u.getUser_id()+"'}");
            }
        }
        historyNodehtml.append("]");
        model.addAttribute("checkhistoryList", historyhtml.toString());
        model.addAttribute("historynodes", historyNodehtml.toString());
        model.addAttribute("WfData", wfd);
        //获取下一步审批节点
        // 和默认审批人
        List<Map<String,Object>> nextList=new ArrayList<Map<String,Object>>();
        WfDataNodeuser myNodeData=(WfDataNodeuser)layoutService.getMapper("WfDataNodeuser").selectByPrimaryKey(wfUserid);
        model.addAttribute("WfDataUser", myNodeData);
        Map userMp=new HashMap();
        if(myNodeData!=null) {
            Example examplenode = new Example(ToolWorkflowLine.class);
            Example.Criteria criterianode = examplenode.createCriteria();
            criterianode.andEqualTo("start_node",myNodeData.getNode_no());
            criterianode.andEqualTo("wf_id",myNodeData.getWf_id());
            List<ToolWorkflowLine> nextnodelist = layoutService.getMapper("ToolWorkflowLine").selectByExample(examplenode);
            for(ToolWorkflowLine line:nextnodelist){
                Map<String,Object> mp=new HashMap<String,Object>();
                Example examplenode2 = new Example(ToolWorkflowNode.class);
                Example.Criteria criterianode2 = examplenode2.createCriteria();
                criterianode2.andEqualTo("node_no",line.getEnd_node());
                criterianode2.andEqualTo("wf_id",myNodeData.getWf_id());
                ToolWorkflowNode lnode=(ToolWorkflowNode)layoutService.getMapper("ToolWorkflowNode").selectOneByExample(examplenode2);
                if(lnode==null) continue;
                Example exampleuser = new Example(ToolWorkflowLineUser.class);
                exampleuser.setOrderByClause("order_index desc");
                Example.Criteria criteriauser = exampleuser.createCriteria();
                criteriauser.andEqualTo("line_id",line.getLine_id());
                //根据记录增加部门限制
//                criteriauser.andLike("dept_id",ShiroUtils.getDeptId()+"%");

                List<ToolWorkflowLineUser> nextuserlist = layoutService.getMapper("ToolWorkflowLineUser").selectByExample(exampleuser);
                mp.put("node_id",lnode.getNode_id());
                mp.put("node_no",lnode.getNode_no());
                mp.put("node_type",lnode.getNode_type());
                mp.put("node_name",lnode.getNode_name());
//                mp.put("users",nextuserlist);
                userMp.put(lnode.getNode_id(),nextuserlist);
                nextList.add(mp);
            }
        }
        model.addAttribute("nextNode", JSON.toJSON(nextList));
        model.addAttribute("nextNodeUsers", JSON.toJSON(userMp));
        DynamicDataSource.clearDataSource();

        return "iframe/check-window";
    }

    @GetMapping
    @RequestMapping("/open-window")
    public String openWindow(Model model, HttpServletRequest request, @RequestParam String openurl,
                             @RequestParam(value="title",defaultValue = "弹出窗口") String title,
                             @RequestParam(value="foreignColumn",defaultValue = "") String foreignColumn,
                             @RequestParam(value="foreignKeyId",defaultValue = "") String foreignKeyId,
                             @RequestParam(value="pageType",defaultValue = "grid") String pageType) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String orgSQL=(String)requestMap.get("orgSQL");
        model.addAttribute("openurl", openurl);
        model.addAttribute("orgSQL", orgSQL);
        if(openurl.indexOf("funId=")>0) {
            System.out.println(openurl);
            String funId = openurl.split("funId=")[1].split("&")[0];
            System.out.println(funId);
            ToolFunction funInfo = layoutService.getFunctionInfo(funId);
            title=funInfo.getFun_name();
        }
        model.addAttribute("title", title);
        model.addAttribute("foreignColumn", foreignColumn);
        model.addAttribute("pageType", pageType);
        model.addAttribute("foreignKeyId", foreignKeyId);
        //当前用户信息
        model.addAttribute("CURRENTUSERNAME",ShiroUtils.getUserName());
        model.addAttribute("CURRENTUSERID",ShiroUtils.getUserId());
        model.addAttribute("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        model.addAttribute("CURRENTDEPTID",ShiroUtils.getDeptId());
        model.addAttribute("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        model.addAttribute("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        model.addAttribute("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        model.addAttribute("CURRENTPROJECTID",ShiroUtils.getProjectId());
        model.addAttribute("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        return "/iframe/open-window";
    }

    @GetMapping
    @RequestMapping("/select-window")
    public String selectWindow(Model model,HttpServletRequest request, @RequestParam String url,
                               @RequestParam(value="sourceCols",defaultValue = "") String sourceCols,
                               @RequestParam(value="targetCols",defaultValue = "") String targetCols,
                               @RequestParam(value="height",defaultValue = "640") String height) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        model.addAttribute("url", url);
        model.addAttribute("sourceCols", sourceCols);
        model.addAttribute("foreignKeyId", targetCols);
        model.addAttribute("height", height);
        String orgSQL=(String)requestMap.get("orgSQL");
        model.addAttribute("orgSQL", orgSQL);
        //当前用户信息
        model.addAttribute("CURRENTUSERNAME",ShiroUtils.getUserName());
        model.addAttribute("CURRENTUSERID",ShiroUtils.getUserId());
        model.addAttribute("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        model.addAttribute("CURRENTDEPTID",ShiroUtils.getDeptId());
        model.addAttribute("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        model.addAttribute("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        model.addAttribute("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        model.addAttribute("CURRENTPROJECTID",ShiroUtils.getProjectId());
        model.addAttribute("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        log.info("url===="+url);
        log.info("height===="+height);
        log.info("cols =="+sourceCols+"=="+targetCols);
        return "iframe/select-window";
    }

    @GetMapping
    @RequestMapping("/select-ico-window")
    public String selectIcoWindow(Model model, @RequestParam  String tableName,
                                  @RequestParam String dataID,
                                  @RequestParam String columnName) {
        model.addAttribute("tableName", tableName);
        model.addAttribute("dataID", dataID);
        model.addAttribute("columnName", columnName);
        log.info("tableName===="+tableName);
        log.info("dataID===="+dataID);
        log.info("columnName =="+columnName );
        return "iframe/select-ico-window";
    }
    @GetMapping
    @RequestMapping("/import-window")
    public String importWindow(Model model, HttpServletRequest request ,
                               @RequestParam(value="layoutURL",defaultValue = "/layout/layout-common-grid") String layoutURL,
                               @RequestParam(value="height",defaultValue = "640") String height) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String sourceFunID=(String)requestMap.get("sourceFunID");
        String targetFunID=(String)requestMap.get("targetFunID");
        String orgSQL=(String)requestMap.get("orgSQL");
        String selectId=(String)requestMap.get("selectId");
        log.info("orgSQL===="+orgSQL);
        String pageType=(String)requestMap.get("pageType");
        String importforeignKeyId=(String)requestMap.get("importforeignKeyId");

        model.addAttribute("importforeignKeyId", importforeignKeyId);
        model.addAttribute("layoutURL", layoutURL);
        model.addAttribute("sourceFunID", sourceFunID);
        if(sourceFunID.indexOf(";")>-1){
            String sourceFunName="";
            for(String funid:sourceFunID.split(";")){
                sourceFunName=sourceFunName+";"+layoutService.getFunctionInfo(funid).getFun_name();
            }
            sourceFunName=sourceFunName.substring(1);
            model.addAttribute("sourceFunName", sourceFunName);
        }
        model.addAttribute("targetFunID", targetFunID);
        model.addAttribute("orgSQL", orgSQL);
        model.addAttribute("selectId", selectId);
        model.addAttribute("pageType", pageType);
        model.addAttribute("height", height);
        //当前用户信息
        model.addAttribute("CURRENTUSERNAME",ShiroUtils.getUserName());
        model.addAttribute("CURRENTUSERID",ShiroUtils.getUserId());
        model.addAttribute("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        model.addAttribute("CURRENTDEPTID",ShiroUtils.getDeptId());
        model.addAttribute("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        model.addAttribute("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        model.addAttribute("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        model.addAttribute("CURRENTPROJECTID",ShiroUtils.getProjectId());
        model.addAttribute("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        log.info("layoutURL===="+layoutURL);
        log.info("orgSQL===="+orgSQL);
        log.info("height===="+height);
        log.info("importforeignKeyId===="+importforeignKeyId);
        log.info("sourceFunID  targetFunID =="+sourceFunID+"=="+targetFunID);
        return "iframe/import-window";
    }

    @GetMapping
    @RequestMapping("/subfun-window")
    public String subfunWindow(Model model, @RequestParam String url,
                               @RequestParam(value="foreignColumn",defaultValue = "") String foreignColumn,
                               @RequestParam(value="foreignKeyId",defaultValue = "") String foreignKeyId,
                               @RequestParam(value="height",defaultValue = "640") String height,
                               @RequestParam(value="mainValide",defaultValue = "true") String mainValide) {
        model.addAttribute("url", url);
        model.addAttribute("foreignColumn", foreignColumn);
        model.addAttribute("foreignKeyId", foreignKeyId);
        model.addAttribute("height", height);
        model.addAttribute("mainValide", mainValide);
        //当前用户信息
        model.addAttribute("CURRENTUSERNAME",ShiroUtils.getUserName());
        model.addAttribute("CURRENTUSERID",ShiroUtils.getUserId());
        model.addAttribute("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        model.addAttribute("CURRENTDEPTID",ShiroUtils.getDeptId());
        model.addAttribute("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        model.addAttribute("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        model.addAttribute("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        model.addAttribute("CURRENTPROJECTID",ShiroUtils.getProjectId());
        model.addAttribute("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        log.info("url===="+url);
        log.info("height===="+height);
        log.info("mainValide===="+mainValide);
        log.info("foreign =="+foreignColumn+"=="+foreignKeyId);
        return "iframe/subfun-window";
    }


    @GetMapping
    @RequestMapping("/chart-window")
    public String chartWindow(Model model,HttpServletRequest request, @RequestParam String chartID,
                               @RequestParam(value="height",defaultValue = "640") String height) {
        model.addAttribute("chartID", chartID);
        model.addAttribute("height", height);
        //当前用户信息
        model.addAttribute("CURRENTUSERNAME",ShiroUtils.getUserName());
        model.addAttribute("CURRENTUSERID",ShiroUtils.getUserId());
        model.addAttribute("CURRENTDEPTNAME",ShiroUtils.getDeptName());
        model.addAttribute("CURRENTDEPTID",ShiroUtils.getDeptId());
        model.addAttribute("CURRENTCOMPANYID",ShiroUtils.getCompanyId());
        model.addAttribute("CURRENTONEDEPTID",ShiroUtils.getOneDeptId());
        model.addAttribute("CURRENTTWODEPTID",ShiroUtils.getTwoDeptId());
        model.addAttribute("CURRENTPROJECTID",ShiroUtils.getProjectId());
        model.addAttribute("CURRENTSYSTEMID",ShiroUtils.getSystemId());

        log.info("chartID===="+chartID);
        return "iframe/chart-window";
    }



    @GetMapping
    @RequestMapping("/codeeditor-window")
    public String codeEditorWindow(Model model,HttpServletRequest request,
                                   @RequestParam(value="mainDIR",defaultValue = "") String mainDir, @RequestParam String filepath) {
        //系统目录
        if(mainDir.equals("SYSTEMDIR")) mainDir= MemCache.getSystemParameter("createDIR");//System.getProperty("user.dir");
        //公共文件目录
        if(mainDir.equals("DUODIR")) mainDir=sqlprop.getFilePath();
        filepath=mainDir+filepath;

//        log.info(filebody.toString());
        model.addAttribute("filepath",filepath);
        return "iframe/codeeditor-window";
    }


}
