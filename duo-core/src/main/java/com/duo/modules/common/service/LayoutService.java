package com.duo.modules.common.service;

import com.alibaba.fastjson.JSON;
import com.duo.DataSourceNames;
import com.duo.MemCache;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.annotation.DataSource;
import com.duo.core.utils.*;
import com.duo.modules.system.entity.SystemDept;
import com.duo.modules.system.entity.SystemMajorfunc;
import com.duo.modules.system.entity.SystemUser;
import com.duo.modules.system.mapper.*;
import com.duo.modules.tool.entity.*;
import com.duo.modules.tool.mapper.*;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author [ Yonsin ] on [2019/8/26]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Service
public class LayoutService extends BaseService {
    @Autowired
    private SystemDeptMapper systemDeptMapper;
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private ToolModuleMapper toolModuleMapper;
    @Autowired
    private ToolFunctionMapper toolFunctionMapper;
    @Autowired
    private ToolSystemModuleMapper toolSystemModuleMapper;
    @Autowired
    private ToolSystemFunctionMapper toolSystemFunctionMapper;
    @Autowired
    private ToolFunctionColumnMapper toolFunctionColumnMapper;
    @Autowired
    private ToolFunctionEventMapper toolFunctionEventMapper;
    @Autowired
    private ToolFunctionQueryMapper toolFunctionQueryMapper;
    @Autowired
    private ToolFunctionQueryColumnMapper toolFunctionQueryColumnMapper;

    @Autowired
    public SystemMajorfuncMapper systemMajorfuncMapper;
    @Autowired
    private SystemRoleUserMapper systemRoleUserMapper;
    @Autowired
    private SystemRoleFunMapper systemRoleFunMapper;

    public static ConcurrentHashMap<String ,Class> _funInfos=new ConcurrentHashMap<String ,Class>();//存储function配置信息

