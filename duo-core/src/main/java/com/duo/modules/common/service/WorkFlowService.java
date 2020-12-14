package com.duo.modules.common.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.utils.E2MapUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.bean.Line;
import com.duo.modules.common.service.bean.Node;
import com.duo.modules.common.service.bean.WFBean;
import com.duo.modules.system.entity.WfData;
import com.duo.modules.system.entity.WfDataNodeuser;
import com.duo.modules.system.mapper.WfDataMapper;
import com.duo.modules.system.mapper.WfDataNodeuserMapper;
import com.duo.modules.tool.entity.*;
import com.duo.modules.tool.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author [ Yonsin ] on [2020/1/22]
 * @Description： [ 工作流相关业务 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Service
@Slf4j
//@Transactional
public class WorkFlowService extends BaseService {

    @Autowired
    private LayoutService layoutService;

    @Autowired
    private ToolWorkflowMapper toolWorkflowMapper;
    @Autowired
    private ToolWorkflowNodeMapper toolWorkflowNodeMapper;
    @Autowired
    private ToolWorkflowNodeColumnMapper toolWorkflowNodeColumnMapper;
    @Autowired
    private ToolWorkflowLineMapper toolWorkflowLineMapper;
    @Autowired
    private ToolWorkflowLineUserMapper toolWorkflowLineUserMapper;
    @Autowired
    private ToolFunctionEventMapper toolFunctionEventMapper;
    @Autowired
    private ToolFunctionEventSqlMapper toolFunctionEventSqlMapper;
    @Autowired
    private WfDataMapper wfDataMapper;
    @Autowired
    private WfDataNodeuserMapper wfDataNodeuserMapper;

    /**
     * 保存流程图
     * @param requestMap
     * @return
     * @throws Exception
     */
    public Result<?> saveWF(Map<String, Object> requestMap) throws Exception {

        String data=StringEscapeUtils.unescapeHtml((String)requestMap.get("data"));
        String nodeFun=StringEscapeUtils.unescapeHtml((String)requestMap.get("nodeFun"));
        JSONObject json = JSONObject.parseObject(data);
        String wf_id= (String) requestMap.get("wf_id");
        ToolWorkflow wfData=toolWorkflowMapper.selectByPrimaryKey(wf_id);
        json.put("title",wfData.getWf_name());
        //如果没有任何调整则直接返回成功
        //  if(!json.toString().equals(wfData.getWf_map())) {
        wfData.setWf_map(json.toString());
        wfData.setNote_fun(nodeFun);
        //保存图形
        toolWorkflowMapper.updateByPrimaryKey(wfData);
        WFBean bean=  JSONObject.toJavaObject(json,WFBean.class);//射界图转对象
        if(!setNodes(wf_id,nodeFun,bean)) return Result.failure("500","保存节点信息出错！");
        if(wfData.getWf_type().equals("audit"))
            if(!setLines(wf_id,bean)) return Result.failure("500","保存连线信息出错！");
        //  }
        return Result.success();
    }
    /**
     * 保存节点信息
     * @param bean
     * @return
     */
    public boolean setNodes(String wf_id, String nodeFun, WFBean bean) throws Exception {
        JSONObject json =  JSONObject.parseObject(nodeFun);
        Map<String, Node> nodesMap = bean.getNodes();
        List<String> ids=new ArrayList<String>();
        for (Map.Entry<String, Node> entry : nodesMap.entrySet()) {
            Node node=entry.getValue();
            E2MapUtil.pringEntity(node, Node.class) ;
            ids.add(wf_id+entry.getKey());
            ToolWorkflowNode entity=toolWorkflowNodeMapper.selectByPrimaryKey(wf_id+entry.getKey());
            if(entity==null){
                entity=new ToolWorkflowNode();
                entity.setNode_id(wf_id + entry.getKey());
                entity.setNode_no(entry.getKey());
                entity.setWf_id(wf_id);
                entity.setNode_name(node.getName());
                entity.setNode_type(node.getType());
                entity.setAllow_skip("");
                entity.setShow_alluser("");
                entity.setTime_maxdays("");
               if(json!=null&&!json.isEmpty()) entity.setFun_id((String)json.get(entry.getKey()));
                toolWorkflowNodeMapper.insertSelective(entity);
            }else {
                entity.setNode_name(node.getName());
                entity.setNode_type(node.getType());
                entity.setAllow_skip("");
                entity.setShow_alluser("");
                entity.setTime_maxdays("");
                if(json!=null&&!json.isEmpty())  entity.setFun_id((String)json.get(entry.getKey()));
                toolWorkflowNodeMapper.updateByPrimaryKey(entity);
            }
        }
        //删除页面不存在的node
        Example example = new Example(ToolWorkflowNode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wf_id",wf_id);
        criteria.andNotIn("node_id",ids);
        toolWorkflowNodeMapper.deleteByExample(example);
        Example nodexample = new Example(ToolWorkflowNodeColumn.class);
        Example.Criteria nodecriteria = nodexample.createCriteria();
        nodecriteria.andEqualTo("wf_id",wf_id);
        nodecriteria.andNotIn("node_id",ids);
        toolWorkflowNodeColumnMapper.deleteByExample(nodexample);
        return true;
    }

    /**
     * 保存连线信息
     * @param bean
     * @return
     */
    public boolean setLines(String wf_id,  WFBean bean) throws Exception {
        Map<String, Line> nodesMap = bean.getLines();
        List<String> ids=new ArrayList<String>();
        for (Map.Entry<String, Line> entry : nodesMap.entrySet()) {
            Line line=entry.getValue();
            E2MapUtil.pringEntity(line, Line.class) ;
            ids.add(wf_id+entry.getKey());
            ToolWorkflowLine entity=toolWorkflowLineMapper.selectByPrimaryKey(wf_id+entry.getKey());
            if(entity==null){
                entity=new ToolWorkflowLine();
                entity.setLine_id(wf_id + entry.getKey());
                entity.setWf_id(wf_id);
                entity.setLine_text(line.getName());
                entity.setLine_type(line.getType());
                entity.setStart_node(line.getFrom());
                entity.setEnd_node(line.getTo());
                toolWorkflowLineMapper.insertSelective(entity);
            }else {
                entity.setLine_text(line.getName());
                entity.setLine_type(line.getType());
                entity.setStart_node(line.getFrom());
                entity.setEnd_node(line.getTo());
                toolWorkflowLineMapper.updateByPrimaryKey(entity);
            }
        }
        //删除页面不存在的node
        Example example = new Example(ToolWorkflowLine.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wf_id",wf_id);
        criteria.andNotIn("line_id",ids);
        toolWorkflowLineMapper.deleteByExample(example);
        Example userexample = new Example(ToolWorkflowLineUser.class);
        Example.Criteria usercriteria = userexample.createCriteria();
        usercriteria.andEqualTo("wf_id",wf_id);
        usercriteria.andNotIn("line_id",ids);
        toolWorkflowLineUserMapper.deleteByExample(userexample);
        return true;
    }

    /**
     * 获取反馈流SQL
     * @param requestMap
     * @return
     */
    public Result<?> getWorkFlowSQL(Map<String, Object> requestMap) {

        Result data=new Result();

        String sqlType=(String)requestMap.get("sqlType");
        String wf_id=(String)requestMap.get("wf_id");
        String node_no=(String)requestMap.get("node_no");
        if(sqlType.equals("number")){//获取计算数量的sql
            ToolWorkflowNode node=  toolWorkflowNodeMapper.selectByPrimaryKey(wf_id+node_no);
            data.setData(node==null?null:node.getNumber_sql());
            data.setSuccess(true);

        }else  if(sqlType.equals("workflow")){//获取工作流sql

            String sourceFunId=(String)requestMap.get("sourceFunId");
            String targetFunId=(String)requestMap.get("targetFunId");
            String eventType=(String)requestMap.get("eventType");
            ToolFunctionEventSql query=new ToolFunctionEventSql();
            query.setSource_funid(sourceFunId);
            query.setTarget_funid(targetFunId);
            // query.setEvent_type(eventType);
            List<ToolFunctionEventSql> eventSqls=toolFunctionEventSqlMapper.select(query);

            data.setData( eventSqls==null?null: JSON.toJSON(eventSqls));

            data.setSuccess(true);
        }

        return data;
    }
    /**
     * 保存更新反馈流SQL
     * @param requestMap
     * @return
     */
    public Result<?> setWorkFlowSQL(Map<String, Object> requestMap) {

        String sqlType=(String)requestMap.get("sqlType");
        String wf_id=(String)requestMap.get("wf_id");
        String node_no=(String)requestMap.get("node_no");
        String number_sql=(String)requestMap.get("number_sql");
        if(sqlType.equals("number")) {//获取计算数量的sql
            ToolWorkflowNode entity=toolWorkflowNodeMapper.selectByPrimaryKey(wf_id+node_no);
            if(entity==null) return Result.failure("500","未找到node信息，保存失败！");
            entity.setNumber_sql(number_sql);
            toolWorkflowNodeMapper.updateByPrimaryKey(entity);
        }else  if(sqlType.equals("workflow")) {//获取工作流sql

            String sourceFunId=(String)requestMap.get("sourceFunId");
            String targetFunId=(String)requestMap.get("targetFunId");
            String eventType=(String)requestMap.get("event_id");
            String event_id=(String)requestMap.get("eventType");
            String source_sql=(String)requestMap.get("source_sql");
            String target_sql=(String)requestMap.get("target_sql");
            ToolFunctionEventSql query=new ToolFunctionEventSql();
            query.setSource_funid(sourceFunId);
            query.setTarget_funid(targetFunId);
            query.setEvent_type(eventType);
            List<ToolFunctionEventSql> eventSqls=toolFunctionEventSqlMapper.select(query);
            if(eventSqls!=null) {
                //保存工作流
                ToolFunctionEventSql eventSql=eventSqls.get(0);
                eventSql.setSource_sql(source_sql);
                eventSql.setTarget_sql(target_sql);
                toolFunctionEventSqlMapper.updateByPrimaryKey(eventSql);
            }else{
                if(event_id==null){
                    ToolFunctionEvent query2=new ToolFunctionEvent();
                    query2.setFun_id(sourceFunId);
                    query2.setEvent_code(eventType);
                    List<ToolFunctionEvent> events=toolFunctionEventMapper.select(query2);
                    if(events==null) return Result.failure("500","未找到对应定义的事件，请确定该功能是否有该事件！");
                    event_id=events.get(0).getEvent_id();
                }

                //新增工作流
                ToolFunctionEventSql eventSql=new ToolFunctionEventSql();
                eventSql.setEventsql_id(getKeyUID());
                eventSql.setSource_funid(sourceFunId);
                eventSql.setTarget_funid(targetFunId);
                eventSql.setEvent_type(eventType);
                eventSql.setEvent_id(event_id);
                eventSql.setSource_sql(source_sql);
                eventSql.setTarget_sql(target_sql);
                toolFunctionEventSqlMapper.insertSelective(eventSql);

            }
        }
        return Result.success();
    }


    /**
     * 获取可编辑字段列表
     * @param requestMap
     * @return
     */
    public Result<?> getWFEditColumn(Map<String, Object> requestMap) {
        Result data=new Result();
        String wf_id=(String)requestMap.get("wf_id");
        String node_no=(String)requestMap.get("node_no");
        ToolWorkflowNode entity=toolWorkflowNodeMapper.selectByPrimaryKey(wf_id+node_no);
        if(entity==null) {
            return Result.failure("500","请先保存流程图后再设置节点信息！");
        }
        ToolWorkflowNodeColumn query = new ToolWorkflowNodeColumn();
        query.setNode_id(entity.getNode_id());
        List<ToolWorkflowNodeColumn> list = toolWorkflowNodeColumnMapper.select(query);
        data.setData(list == null ? null : JSON.toJSON(list));
        data.setSuccess(true);
        return data;
    }

    /**
     * 保存可编辑字段列表
     * @param requestMap
     * @return
     */
    public Result<?> setWFEditColumn(Map<String, Object> requestMap) throws Exception {

        String wf_id=(String)requestMap.get("wf_id");
        String node_no=(String)requestMap.get("node_no");
        String datas=(String)requestMap.get("datas");
        ToolWorkflowNode entity=toolWorkflowNodeMapper.selectByPrimaryKey(wf_id+node_no);
        if(entity==null) return Result.failure("500","请先保存流程图后再设置节点信息！");
        List<Map> list = JSONObject.parseArray(datas,Map.class);
        ToolWorkflowNodeColumn query=new ToolWorkflowNodeColumn();
        query.setNode_id(entity.getNode_id());
        toolWorkflowNodeColumnMapper.delete(query);
        int n=1;
        for(Map mp:list){
            ToolWorkflowNodeColumn column = (ToolWorkflowNodeColumn) Map2EntityUtil.createModel(ToolWorkflowNodeColumn.class, mp);
            column.setNodecol_id(getKeyUID());
            column.setNode_id(entity.getNode_id());
            column.setWf_id(wf_id);
            column.setOrder_index(n++*100);
            toolWorkflowNodeColumnMapper.insertSelective(column);
        }
        return Result.success();
    }



    /**
     * 获取线的用户列表
     * @param requestMap
     * @return
     */
    public Result<?> getWFLineUser(Map<String, Object> requestMap) {
        Result data=new Result();
        String wf_id=(String)requestMap.get("wf_id");
        String start_node_no=(String)requestMap.get("start_node_no");
        String end_node_no=(String)requestMap.get("end_node_no");
        Example example = new Example(ToolWorkflowLine.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wf_id",wf_id);
        criteria.andEqualTo("start_node",start_node_no);
        criteria.andEqualTo("end_node",end_node_no);
        ToolWorkflowLine entity=toolWorkflowLineMapper.selectOneByExample(example);
        if(entity==null) {
            return Result.failure("500","请先保存流程图后再设置连线信息！");
        }
        ToolWorkflowLineUser query=new ToolWorkflowLineUser();
        query.setLine_id(entity.getLine_id());
        List<ToolWorkflowLineUser> list=toolWorkflowLineUserMapper.select(query);
        Map mp=new HashMap();
        mp.put("whersql",entity.getWhere_sql());
        mp.put("users",list);
        data.setData(JSON.toJSON(mp));
        data.setSuccess(true);
        return data;
    }

    /**
     * 保存线的用户列表
     * @param requestMap
     * @return
     */
    public Result<?> setWFLineUser(Map<String, Object> requestMap) throws Exception {
        String wf_id=(String)requestMap.get("wf_id");
        String whersql=(String)requestMap.get("whersql");
        String datas=(String)requestMap.get("datas");
        String start_node_no=(String)requestMap.get("start_node_no");
        String end_node_no=(String)requestMap.get("end_node_no");
        Example example = new Example(ToolWorkflowLine.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wf_id",wf_id);
        criteria.andEqualTo("start_node",start_node_no);
        criteria.andEqualTo("end_node",end_node_no);
        ToolWorkflowLine entity=toolWorkflowLineMapper.selectOneByExample(example);
        if(entity==null) {
            return Result.failure("500","请先保存流程图后再设置连线信息！");
        }
        List<Map> list = JSONObject.parseArray(datas,Map.class);
        ToolWorkflowLineUser query=new ToolWorkflowLineUser();
        query.setLine_id(entity.getLine_id());
        toolWorkflowLineUserMapper.delete(query);
        int n=1;
        for(Map mp:list){
            ToolWorkflowLineUser user = (ToolWorkflowLineUser) Map2EntityUtil.createModel(ToolWorkflowLineUser.class, mp);
            user.setNoduser_id(getKeyUID());
            user.setLine_id(entity.getLine_id());
            user.setWf_id(wf_id);
            user.setOrder_index(n++*100);
            toolWorkflowLineUserMapper.insertSelective(user);
        }
        entity.setWhere_sql(whersql);
        toolWorkflowLineMapper.updateByPrimaryKey(entity);
        return Result.success();
    }


    /**************************************************审批流程过程处理***************************************************************/

    //发起流程——获取下一个节点信息和主办人信息
    public Result<?> getnextNodes(Map<String, Object> requestMap) {
        String funId=(String)requestMap.get("funId");
        String dataId=(String)requestMap.get("dataId");
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        ToolWorkflow query=new ToolWorkflow();
        query.setFun_id(funInfo.getFun_id());
        query.setWf_state("1");
        ToolWorkflow wf=toolWorkflowMapper.selectOne(query);
        if(wf==null) return Result.failure("500","该功能未定义审批流或审批流未生效！");
        ToolWorkflowNode n=new ToolWorkflowNode();
        n.setWf_id(wf.getWf_id());
        n.setNode_type("start round mix");
        ToolWorkflowNode start=toolWorkflowNodeMapper.selectOne(n);
        if(start==null) return Result.failure("500","该审批流定义不正确，无开始节点！");


        return getNextInfo(wf,start);
    }
    public Result<?> getNextInfo(ToolWorkflow wf,ToolWorkflowNode start){
        ToolWorkflowLine q=new ToolWorkflowLine();
        q.setWf_id(wf.getWf_id());
        q.setStart_node(start.getNode_no());
        List<ToolWorkflowLine> lines=toolWorkflowLineMapper.select(q);
        if(lines==null)  return Result.failure("500","该审批流定义不正确，发现不到下一个节点！");
        List ids=new ArrayList();
        for(ToolWorkflowLine line:lines){
            ids.add(line.getEnd_node());
        }
        Example example = new Example(ToolWorkflowNode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wf_id",wf.getWf_id());
        criteria.andIn("node_no",ids);

        List<ToolWorkflowNode> nodes=toolWorkflowNodeMapper.selectByExample(example);
        DynamicDataSource.clearDataSource();
        Map mp=new HashMap();
        mp.put("nodes",nodes);
        mp.put("wf_id",wf.getWf_id());
        mp.put("step_no","0");
        Result r=new Result();
        r.setSuccess(true);
        log.info(JSON.toJSONString(mp));
        r.setData(JSON.toJSON(mp));
        return r;
    }

    //保存审批记录，并不是提交审批，只是暂存
    //非主办人，当isDeal为1时办理完成
    public Result<?> saveData(Map<String, Object> requestMap) {
        String wf_id=(String)requestMap.get("wf_id");
        String isDeal=(String)requestMap.get("isDeal");
        String wfUserid=(String)requestMap.get("wfUserid");
        String agreeRadio=(String)requestMap.get("agreeRadio");
        String checkAdvise=(String)requestMap.get("checkAdvise");
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        WfDataNodeuser myNodeData=(WfDataNodeuser)getMapper("WfDataNodeuser").selectByPrimaryKey(wfUserid);
        myNodeData.setIs_agree(agreeRadio);
        myNodeData.setSign_advise(checkAdvise);
        if(isDeal!=null&&isDeal.equals("1")){
            myNodeData.setData_status("1");
        }

        getMapper("WfDataNodeuser").updateByPrimaryKey(myNodeData);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }


    //提交下一节点审批流程
    public Result<?> putNext(Map<String, Object> requestMap)  {
        String wf_id=(String)requestMap.get("wfId");
        String node_id=(String)requestMap.get("nodeId");
        String record_id=(String)requestMap.get("keyId");
        String wfUserid=(String)requestMap.get("wfUserid");
        String data_id=(String)requestMap.get("dataId");
        String step_no=(String)requestMap.get("stepNo");
        String isBack=(String)requestMap.get("isBack");//是否回退
        String checkAdvise=(String)requestMap.get("checkAdvise");
        String agreeRadio=(String)requestMap.get("agreeRadio");
        String auditColumn=(String)requestMap.get("auditColumn");
        String sponsor_user_id=(String)requestMap.get("sponsor_user_id");
        String sponsor_user_name=(String)requestMap.get("sponsor_user_name");
        String operator_user_id=(String)requestMap.get("operator_user_id");
        String operator_user_name=(String)requestMap.get("operator_user_name");
        String copy_user_id=(String)requestMap.get("copy_user_id");
        String copy_user_name=(String)requestMap.get("copy_user_name");
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        try {
            if (step_no.equals("0")) {//发起流程
                String funId = (String) requestMap.get("funId");
                //获取功能设置信息
                ToolFunction funInfo = layoutService.getFunctionInfo(funId);
                //判断是否重复发起
                WfData query = new WfData();
                query.setWf_id(wf_id);
                query.setRecord_id(record_id);
                WfData wfdata=wfDataMapper.selectOne(query);
                if(wfdata==null) {
                    data_id = getKeyUID();
                    wfdata = new WfData();
                    wfdata.setData_id(data_id);
                    wfdata.setWf_id(wf_id);
                    wfdata.setRecord_id(record_id);
                    wfdata.setData_title(funInfo.getFun_name());
                    wfdata.setFun_id(funInfo.getFun_id());
                    wfdata.setCreate_date(new Date());
                    wfdata.setPost_name(auditColumn);//存该fun的状态字段
                    wfdata.setData_no(autoRuleCode("WFNO", "default", "yearseq", null, null, 6, ""));
                    wfDataMapper.insertSelective(wfdata);
                }else{
                    data_id =wfdata.getData_id();
                }

                DynamicDataSource.setDataSource(funInfo.getDb_source());
                BaseEntity entity = (BaseEntity) getMapper(funInfo.getTable_name()).selectByPrimaryKey(record_id);
                //根据页面最新值更新对象//初始化对象
                entity = (BaseEntity) Map2EntityUtil.updateModel(entity, requestMap);
                entity = modifyDataInfo(entity);
                //保存记录
                getMapper(funInfo.getTable_name()).updateByPrimaryKeySelective(entity);

                DynamicDataSource.clearDataSource();
                DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);

            }else{
                //标记已完成，其他经办人也关闭待办
                WfDataNodeuser q=new WfDataNodeuser();
                q.setData_id(data_id);
                q.setData_status("0");
                List<WfDataNodeuser> lsUsers=wfDataNodeuserMapper.select(q);
                if(isBack==null||!isBack.equals("1")) {//非退回流程需要其他经办人完成签批
                    for (WfDataNodeuser user : lsUsers) {//经办人还未结束会签，不允许提交
                        if (user.getIs_operator().equals("1")) {
                            return Result.failure("500", "需要所有经办人办理后才能提交下一步！");
                        }
                    }
                }

                for(WfDataNodeuser user:lsUsers){
                    if(user.getWf_userid().equals(wfUserid)) {
                        user.setData_status("1");
                        user.setIs_agree(agreeRadio);
                        user.setSign_advise(checkAdvise);
                    }else {
                        user.setData_status("3");
                    }
                    wfDataNodeuserMapper.updateByPrimaryKey(user);
                }

            }
            ToolWorkflowNode node = toolWorkflowNodeMapper.selectByPrimaryKey(node_id);
            //结束节点
            if(node.getNode_type().equals("end round red")){
                String funId = (String) requestMap.get("funId");
                //获取功能设置信息
                ToolFunction funInfo = layoutService.getFunctionInfo(funId);
                DynamicDataSource.setDataSource(funInfo.getDb_source());
                BaseEntity entity = (BaseEntity) getMapper(funInfo.getTable_name()).selectByPrimaryKey(record_id);
                //根据页面最新值更新对象//初始化对象
                entity = (BaseEntity) Map2EntityUtil.updateModel(entity, requestMap);
                entity = modifyDataInfo(entity);
                //保存记录
                getMapper(funInfo.getTable_name()).updateByPrimaryKeySelective(entity);

                DynamicDataSource.clearDataSource();
                return Result.success();
            }else  if(node.getNode_type().equals("start round mix")){//退回开始节点
                String funId = (String) requestMap.get("funId");
                //获取功能设置信息
                ToolFunction funInfo = layoutService.getFunctionInfo(funId);
                DynamicDataSource.setDataSource(funInfo.getDb_source());
                BaseEntity entity = (BaseEntity) getMapper(funInfo.getTable_name()).selectByPrimaryKey(record_id);
                //根据页面最新值更新对象//初始化对象
                entity = (BaseEntity) Map2EntityUtil.updateModel(entity, requestMap);
                entity = modifyDataInfo(entity);
                //保存记录
                getMapper(funInfo.getTable_name()).updateByPrimaryKeySelective(entity);

                DynamicDataSource.clearDataSource();
                return Result.success();
            }

            String hasUserids="";
            //主办人
            WfDataNodeuser wfuser = new WfDataNodeuser();
            wfuser.setWf_userid(getKeyUID());
            wfuser.setData_id(data_id);
            wfuser.setUser_id(sponsor_user_id);
            wfuser.setUser_name(sponsor_user_name);
            wfuser.setAdd_date(new Date());
            wfuser.setData_status("0");
            wfuser.setIs_sponsor("1");
            wfuser.setIs_operator("0");
            wfuser.setNode_id(node.getNode_id());
            wfuser.setNode_name(node.getNode_name());
            wfuser.setNode_no(node.getNode_no());
            wfuser.setStep_no(String.valueOf(Integer.parseInt(step_no)+1));
            wfDataNodeuserMapper.insertSelective(wfuser);
            hasUserids=hasUserids+";"+sponsor_user_id+";";
            //经办人
            if (!StringUtils.isEmpty(operator_user_id)) {
                String[] userids = operator_user_id.split(";");
                String[] usernames = operator_user_name.split(";");
                for (int i = 0; i < userids.length; i++) {
                    String userid = userids[i];
                    if (StringUtils.isEmpty(userid)) continue;
                    if(hasUserids.indexOf(userid+";")>-1) continue;//已经是主办人或重复经办的不再插入
                    WfDataNodeuser wfuserc = new WfDataNodeuser();
                    wfuserc.setWf_userid(getKeyUID());
                    wfuserc.setData_id(data_id);
                    wfuserc.setUser_id(userid);
                    wfuserc.setUser_name(usernames[i]);
                    wfuserc.setAdd_date(new Date());
                    wfuserc.setData_status("0");
                    wfuserc.setIs_sponsor("0");
                    wfuserc.setIs_operator("1");
                    wfuserc.setNode_id(node.getNode_id());
                    wfuserc.setNode_name(node.getNode_name());
                    wfuserc.setNode_no(node.getNode_no());
                    wfuserc.setStep_no(String.valueOf(Integer.parseInt(step_no)+1));
                    wfDataNodeuserMapper.insertSelective(wfuserc);
                    hasUserids=hasUserids+";"+userid+";";
                }
            }
            //抄送人
            if (!StringUtils.isEmpty(copy_user_id)) {
                String[] userids = copy_user_id.split(";");
                String[] usernames = copy_user_name.split(";");
                for (int i = 0; i < userids.length; i++) {
                    String userid = userids[i];
                    if (StringUtils.isEmpty(userid)) continue;
                    if(hasUserids.indexOf(userid+";")>-1) continue;//已经是主办人或重复经办的不再插入
                    WfDataNodeuser wfuserc = new WfDataNodeuser();
                    wfuserc.setWf_userid(getKeyUID());
                    wfuserc.setData_id(data_id);
                    wfuserc.setUser_id(userid);
                    wfuserc.setUser_name(usernames[i]);
                    wfuserc.setAdd_date(new Date());
                    wfuserc.setData_status("0");
                    wfuserc.setIs_sponsor("0");
                    wfuserc.setIs_operator("0");
                    wfuserc.setNode_id(node.getNode_id());
                    wfuserc.setNode_name(node.getNode_name());
                    wfuserc.setNode_no(node.getNode_no());
                    wfuserc.setStep_no(String.valueOf(Integer.parseInt(step_no)+1));
                    wfDataNodeuserMapper.insertSelective(wfuserc);
                    hasUserids=hasUserids+";"+userid+";";
                }
            }

            DynamicDataSource.clearDataSource();
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure("500","执行出错！");
        }finally {
            DynamicDataSource.clearDataSource();
        }
    }

    //挂起流程
    public Result<?> pauseData(Map<String, Object> requestMap) {
        String wf_id=(String)requestMap.get("wf_id");
        String wfUserid=(String)requestMap.get("wfUserid");
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        WfDataNodeuser myNodeData=(WfDataNodeuser)getMapper("WfDataNodeuser").selectByPrimaryKey(wfUserid);
        myNodeData.setData_status("4");
        getMapper("WfDataNodeuser").updateByPrimaryKey(myNodeData);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }



    //委托流程
    public Result<?> entrustData(Map<String, Object> requestMap) {
        String wf_id=(String)requestMap.get("wf_id");
        String wfUserid=(String)requestMap.get("wfUserid");
        String entrustUserid=(String)requestMap.get("entrustUserid");
        String entrustUsername=(String)requestMap.get("entrustUsername");
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        WfDataNodeuser myNodeData=(WfDataNodeuser)getMapper("WfDataNodeuser").selectByPrimaryKey(wfUserid);
        myNodeData.setData_status("1");
        myNodeData.setEntrust_userid(entrustUserid);
        myNodeData.setEntrust_user(entrustUsername);
        myNodeData.setSign_advise("已委托给【"+entrustUsername+"】。");
        myNodeData.setIs_agree("1");
        getMapper("WfDataNodeuser").updateByPrimaryKey(myNodeData);
        //委托人
        myNodeData.setWf_userid(getKeyUID());
        myNodeData.setIs_agree("1");
        myNodeData.setData_status("0");
        myNodeData.setAdd_date(new Date());
        myNodeData.setSign_advise(null);
        myNodeData.setEntrust_user(null);
        myNodeData.setEntrust_userid(null);
        myNodeData.setAdd_userid(myNodeData.getUser_id());
        myNodeData.setSign_date(null);
        myNodeData.setUser_id(entrustUserid);
        myNodeData.setUser_name("(委)"+entrustUsername);
        myNodeData.setModify_date(null);
        myNodeData.setModify_userid(null);
        getMapper("WfDataNodeuser").insertSelective(myNodeData);
        DynamicDataSource.clearDataSource();
        return Result.success();
    }


}
