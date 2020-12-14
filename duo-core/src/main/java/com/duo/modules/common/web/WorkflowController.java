package com.duo.modules.common.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseController;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.WorkFlowService;
import com.duo.modules.common.service.bean.WFBean;
import com.duo.modules.system.entity.WfDataNodeuser;
import com.duo.modules.tool.entity.*;
import com.duo.modules.tool.mapper.ToolFunctionEventMapper;
import com.duo.modules.tool.mapper.ToolFunctionEventSqlMapper;
import com.duo.modules.tool.mapper.ToolWorkflowMapper;
import com.duo.modules.tool.mapper.ToolWorkflowNodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@Slf4j
@RequestMapping("/workflow")
public class WorkflowController extends BaseController {
    @Autowired
    private ToolWorkflowMapper toolWorkflowMapper;

    @Autowired
    private WorkFlowService workFlowService;

    @GetMapping
    @RequestMapping("/page-tool-workflowdesign")
    public String page_tool_workflowdesigner(Model model, HttpServletRequest request){

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String wf_id= (String) requestMap.get("foreignKeyId");
        ToolWorkflow wfData=toolWorkflowMapper.selectByPrimaryKey(wf_id);
        String jsondata=wfData.getWf_map();
        model.addAttribute("jsondata", StringUtils.emptyToDefault(jsondata,"{\"title\":\""+wfData.getWf_name()+"\"}"));
        model.addAttribute("nodeFun",wfData.getNote_fun());
        model.addAttribute("wf_id",wf_id);
        return "tool/page-tool-workflowdesign";
    }

    @GetMapping
    @RequestMapping("/page-tool-auditflowdesign")
    public String page_tool_auditflowdesigner(Model model, HttpServletRequest request){

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String wf_id= (String) requestMap.get("foreignKeyId");
        ToolWorkflow wfData=toolWorkflowMapper.selectByPrimaryKey(wf_id);
        String jsondata=wfData.getWf_map();
        model.addAttribute("jsondata",StringUtils.emptyToDefault(jsondata,"{\"title\":\""+wfData.getWf_name()+"\"}"));
        model.addAttribute("wf_id",wf_id);
        String fkeyid=wfData.getFun_id();
        List<ToolFunctionColumn> columnList=layoutService.getFunctionColumn(fkeyid);
        StringBuffer selectColumnList=new StringBuffer("");
        for(ToolFunctionColumn column:columnList){
            if(column.getIs_show().equals("0")&&column.getColumn_name().indexOf("_id")>0) continue;
            selectColumnList.append("<tr><td style=\"width:20%;\">")
                    .append(column.getColumn_name())
                    .append("</td><td style=\"width:25%;\">")
                    .append(column.getColumn_comment())
//                    .append("</td><td style=\"width:20%;\">")
//                    .append(column.getColumn_type())
//                    .append("</td><td style=\"width:15%;\">")
//                    .append("<input type='checkbox' "+(column.getIs_show().equals("1")?"checked":"")+"/>")
                    .append("</td><td style=\"width:15%;\">")
                    .append("<input type='checkbox' "+(column.getIs_modifyedit().equals("1")?"checked":"")+"/>")
                    .append("</td><td style=\"width:15%;\">")
                    .append("<input type='checkbox' "+(column.getIs_null().equals("1")?"checked":"")+"/>")
                    .append("</td><td style=\"width:20%;display:none;\">")
                    .append(column.getColumn_id())
                    .append("</td></tr>");
        }
        // log.info("selectColumnList:::"+selectColumnList);
        model.addAttribute("selectColumnList",selectColumnList.toString());
        return "tool/page-tool-auditflowdesign";
    }

    //保存流程设计图
    @ResponseBody
    @RequestMapping("/wfupdate")
    public Result<?> wfupdate(HttpServletRequest request) throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("wfupdateKey = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.saveWF(requestMap);
    }

    /**
     * 获取反馈流SQL
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getWFSQL")
    public Result<?> getWorkFlowSQL(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("getWFSQL Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }

        return workFlowService.getWorkFlowSQL(requestMap);
    }

    /**
     * 保存更新反馈流sql
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/setWFSQL")
    public Result<?> setWorkFlowSQL(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("setWFSQL Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.setWorkFlowSQL(requestMap);
    }



    //获取可编辑字段列表
    @ResponseBody
    @RequestMapping("/getWFEditColumn")
    public Result<?> getWFEditColumn(HttpServletRequest request) throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("getWFEditColumn key= " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.getWFEditColumn(requestMap);
    }


    //保存可编辑字段列表
    @ResponseBody
    @RequestMapping("/setWFEditColumn")
    public Result<?> setWFEditColumn(HttpServletRequest request) throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("setWFEditColumn key= " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.setWFEditColumn(requestMap);
    }


    //获取线的用户列表
    @ResponseBody
    @RequestMapping("/getWFLineUser")
    public Result<?> getWFLineUser(HttpServletRequest request) throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("getWFLineUser key= " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.getWFLineUser(requestMap);
    }


    //保存线的用户列表
    @ResponseBody
    @RequestMapping("/setWFLineUser")
    public Result<?> setWFLineUser(HttpServletRequest request) throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("setWFLineUser key= " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.setWFLineUser(requestMap);
    }

    /**************************************************审批流程过程处理***************************************************************/


    //发起流程——获取下一个节点信息和主办人信息
    @ResponseBody
    @RequestMapping("/getnextNodes")
    public Result<?> getnextNodes(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.getnextNodes(requestMap);
    }


    //保存审批记录，并不是提交审批，只是暂存
    @ResponseBody
    @RequestMapping("/saveData")
    public Result<?> saveData(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.saveData(requestMap);
    }


    //提交下一节点审批流程
    @ResponseBody
    @RequestMapping("/putNext")
    public Result<?> putNext(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.putNext(requestMap);
    }


    //挂起流程
    @ResponseBody
    @RequestMapping("/pauseData")
    public Result<?> pauseData(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.pauseData(requestMap);
    }



    //委托流程
    @ResponseBody
    @RequestMapping("/entrustData")
    public Result<?> entrustData(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.entrustData(requestMap);
    }




    //获取默认审批人和查询全部审批人
    @ResponseBody
    @RequestMapping("/getCheckUser")
    public Result<?> getCheckUser(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.entrustData(requestMap);
    }




    //提交审批
    @ResponseBody
    @RequestMapping("/sentCheck")
    public Result<?> sentCheck(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        return workFlowService.entrustData(requestMap);
    }


}