    /**
     * 查找功能定义信息到通用模版，初始化页面
     * @param model
     * @param funId
     * @return
     */
    public boolean initFuntionInfo(Model model, HttpServletRequest request, String funId, String pageType){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String foreignColumn=(String)requestMap.get("foreignColumn");
        String foreignKeyId=(String)requestMap.get("foreignKeyId");
        String targetFunID=(String)requestMap.get("targetFunID");
        String mainValide=(String)requestMap.get("mainValide");
        if(StringUtils.isEmpty(mainValide)) {
            if (pageType.equals("subgrid")) mainValide = "false";
            else mainValide = "true";
        }
        String orgSQL=(String)requestMap.get("orgSQL");
        String importforeignKeyId=(String)requestMap.get("importforeignKeyId");
        logdb.info("funId============"+funId+"\n"
                +"pageType============"+pageType+"\n"
                +"mainValide============"+mainValide+"\n"
                +"foreignKeyId============"+foreignKeyId+"\n"
                +"foreignColumn============"+foreignColumn+"\n"
                +"targetFunID============"+targetFunID+"\n"
                +"importforeignKeyId============"+importforeignKeyId);
        //常用功能访问次数记录
        if(pageType.equals("grid")||pageType.equals("total")) {
            SystemMajorfunc record = new SystemMajorfunc();
            record.setFun_id(funId);
            record.setMemo(ShiroUtils.getSystemId());
            record.setUser_id(ShiroUtils.getUserId());
            SystemMajorfunc  dbrecord=systemMajorfuncMapper.selectOne(record);
            if(dbrecord==null){
                record.setMajor_id(getUUID());
                record.setHit_num(1);
                systemMajorfuncMapper.insertSelective(record);
            }else{
                dbrecord.setHit_num(dbrecord.getHit_num()+1);
                systemMajorfuncMapper.updateByPrimaryKey(dbrecord);
            }
        }
        String userId=ShiroUtils.getUserId();
        //判断是否有权限，防止直接url链接进入功能
        if(!MemCache._userIsAdmin.containsKey(userId)){
            cacheRoleInfo();
        }
        boolean isAdmin=MemCache._userIsAdmin.get(userId);
        if(!isAdmin) {//超级管理员不需要判断
            if (pageType.equals("grid") || pageType.equals("form") || pageType.equals("total")) {//仅对grid页面类型，form页面类型，total页面类型判断
                if (!MemCache._userIsAdmin.get(userId) &&(!MemCache._userRoles.get(userId).containsKey(funId)||
                        !"1".equals((String) MemCache._userRoles.get(userId).get(funId).get("is_access")))) {//不是系统管理员，也没有该功能权限则跳过
                    model.addAttribute("error", "无该功能访问权限，请联系管理员！");
                    return false;
                }
            }
        }

        ToolFunction funInfo=getFunctionInfo(funId);
        if(funInfo==null){//无定义功能
            model.addAttribute("error", "功能不存在，请联系管理员！");
            return false;
        }
        //是否自定义了外键
        if (funInfo!=null&&StringUtils.isNotEmpty(funInfo.getForeign_column())) {
            foreignColumn=funInfo.getForeign_column();
        }
        model.addAttribute("importforeignKeyId", importforeignKeyId);
        model.addAttribute("foreignColumn", foreignColumn);
        model.addAttribute("foreignKeyId", foreignKeyId);
        model.addAttribute("targetFunID", targetFunID);
        model.addAttribute("funId", funId);
        model.addAttribute("orgSQL", orgSQL);//orgSQL

        String allowEidtRole="false";//是否可以编辑
        List<ToolSystemModule> moduleList=getSystemModuleFromFun(funInfo.getModule_id());
        if(moduleList!=null&&moduleList.size()==2) {
            model.addAttribute("topModuleId", moduleList.get(0).getSystem_id());//一级模块名称
            model.addAttribute("topModuleName", moduleList.get(0).getSystem_name());//一级模块名称
            model.addAttribute("moduleId", moduleList.get(1).getSystem_id());//模块名称
            model.addAttribute("moduleName", moduleList.get(1).getSystem_name());//模块名称
        }
        model.addAttribute("funName", funInfo.getFun_name());//功能名称
        model.addAttribute("funDescript", funInfo.getFun_descript());//功能描述
        model.addAttribute("keyIDColumn", funInfo.getKey_column()==null?"":funInfo.getKey_column().indexOf(".")>0?funInfo.getKey_column().split(".")[1]:funInfo.getKey_column());//keyIDColumn
        model.addAttribute("getDataUrl", funInfo.getQuery_function());//bootstrapTable getData url
        model.addAttribute("getTreeDataUrl", funInfo.getTree_function());//bootstrapTable getTreeData url
        model.addAttribute("treeForwordWhere", funInfo.getTree_forwordwhere());//bootstrapTable treeForwordWhere
        model.addAttribute("treeName", funInfo.getTree_name());//树名
        model.addAttribute("treeTopId", funInfo.getTree_topid());//顶级id
        model.addAttribute("treeWhereSQL", funInfo.getTree_wheresql());//树过滤sql
        model.addAttribute("auditColumn", funInfo.getAudit_column());//复核字段
        model.addAttribute("funParames", funInfo.getFun_parames());//其他自定义参数
        //导入自定义的js
        String  formUrl="",
                gridUrl="",
                defineJsUrl="",
                defineModalUrl="";
        if(!StringUtils.isEmpty(funInfo.getForm_html())) formUrl="../project"+funInfo.getForm_html();
        if(!StringUtils.isEmpty(funInfo.getGrid_html())) gridUrl="../project"+funInfo.getGrid_html();
        if(!StringUtils.isEmpty(funInfo.getDefine_js())) defineJsUrl="../project"+funInfo.getDefine_js();
        if(!StringUtils.isEmpty(funInfo.getDefine_modal())) defineModalUrl="../project"+funInfo.getDefine_modal();
        model.addAttribute("formUrl", formUrl);//  form文件
        model.addAttribute("gridUrl", gridUrl);// grid文件
        model.addAttribute("defineJsUrl", defineJsUrl);//自定义js文件
        model.addAttribute("defineModalUrl", defineModalUrl);//自定义Modal文件
        //查询条
        if(!MemCache._queryInfos.containsKey(funId)){
            List<ToolFunctionQuery> queryList=getFunctionQuery(funId);
            StringBuffer querySelect=new StringBuffer("");
            StringBuffer queryBar=new StringBuffer("");
            for(int i=0,n=queryList.size();i<n;i++){
                ToolFunctionQuery query=queryList.get(i);
                if(i==0){
                    model.addAttribute("defaultQueryId", query.getQuery_id());//defaultQueryId
                    MemCache._queryInfos.put(funId+"::queryid", query.getQuery_id());//存入缓存
                }
                if(n>1) querySelect.append("<option value=\"").append(query.getQuery_id()).append("\">").append(query.getQuery_name()).append("</option>");

                queryBar.append("<div query-id='query_"+query.getQuery_id()+"'  "+(i==0?"":"style='display:none;'")+">");
                //获取字段清单
                List<ToolFunctionQueryColumn> columnList=getFunctionQueryColumn(query.getQuery_id());
                for(ToolFunctionQueryColumn column:columnList){
                    String hide="style='display:none;'";
                    if(column.getIs_show().equals("1")) hide="";
                    queryBar.append("<label "+hide+"> "+column.getColumn_name()+"</label>");
                    String controlType=column.getColumn_type();
                    if(controlType.equals("combobox")){//下拉
                        queryBar.append("  <select class=\"form-control input-sm\" name=\""+column.getColumn_code()+"\"   default-data=\""+StringUtils.emptyToDefault(column.getColumn_default(),"")+"\" "+hide+" >");
                        queryBar.append("<script type=\"text/javascript\"> " +
                                " var optionJson= "+column.getControl_name()+ ";" +
                                " $('select[name=\"" + column.getColumn_code() + "\"]').append(\"<option value='' selected>---请选择---</option>\"); " +
                                "for(var key in optionJson)  $('select[name=\"" + column.getColumn_code() + "\"]').append(\"<option value='\"+key+\"' >\"+optionJson[key]+\"</option>\"); " +
                                "</script> ");
                        queryBar.append(" </select>");
                    }else if(controlType.equals("selectwindow")){//选择窗口
                        queryBar.append("<input type=\"text\" class=\"form-control input-sm\" readonly name=\""+column.getColumn_code()+"\"   default-data=\""+StringUtils.emptyToDefault(column.getColumn_default(),"")+"\"  placeholder=\""+column.getPlace_holder()+"\"  "+hide+"   onclick='formSelectWindow(\""+column.getControl_name()+"\",\""+column.getControl_source()+"\",\""+column.getControl_target()+"\")'/>");
                        queryBar.append("<button type='button'  class='form-control' style='width:30px; ' onclick='formSelectWindow(\""+column.getControl_name()+"\",\""+column.getControl_source()+"\",\""+column.getControl_target()+"\",\""+column.getControl_where()+"\")'  "+hide+"  >...</button>");
                    }else if(controlType.equals("checkbox")){//复选框
                        queryBar.append("<input type=\"checkbox\"  default-data=\""+StringUtils.emptyToDefault(column.getColumn_default(),"")+"\"  name=\"" + column.getColumn_code() + "\" />");
                    }else if(controlType.equals("date")|| controlType.equals("time")||controlType.equals("timeminute")||
                            controlType.equals("year")||controlType.equals("yearmonth")){//日期类
                        String dateFmt= "yyyy-MM-dd HH:mm:ss";
                        if(controlType.equals("date")) dateFmt="yyyy-MM-dd";
                        else if(controlType.equals("time")) dateFmt="HH:mm:ss";
                        else if(controlType.equals("timeminute")) dateFmt="HH:mm";
                        else if(controlType.equals("year")) dateFmt="yyyy";
                        else if(controlType.equals("yearmonth")) dateFmt="yyyy-MM";
                        queryBar.append(" <input type=\"text\" class=\"form-control input-sm \" name=\""+column.getColumn_code()+"\"  default-data=\""+StringUtils.emptyToDefault(column.getColumn_default(),"")+"\" onfocus=\"WdatePicker({dateFmt:'"+dateFmt+"', autoPickDate: true ,onpicked:function(){blur();},onpicking:function(dp){blur();},isShowClear:false,readOnly:true})\"  "+hide+" />");
                    }else if(controlType.equals("text")){//文本
                        queryBar.append(" <input type=\"text\" class=\"form-control input-sm\" name=\""+column.getColumn_code()+"\"   default-data=\""+StringUtils.emptyToDefault(column.getColumn_default(),"")+"\" placeholder=\""+column.getPlace_holder()+"\"  "+hide+" />");
                    }else if(controlType.equals("number")){//number
                        queryBar.append(" <input type=\"number\" class=\"form-control input-sm\" name=\""+column.getColumn_code()+"\"   default-data=\""+StringUtils.emptyToDefault(column.getColumn_default(),"")+"\" placeholder=\""+column.getPlace_holder()+"\"  "+hide+" />");
                    }else {//文本
                        queryBar.append(" <input type=\"text\" class=\"form-control input-sm\" name=\""+column.getColumn_code()+"\"  default-data=\""+StringUtils.emptyToDefault(column.getColumn_default(),"")+"\" placeholder=\""+column.getPlace_holder()+"\"  "+hide+" />");
                    }
                }
                queryBar.append("</div>");
            }
            MemCache._queryInfos.put(funId+"::select",querySelect.toString());//存入缓存
            MemCache._queryInfos.put(funId,queryBar.toString());//存入缓存
        }
        model.addAttribute("defaultQueryId", MemCache._queryInfos.get(funId+"::queryid"));// 获取缓存
        model.addAttribute("querySelect", MemCache._queryInfos.get(funId+"::select"));// 获取缓存
        model.addAttribute("queryBar", MemCache._queryInfos.get(funId));// 获取缓存
        if (!MemCache.getSystemParameter("queryBarCache").equals("true")) {//是否启用查询条缓存
            MemCache._queryInfos.remove(funId+"::queryid");
            MemCache._queryInfos.remove(funId+"::select");
            MemCache._queryInfos.remove(funId);
        }
        //权限
        Map<String,Object> roleMp= isAdmin?null:MemCache._userRoles.get(userId).get(funId);
        //事件信息
        List<ToolFunctionEvent> listEvents=getFunctionEvent(funId);
        StringBuffer eventHtml=new StringBuffer("");//事件按钮条
        StringBuffer formeventHtml=new StringBuffer("");//Form事件按钮条
        StringBuffer gridventHtml=new StringBuffer("");//Grid行内事件按钮
        for(ToolFunctionEvent event:listEvents){
            //判断是否有事件权限，没有则不显示
            if(!isAdmin) {//超级管理员不需要判断
                if ("edit".equals(event.getPermission_type()) && roleMp!=null&&!"1".equals(roleMp.get("event_edit"))) continue;
                if ("audit".equals(event.getPermission_type()) && roleMp!=null&&!"1".equals(roleMp.get("event_audit"))) continue;
                if ("delete".equals(event.getPermission_type()) && roleMp!=null&&!"1".equals(roleMp.get("event_delete"))) continue;
                if ("sign".equals(event.getPermission_type()) && roleMp!=null&&!"1".equals(roleMp.get("event_sign"))) continue;
                if ("print".equals(event.getPermission_type()) &&roleMp!=null&& !"1".equals(roleMp.get("event_print"))) continue;
            }

            if(event.getIs_show().equals("1")&&event.getPage_type().indexOf(";"+pageType+";")>-1) //设置为显示，并且支持的页面类型含有当前pageType
                eventHtml.append("<button  type='button' id='"+event.getEvent_id()+"' name='"+event.getEvent_code()+"' class='btn btn-default' "+(event.getBo_function()==null?"":"bo-function=\""+event.getBo_function()+"\" " )+
                        "  data-action=\"{selection:'"+event.getSelection_type()+"',audit:'"+event.getPermission_type()+"'}\"   "+(mainValide.equals("false") ? "default-disabled='3'":StringUtils.isEmpty(event.getDefault_disabled())?"default-disabled='0'":"default-disabled='1'")+"  "+(mainValide.equals("true")&&StringUtils.isEmpty(event.getDefault_disabled())?"":"disabled")+ " onclick=\""+event.getJs_function()+"\"><i class='"+event.getIco_img()+"'></i> "+event.getEvent_name()+" </button>");
            if(event.getIs_show().equals("1")&&(event.getPage_type().indexOf(";form;")>-1||event.getPage_type().indexOf(";subform;")>-1)) //设置为显示，并且支持的页面类型含有当前pageType
                formeventHtml.append("<button  type='button' id='"+event.getEvent_id()+"' name='"+event.getEvent_code()+"' class='btn btn-default' "+(event.getBo_function()==null?"":"bo-function=\""+event.getBo_function()+"\" " )+
                        "  data-action=\"{selection:'"+event.getSelection_type()+"',audit:'"+event.getPermission_type()+"'}\"   "+(mainValide.equals("false") ? "default-disabled='3'":StringUtils.isEmpty(event.getDefault_disabled())?"default-disabled='0'":"default-disabled='1'")+"  "+(mainValide.equals("true")&&StringUtils.isEmpty(event.getDefault_disabled())?"":"disabled")+ " onclick=\""+event.getJs_function()+"\"><i class='"+event.getIco_img()+"'></i> "+event.getEvent_name()+" </button>");
            if(event.getIs_show().equals("1")&&(event.getPage_type().indexOf(";gridbtn;")>-1)) //设置为显示，并且支持的页面类型含有当前pageType
                gridventHtml.append("<button  type='button' id='"+event.getEvent_id()+"' name='"+event.getEvent_code()+"' class='btn btn-default' "+(event.getBo_function()==null?"":"bo-function=\""+event.getBo_function()+"\" " )+
                        "  data-action=\"{audit:'"+event.getPermission_type()+"'}\"    onclick=\""+event.getJs_function()+"\"><i class='"+event.getIco_img()+"'></i> "+event.getEvent_name()+" </button>");

            if(event.getEvent_code().equals("gridsave")&&event.getPage_type().indexOf(";"+pageType+";")>-1) allowEidtRole="true";//有grid保存按钮权限
        }
        if(funInfo.getIs_audit()!=null&&funInfo.getIs_audit().equals("1")){//设置自动归档时显示
            eventHtml.append(" &nbsp;&nbsp; &nbsp;&nbsp; <span title=\"[已复核]状态数据自动归档\"><input th:type=\"checkbox\"/> 显示归档</span>");
        }
        model.addAttribute("eventBar",eventHtml.toString());//事件按钮条
        model.addAttribute("eventFormBar",formeventHtml.toString());//Form事件按钮条
        model.addAttribute("eventGridBtn",gridventHtml.toString());//grid行内事件按钮条

        model.addAttribute("allowEidtRole", allowEidtRole);//是否可以编辑
        //子功能
        String subs=funInfo.getSub_funs();
        StringBuffer subFuns=new StringBuffer("");
        if(funInfo.getLayout_url().indexOf("layout-common-tree-tabsubgrid")>0||funInfo.getLayout_url().indexOf("layout-common-tabgrid")>0){//layout-common-tree-tabsubgrid.html布局增加grid和form页面的tab
            subFuns.append(" <li  class='active'><a href=\"javascript:void(0);\"   onclick=\"funFormTab(this,'grid')\" >");
            subFuns.append(funInfo.getFun_name()+"列表");
            subFuns.append(" </a> </li>");
            if(StringUtils.isNotEmpty(funInfo.getForm_html())) {
                subFuns.append(" <li><a href=\"javascript:void(0);\"   onclick=\"funFormTab(this,'form')\" >");
                subFuns.append(funInfo.getFun_name() + "表单");
                subFuns.append(" </a> </li>");
            }
        }else if(funInfo.getLayout_url().indexOf("layout-common-charttab")>0){
            subFuns.append(" <li  class='active'><a href=\"javascript:void(0);\"   onclick=\"funFormTab(this,'grid')\" >");
            subFuns.append(funInfo.getFun_name()+"数据列表");
            subFuns.append(" </a> </li>");
            if(StringUtils.isNotEmpty(funInfo.getForm_html())) {
                subFuns.append(" <li><a href=\"javascript:void(0);\"   onclick=\"funFormTab(this,'chart')\" >");
                subFuns.append(funInfo.getFun_name() + "图表");
                subFuns.append(" </a> </li>");
            }
        }
        if(StringUtils.isNotEmpty(subs)){
            for(String subfunid:subs.split(";")){
                ToolFunction subFunInfo=getFunctionInfo(subfunid);
                if(subFunInfo!=null){
                    subFuns.append(" <li><a href=\"javascript:void(0);\"   onclick=\"funFormTab(this,'"+subFunInfo.getLayout_url()+"?funId="+subfunid+"')\" >");
                    subFuns.append(subFunInfo.getFun_name());
                    subFuns.append(" </a> </li>");
                }
            }
        }

        model.addAttribute("subFuns",subFuns.toString());//子功能li
        return true;
    }


    /**
     * 获取一级菜单
     *       （后续再考虑project的问题）
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolModule>  getMenuOne(){
        Example example = new Example(ToolModule.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("is_show","1"); //可见
        criteria.andEqualTo("module_level",1);//第一级
        List<ToolModule> list=toolModuleMapper.selectByExample(example);

        return list;
    }
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolSystemModule>  getSystemMenuOne(){
        Example example = new Example(ToolSystemModule.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("system_id",ShiroUtils.getSystemId()+"%"); //可见
        criteria.andEqualTo("is_show","1"); //可见
        criteria.andEqualTo("system_level",2);//第2级
        List<ToolSystemModule> list=toolSystemModuleMapper.selectByExample(example);

        return list;
    }

    /**
     * 根据一级模块获取二级模块信息
     * @param module_id
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolModule>  getMenuTwo(String module_id){
        Example example = new Example(ToolModule.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("is_show","1");//可见
        criteria.andEqualTo("module_level",2);//第一级
        criteria.andLike("module_id",module_id+"%");//子目录
        List<ToolModule> list=toolModuleMapper.selectByExample(example);

        return list;
    }
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolSystemModule>  getSystemMenuTwo(String module_id){
        Example example = new Example(ToolSystemModule.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("system_id",module_id+"%");//子目录
        criteria.andEqualTo("is_show","1"); //可见
        criteria.andEqualTo("system_level",3);//第2级
        List<ToolSystemModule> list=toolSystemModuleMapper.selectByExample(example);

        return list;
    }

    /**
     * 根据功能所属模块获取一、二级模块
     * @param module_id
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolModule>  getModuleFromFun(String module_id){
        Example example = new Example(ToolModule.class);
        example.setOrderByClause("module_id");
        Example.Criteria criteria = example.createCriteria();
        List<Object> inlist=new ArrayList<Object>();
        inlist.add(module_id);//功能所在模块级及上级
        inlist.add(module_id.substring(0,module_id.length()-4));
        criteria.andIn("module_id",inlist);
        List<ToolModule> list=toolModuleMapper.selectByExample(example);

        return list;
    }



    /**
     * 根据功能所属模块获取一、二级模块
     * @param module_id
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolSystemModule>  getSystemModuleFromFun(String module_id){
        Example example = new Example(ToolSystemModule.class);
        example.setOrderByClause("system_id");
        Example.Criteria criteria = example.createCriteria();
        List<Object> inlist=new ArrayList<Object>();
        inlist.add(module_id);//功能所在模块级及上级
        inlist.add(module_id.substring(0,module_id.length()-4));
        criteria.andIn("system_id",inlist);
        List<ToolSystemModule> list=toolSystemModuleMapper.selectByExample(example);

        return list;
    }

    /**
     * 根据模块获取功能
     * @param module_id
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<Map<String,Object>>  getFunctions(String module_id) throws Exception {
        Example example = new Example(ToolSystemFunction.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("is_show","1");//可见
        criteria.andEqualTo("system_id",module_id);//子目录
//        List<Object> inlist=new ArrayList<Object>();
//        inlist.add("main");
//        inlist.add("maintree");
//        criteria.andIn("fun_type",inlist );//主功能


        List<ToolSystemFunction> list=toolSystemFunctionMapper.selectByExample(example);
        List<Map<String,Object>> newlist=new ArrayList<Map<String,Object>>();
        for(ToolSystemFunction sf:list){
            ToolFunction funInfo=toolFunctionMapper.selectByPrimaryKey(sf.getFun_id());
            if(funInfo!=null&&(funInfo.getFun_type().equals("main")||funInfo.getFun_type().equals("maintree"))){
                if(StringUtils.isNotEmpty(sf.getFun_name())) funInfo.setFun_name(sf.getFun_name());
                if(StringUtils.isNotEmpty(sf.getIco_img())) funInfo.setIco_img(sf.getIco_img());
                if(StringUtils.isNotEmpty(sf.getDb_source())) funInfo.setDb_source(sf.getDb_source());
                if(StringUtils.isNotEmpty(sf.getUrl_parames())&&sf.getUrl_parames().indexOf("{")>-1) {
                    Map<String,String> parames= JSON.parseObject(sf.getUrl_parames(),Map.class);
                    funInfo= Map2EntityUtil.updateModel(funInfo, parames);
                };
                Map<String,Object> mp=E2MapUtil.E2Map(funInfo,ToolFunction.class);
                mp.put("fun_id",sf.getSystem_funid());
                mp.put("sfun_id",sf.getFun_id());
                newlist.add(mp);
            }
        }

        return newlist;
    }

    /**
     * 获取功能信息
     * @param funId
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public ToolFunction getFunctionInfo(String funId){
        if(StringUtils.isEmpty(funId)) return null;
        ToolSystemFunction sf=toolSystemFunctionMapper.selectByPrimaryKey(funId);
        if(sf==null){//选择窗口，子功能等记录的是原始funid，所以做一次处理
            log.info("funid===="+funId+"  system_id=  "+ShiroUtils.getSystemId()+"%");
            Example example = new Example(ToolSystemFunction.class);
            example.setOrderByClause("order_index");
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fun_id",funId);
            criteria.andLike("system_id",ShiroUtils.getSystemId()+"%");
            List<ToolSystemFunction> lsf= toolSystemFunctionMapper.selectByExample(example);
            sf= lsf==null||lsf.size()<1?null:lsf.get(0);
        }

        ToolFunction entity=toolFunctionMapper.selectByPrimaryKey(sf==null?funId:sf.getFun_id());
        if(sf!=null){
            //换掉具体系统参数
            if(StringUtils.isNotEmpty(sf.getSystem_id())) entity.setModule_id(sf.getSystem_id());
            if(StringUtils.isNotEmpty(sf.getFun_name())) entity.setFun_name(sf.getFun_name());
            if(StringUtils.isNotEmpty(sf.getIco_img())) entity.setIco_img(sf.getIco_img());
            if(StringUtils.isNotEmpty(sf.getDb_source())) entity.setDb_source(sf.getDb_source());
            if(StringUtils.isNotEmpty(sf.getUrl_parames())&&sf.getUrl_parames().indexOf("{")>-1) {
                Map<String,String> parames= JSON.parseObject(sf.getUrl_parames(),Map.class);
                try {
                    entity= Map2EntityUtil.updateModel(entity, parames);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }

        return entity;
    }

    /**
     * 获取功能字段信息
     * @param funId
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolFunctionColumn> getFunctionColumn(String funId){
        ToolSystemFunction sf=toolSystemFunctionMapper.selectByPrimaryKey(funId);

        Example example = new Example(ToolFunctionColumn.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fun_id",sf==null?funId:sf.getFun_id());
//        criteria.andEqualTo("group_name","sql");
        List<ToolFunctionColumn> list=toolFunctionColumnMapper.selectByExample(example);

        return list;
    }

    /**
     * 获取功能唯一性判断字段信息
     * @param funId
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolFunctionColumn> getFunctionSingleColumn(String funId){
        ToolSystemFunction sf=toolSystemFunctionMapper.selectByPrimaryKey(funId);

        Example example = new Example(ToolFunctionColumn.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fun_id",sf==null?funId:sf.getFun_id());
        criteria.andEqualTo("group_name","sql");
        criteria.andEqualTo("is_unique","1");
        List<ToolFunctionColumn> list=toolFunctionColumnMapper.selectByExample(example);

        return list;
    }

    /**
     * 获取功能自动编码判断字段信息
     * @param funId
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolFunctionColumn> getFunctionAutoColumn(String funId){
        ToolSystemFunction sf=toolSystemFunctionMapper.selectByPrimaryKey(funId);

        Example example = new Example(ToolFunctionColumn.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fun_id",sf==null?funId:sf.getFun_id());
        criteria.andEqualTo("group_name","sql");
        criteria.andIsNotNull("auto_code");
        criteria.andNotEqualTo("auto_code","0");
        List<ToolFunctionColumn> list=toolFunctionColumnMapper.selectByExample(example);

        return list;
    }
    @DataSource(name = DataSourceNames.PLATFORM)
    public  ToolFunctionColumn  getFunctionColumnOne(String funId,String colName){
        ToolSystemFunction sf=toolSystemFunctionMapper.selectByPrimaryKey(funId);

        Example example = new Example(ToolFunctionColumn.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fun_id",sf==null?funId:sf.getFun_id());
        criteria.andEqualTo("group_name","sql");
        criteria.andEqualTo("column_name",colName);
        List<ToolFunctionColumn> list=toolFunctionColumnMapper.selectByExample(example);

       if(list!=null&&list.size()>0) return list.get(0);
       return null;
    }

    /**
     * 获取功能事件信息
     * @param funId
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolFunctionEvent> getFunctionEvent(String funId){
        ToolSystemFunction sf=toolSystemFunctionMapper.selectByPrimaryKey(funId);

        Example example = new Example(ToolFunctionEvent.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fun_id",sf==null?funId:sf.getFun_id());
        criteria.andEqualTo("is_show","1");//可见
        List<ToolFunctionEvent> list=toolFunctionEventMapper.selectByExample(example);

        return list;
    }


    /**
     * 获取查询条信息
     * @param funId
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolFunctionQuery> getFunctionQuery(String funId){
        ToolSystemFunction sf=toolSystemFunctionMapper.selectByPrimaryKey(funId);
        Example example = new Example(ToolFunctionQuery.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fun_id",sf==null?funId:sf.getFun_id());
        criteria.andEqualTo("is_show","1");//可见
        List<ToolFunctionQuery> list=toolFunctionQueryMapper.selectByExample(example);

        return list;
    }

    /**
     * 获取查询条信息
     * @param keyId
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public  ToolFunctionQuery  getFunctionQueryById(String keyId){
         ToolFunctionQuery  query=toolFunctionQueryMapper.selectByPrimaryKey(keyId);
        return query;
    }
    /**
     * 获取查询条字段明细
     * @param queryId
     * @return
     */
    @DataSource(name = DataSourceNames.PLATFORM)
    public List<ToolFunctionQueryColumn> getFunctionQueryColumn(String queryId){
        Example example = new Example(ToolFunctionQueryColumn.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("query_id",queryId);
        List<ToolFunctionQueryColumn> list=toolFunctionQueryColumnMapper.selectByExample(example);

        return list;
    }

    /**
     * 获取部门Json
     * @return
     */
    @DataSource(name = DataSourceNames.DEFAULT)
    public String getDeptJson(){
        List<SystemDept> datals=systemDeptMapper.selectAll();
        StringBuffer jsonstr=new StringBuffer("{");
        if(datals!=null)
            for(int i=0;i<datals.size();i++){
                SystemDept data=datals.get(i);
                jsonstr.append("'"+data.getDept_id()+"':'"+data.getDept_name()+"'"+(i<datals.size()-1?",":""));
            }
        jsonstr.append("}");
        return jsonstr.toString();
    }

    /**
     * 获取用户Json
     * @return
     */
    @DataSource(name = DataSourceNames.DEFAULT)
    public String getUserJson(){
        List<SystemUser> datals=systemUserMapper.selectAll();
        StringBuffer jsonstr=new StringBuffer("{");
        if(datals!=null)
            for(int i=0;i<datals.size();i++){
                SystemUser data=datals.get(i);
                jsonstr.append("'"+data.getUser_id()+"':'"+data.getUser_name()+"'"+(i<datals.size()-1?",":""));
            }
        jsonstr.append("}");
        return jsonstr.toString();
    }

    /**
     * 初始化权限信息
     */
    public void cacheRoleInfo(){
        String userId= ShiroUtils.getUserId();
        if(userId.equals("admin")) MemCache._userIsAdmin.put(userId,true);
        if(MemCache._userIsAdmin.containsKey(userId)){
            return;
        }
        SqlSession session =getSession(DataSourceNames.DEFAULT);
        SqlMapper sqlMapper=new SqlMapper(session);
        try{
            //是否超级管理员角色用户
            Map<String,Object> adminMp=sqlMapper.selectOne("select count(*) cnt from system_role_user where role_id='administrators' and user_id=#{userId}",userId);
            if((Long)adminMp.get("cnt")>0) {
                MemCache._userIsAdmin.put(userId,true);
                return;
            }  else MemCache._userIsAdmin.put(userId,false);
            //功能权限
            Map<String,String> paramMp=new HashMap<String,String>();
            paramMp.put("systemId",ShiroUtils.getSystemId());
            paramMp.put("userId",userId);
            List<Map<String,Object>> funList=sqlMapper.selectList("select  *   from view_system_userrole where system_id=#{systemId} and user_id=#{userId}",paramMp);
            Map<String,Map<String,Object>> newMap=new HashMap<String,Map<String,Object>>();
            for(Map<String,Object> fun:funList){//可能存在多个角色授权同一个用户，取最高权限
                Map<String,Object> mp=newMap.get((String)fun.get("fun_id"));

                if( !MemCache._userRoleModule.containsKey(userId + "--" + fun.get("module_id"))) {//记录有权限的模块id
                    MemCache._userRoleModule.put(userId + "--" + fun.get("one_moduleid"), true);
                    MemCache._userRoleModule.put(userId + "--" + fun.get("module_id"), true);
                }
                if(mp==null){
                    newMap.put((String)fun.get("fun_id"),fun);
                    continue;
                }
                if(StringUtils.emptyToDefault((String)fun.get("is_access"),"").equals("1")) mp.put("is_access","1");
                if(StringUtils.emptyToDefault((String)fun.get("event_edit"),"").equals("1")) mp.put("event_edit","1");
                if(StringUtils.emptyToDefault((String)fun.get("event_delete"),"").equals("1")) mp.put("event_delete","1");
                if(StringUtils.emptyToDefault((String)fun.get("event_audit"),"").equals("1")) mp.put("event_audit","1");
                if(StringUtils.emptyToDefault((String)fun.get("event_sign"),"").equals("1")) mp.put("event_sign","1");
                if(StringUtils.emptyToDefault((String)fun.get("event_print"),"").equals("1")) mp.put("event_print","1");
                //所有数据权限 or拼接，如果存在不限制的，则不限制
                if(StringUtils.emptyToDefault((String)fun.get("dataright_sql"),"").equals("")||StringUtils.emptyToDefault((String)mp.get("dataright_sql"),"").equals(""))
                    mp.put("dataright_sql","");
                else{
                    mp.put("dataright_sql","("+(String)mp.get("dataright_sql")+") or ("+(String)fun.get("dataright_sql")+")");
                }
                newMap.put((String)fun.get("fun_id"),mp);
            }
            MemCache._userRoles.put(userId,newMap);

        }catch (Exception e){
            log.error("初始化权限信息错误！"+userId,e);
        }finally {
            session.close();
        }

    }


}
