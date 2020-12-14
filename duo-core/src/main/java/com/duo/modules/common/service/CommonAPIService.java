package com.duo.modules.common.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.config.DynamicDataSource;
import com.duo.core.APISessionBean;
import com.duo.core.BaseEntity;
import com.duo.core.BaseService;
import com.duo.core.PasswordHelper;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.tool.entity.*;
import com.duo.modules.tool.mapper.*;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author [ Yonsin ] on [2019/4/12]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Service
public class CommonAPIService extends BaseService {

    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private ToolApiMapper toolApiMapper;
    @Autowired
    private ToolApiBlacklistMapper toolApiBlacklistMapper;
    @Autowired
    private ToolApiParamMapper toolApiParamMapper;
    @Autowired
    private ToolApiReturnMapper toolApiReturnMapper;
    @Autowired
    private ToolApiUserMapper toolApiUserMapper;
    @Autowired
    private ToolApiUserRuleMapper toolApiUserRuleMapper;
    @Autowired
    private ToolApiLogMapper toolApiLogMapper;

    /**
     * API主判断类
     */
    public Result mainAPI(HttpServletRequest request,String project, String apiurl) {
        Result result;
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("mainAPI key= " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        log.info("............project====......." + project);
        log.info("..........apiurl===........." + apiurl);
        ToolApi query = new ToolApi();
        query.setApi_url("/" + project + "/" + apiurl);
        query.setApi_status("1");
        ToolApi api = toolApiMapper.selectOne(query);
        if (api == null) {
            result= Result.failure("201", "接口地址不存在或未经授权！");
            if(!StringUtils.isEmpty(api.getIs_log())&&api.getIs_log().equals("1")){
                writeLog(api,result,requestMap);
            }
            return  result;
        }
        if (StringUtils.isEmpty(api.getDbsource_name())  //不允许数据源为空
                || (StringUtils.isEmpty(api.getData_entity()) && StringUtils.isEmpty(api.getData_sql()))) {//不允许entity和datasql字段同时为空

            result=  Result.failure("202", "接口未定义存在错误，请联系系统管理员！");
            if(!StringUtils.isEmpty(api.getIs_log())&&api.getIs_log().equals("1")){
                writeLog(api,result,requestMap);
            }
            return  result;
        }
        //ip是否该接口黑名单


        //判断输入参数是否符合要求
        Result check=validateParam(requestMap,api);
        if(!check.isSuccess()) return check;


        //系统预设的登录和退出   auth
        if (StringUtils.isEmpty(api.getData_sql()) && api.getApi_type().equals("auth")
                && apiurl.equals("login")) return login(request);
        //判断tokenId是否存在
        String tokenID=(String)requestMap.get("tokenID");
        if (StringUtils.isEmpty(tokenID)){//validateParam 没有要求tokenID则不判断
//            result=  Result.failure("110", "tokenID为空，请先登录认证后再执行！");
//            if(!StringUtils.isEmpty(api.getIs_log())&&api.getIs_log().equals("1")){
//                writeLog(api,result,requestMap);
//            }
//            return  result;
        }else{
            if(!RedisUtil.hHasKey("ApiTokens",tokenID)){
                result=  Result.failure("119", "tokenID有误或未登录，请先登录认证后再执行！");
                if(!StringUtils.isEmpty(api.getIs_log())&&api.getIs_log().equals("1")){
                    writeLog(api,result,requestMap);
                }
                return  result;
            }
        }

        //系统预设的登录和退出   auth
        if (StringUtils.isEmpty(api.getData_sql()) && api.getApi_type().equals("auth")
                && apiurl.equals("logout")) return logout(request);

        ToolApiUser user=null;
        ToolApiUserRule rule=null;
        if (StringUtils.isNotEmpty(tokenID)) {
            String beanJson = (String) RedisUtil.hget("ApiTokens", tokenID);
            log.info(beanJson);
            APISessionBean userBean = JSONObject.parseObject(beanJson, APISessionBean.class);
            if (userBean == null || userBean.getToolApiUser() == null) {
                result = Result.failure("120", "tokenID有误或未登录，请先登录认证后再执行！");
                if (!StringUtils.isEmpty(api.getIs_log()) && api.getIs_log().equals("1")) {
                    writeLog(api, result, requestMap);
                }
                return result;
            }
            //判断访问剩余次数
            user = toolApiUserMapper.selectByPrimaryKey(userBean.getToolApiUser().getUser_id());
            if (user == null) {
                result = Result.failure("121", "tokenID有误或未登录，请先登录认证后再执行！");
                if (!StringUtils.isEmpty(api.getIs_log()) && api.getIs_log().equals("1")) {
                    writeLog(api, result, requestMap);
                }
                return result;
            }
            //将tokenID里的用户信息写入requestMap
            requestMap.put("CURRENTIP", userBean.getIp());
            requestMap.put("CURRENTUSERNAME", userBean.getToolApiUser().getUser_name());
            requestMap.put("CURRENTUSERID", userBean.getToolApiUser().getUser_id());
            requestMap.put("CURRENTTOKENID", userBean.getTokenID());
            if ((user.getHas_num() == null ? 0 : user.getHas_num()) < 0) {
                result = Result.failure("900", "剩余可访问次数不足，请联系系统管理员！");
                if (!StringUtils.isEmpty(api.getIs_log()) && api.getIs_log().equals("1")) {
                    writeLog(api, result, requestMap);
                }
                return result;
            }
            //判断是否有权访问
            ToolApiUserRule ur = new ToolApiUserRule();
            ur.setApi_id(api.getApi_id());
            ur.setUser_id(user.getUser_id());
            rule = toolApiUserRuleMapper.selectOne(ur);
            if (rule == null) {
                result = Result.failure("109", "无权访问该接口，请联系系统管理员！");
                if (!StringUtils.isEmpty(api.getIs_log()) && api.getIs_log().equals("1")) {
                    writeLog(api, result, requestMap);
                }
                return result;
            }
        }
        //执行API逻辑
        result= runAPI(requestMap,api);

        //扣减访问次数
        if(user!=null&&rule!=null&&result.isSuccess()){
            user.setHas_num((user.getHas_num()==null? 0 : user.getHas_num())-(rule.getEach_num()==null? 0 : rule.getEach_num()));
            user.setUsed_num((user.getUsed_num()==null? 0 : user.getUsed_num())+(rule.getEach_num()==null? 0 : rule.getEach_num()));
            toolApiUserMapper.updateByPrimaryKey(user);
        }
        if(!StringUtils.isEmpty(api.getIs_log())&&api.getIs_log().equals("1")){
            writeLog(api,result,requestMap);
        }

        return result;
    }

    /**
     * 记录log日志
     *
     */
    public void writeLog(ToolApi api,Result result,Map<String, Object> requestMap){
        ToolApiLog apiLog=new ToolApiLog();
        apiLog.setApi_id(getKeyUID());
        apiLog.setApi_name(api.getApi_name());
        apiLog.setApi_id(api.getApi_id());
        apiLog.setIs_success(result.isSuccess()?"1":"0");
        apiLog.setPost_parames(JSONObject.toJSONString(requestMap));
        apiLog.setOccur_date(new Date());
        apiLog.setLog_content(JSONObject.toJSONString(result.getMsg()));
        toolApiLogMapper.insertSelective(apiLog);
    }

    /**
     * 执行主API逻辑类，方便递归调用
     * @param requestMap
     * @param api
     * @return
     */
    public Result runAPI(Map<String, Object> requestMap,ToolApi api){

        //查单条结果API   onerow
       if(api.getApi_type().equals("onerow"))
           return oneRowQuery(requestMap,api);

        //查多条结果API   list
        if(api.getApi_type().equals("list"))
            return listRowQuery(requestMap,api);

        //新增单条数据API   addone
        if(api.getApi_type().equals("addone"))
            return oneRowAdd(requestMap,api);

        //新增多条数据API   addlist
        if(api.getApi_type().equals("addlist"))
            return listRowAdd(requestMap,api);

        //修改单条数据API   modifyone
        if(api.getApi_type().equals("modifyone"))
            return oneRowUpdate(requestMap,api);

        //修改多条数据API   modifylist
        if(api.getApi_type().equals("modifylist"))
            return listRowUpdate(requestMap,api);

        //删除单条数据API   deleteone
        if(api.getApi_type().equals("deleteone"))
            return oneRowDelete(requestMap,api);

        //删除多条数据API   deletelist
        if(api.getApi_type().equals("deletelist"))
            return listRowDelete(requestMap,api);

        return Result.failure("000","接口未定义，请联系系统管理员！");
    }

    /**
     * 判断传入参数是否符合要求
     * @return
     */
    public Result validateParam(Map<String, Object> requestMap,ToolApi api){
        ToolApiParam query =new ToolApiParam();
        query.setApi_id(api.getApi_id());
        List<ToolApiParam> paramList=toolApiParamMapper.select(query);
        if(paramList!=null) {//无定义参数则不判断
            for(ToolApiParam param:paramList){
                if(param.getParam_source().equals("param")) {//参数来源于传入
                    if (param.getIs_null().equals("1")) {//必填判断
                        if (StringUtils.isEmpty((String) requestMap.get(param.getParam_code()))) {//参数没有传入改值或为空
                            return Result.failure("301", "参数【" + param.getParam_code() + "】不能为空！");
                        }
                    }
                    if (!StringUtils.isEmpty(param.getCheck_regex())) {//判断是否符合正则表达式格式要求
                        Pattern pat = Pattern.compile(param.getCheck_regex());
                        Matcher mat = pat.matcher((String) requestMap.get(param.getParam_code()));
                        if (!mat.find()) {
                            return Result.failure("302", "参数【" + param.getParam_code() + "】不符合格式【" + param.getCheck_regex() + "】要求！");
                        }
                    }
                    if(param.getParam_type().equals("list")){//字段类型是list和map的处理一下
//                        List<Map> list = JSONObject.parseArray((String) requestMap.get(param.getParam_code()),Map.class);
                        String paramStr = requestMap.get(param.getParam_code()).toString();
                        List<String> list = Arrays.asList(paramStr.split(","));
                        requestMap.put(param.getParam_code(),list);
                    }else if(param.getParam_type().equals("map")){//字段类型是list和map的处理一下

                        Map  mp = JSONObject.parseObject((String) requestMap.get(param.getParam_code()),Map.class);
                        requestMap.put(param.getParam_code(),mp);
                    }

                    if(param.getChange_type().equals("data")){//数据字典

                    }else  if(param.getChange_type().equals("dept")){//部门

                    }else if(param.getChange_type().equals("user")){//用户

                    }else if(param.getChange_type().equals("member")){//会员

                    }
                }else if(param.getParam_source().equals("default")) {//参数来源于  默认值
                    if(param.getParam_default().equals("{CURRENTUSERID}")){//当前用户id
                        requestMap.put(param.getParam_code(),requestMap.get("CURRENTUSERID"));
                    }else if(param.getParam_default().equals("{CURRENTUSERNAME}")){//当前用户名
                        requestMap.put(param.getParam_code(),requestMap.get("CURRENTUSERNAME"));
                    }else if(param.getParam_default().equals("{CURRENTTOKENID}")){//当前Tokenid
                        requestMap.put(param.getParam_code(),requestMap.get("CURRENTTOKENID"));
                    }else if(param.getParam_default().equals("{CURRENTIP}")){//当前ip
                        requestMap.put(param.getParam_code(),requestMap.get("CURRENTIP"));
                    }else if(param.getParam_default().equals("{CURRENTDATE}")){//当前日期
                        requestMap.put(param.getParam_code(),DateUtils.getDate());
                    }else if(param.getParam_default().equals("{CURRENTDATETIME}")){//当前时间
                        requestMap.put(param.getParam_code(),DateUtils.getDateTime());
                    }else if(param.getParam_default().equals("{CURRENTYEARMONTH}")){//当前年月
                        requestMap.put(param.getParam_code(),DateUtils.getYear()+"-"+DateUtils.getMonth());
                    }else{
                        requestMap.put(param.getParam_code(), param.getParam_default());
                    }

                }

            }
        }
        //是否有定义,执行前判断sql或class
        if(!StringUtils.isEmpty(api.getPre_sql())){
            log.info("Exec getPre_sql ===" + api.getPre_sql());
            if(api.getPre_sql().startsWith("com.")){//class
                boolean r=(boolean) ExecClassMethodUtil.ExecMethod(api.getPre_sql(),true,requestMap) ;
                if(!r){
                    return Result.failure("303", "校验逻辑执行出错，请联系管理员！");
                }
            }else {
                SqlSession targetSession = getSession(api.getDbsource_name());
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {
                    targetSqlMapper.update(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getPre_sql()), requestMap);
                } catch (Exception e) {
                    log.error("304",e);
                    return Result.failure("304", "校验逻辑执行出错，请联系管理员！");
                    // throw e;
                } finally {
                    targetSession.close();
                }
            }
        }

        return Result.success();
    }

    /**
     * 单条数据查询 1
     * @return
     */
    public Result oneRowQuery(Map<String, Object> requestMap,ToolApi api){
        Map<String,Object> row=new HashMap<>();
        if(!StringUtils.isEmpty(api.getData_entity())){//基于Entity
            String keycolumn = getTableKeyIdColumn(api.getData_entity());//主键列
            String keyid = (String) requestMap.get(keycolumn);
            if(StringUtils.isEmpty(keyid)) {
                keyid=(String)requestMap.get("keyid");
                if(keyid!=null) requestMap.put(keycolumn,keyid);
            }

            requestMap.put("KEYID",keyid);

            try {
                BaseEntity query= (BaseEntity) Map2EntityUtil.createModel(getEntity(api.getData_entity()),requestMap);//将传入值更新

                DynamicDataSource.setDataSource(api.getDbsource_name());
                BaseEntity entity= (BaseEntity) getMapper(api.getData_entity()).selectOne(query);

                if (entity==null) {
                    return Result.failure("412", "记录不存在或查询条件有误！");
                }
                row= E2MapUtil.E2Map(entity,getEntity(api.getData_entity()));
            }catch (Exception e){
                log.error("413",e);
                return Result.failure("413", "查询记录出错！");
            }finally {
                DynamicDataSource.clearDataSource();
            }

        }else {
            if (api.getData_sql().startsWith("com.")) {//class
                Result r = (Result) ExecClassMethodUtil.ExecMethod(api.getData_sql(), true, requestMap);
                if (!r.isSuccess()) {
                    return r;
                }
                row= (Map<String, Object>) r.getData();
            } else {
                //基于SQL
                SqlSession targetSession = getSession(api.getDbsource_name());
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {
                    ;
                    Map<String, Object> newRow = targetSqlMapper.selectOne(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getData_sql()), requestMap);
                    if (newRow == null) {
                        return Result.failure("414", "记录不存在或查询条件有误，查询记录失败！");
                    }
                    //防止oracle返回key为大写
                    for (Map.Entry<String, Object> entry : newRow.entrySet()) {
                        row.put(entry.getKey().toLowerCase(), entry.getValue());
                    }
                } catch (Exception e) {
                    log.error("415",e);
                    return Result.failure("415", "查询记录出错，请联系管理员！");
                    // throw e;
                } finally {
                    targetSession.close();
                }
            }
        }

        //事件后逻辑执行
        Result postR=execPosSQL(requestMap,api);
        if(!postR.isSuccess()) return postR;
        //根据返回值范围处理数据
        if(row!=null){
            ToolApiReturn query=new ToolApiReturn();
            query.setApi_id(api.getApi_id());
            List<ToolApiReturn> returnList=toolApiReturnMapper.select(query);
            if(returnList!=null){
                Map<String,Object> newRow=new HashMap<String,Object>();
                for(ToolApiReturn rt:returnList){
                    Object value=row.get(rt.getColumn_code());
                   if(!StringUtils.isEmpty(rt.getColumn_default())){//空值处理
                       if(rt.getColumn_default().equals("{CURRENTDATE}")){//当前日期
                           value=DateUtils.getDate();
                       }else if(rt.getColumn_default().equals("{CURRENTDATETIME}")){//当前时间
                           value=DateUtils.getDateTime();
                       }else if(rt.getColumn_default().equals("{CURRENTYEARMONTH}")){//当前年月
                           value=DateUtils.getYear()+"-"+DateUtils.getMonth();
                       }else{
                           value=rt.getColumn_default();
                       }
                   }
                   //转换处理
                    if(rt.getChange_type().equals("data")){//数据字典

                    }else  if(rt.getChange_type().equals("dept")){//部门

                    }else if(rt.getChange_type().equals("user")){//用户

                    }else if(rt.getChange_type().equals("member")){//会员

                    }else {
                        newRow.put(rt.getColumn_code(), ConvertUtil.castFromObject(value, String.class));
                    }
                    //子API处理
                    if(!StringUtils.isEmpty(rt.getSub_api_id())){
                        ToolApi subApi=toolApiMapper.selectByPrimaryKey(rt.getSub_api_id());
                        if(subApi!=null) {
                            Result subRt = runAPI(row,subApi);
                            if(subRt.isSuccess()){
                                newRow.put(rt.getColumn_code(),subRt.getData());
                            }else{
                                newRow.put(rt.getColumn_code(),subRt.getCode()+":"+subRt.getMsg());
                            }
                        }
                    }
                }
                return Result.success(newRow);
            }
        }

        return Result.success(row);
    }


    /**
     * 多条数据查询 2
     * @return
     */
    public Result listRowQuery(Map<String, Object> requestMap,ToolApi api){
        List<Map<String,Object>> list=new ArrayList<>();
        if(!StringUtils.isEmpty(api.getData_entity())){//基于Entity
            String keycolumn = getTableKeyIdColumn(api.getData_entity());//主键列
            String keyid = (String) requestMap.get(keycolumn);
            if(StringUtils.isEmpty(keyid)) {
                keyid=(String)requestMap.get("keyid");
                if(keyid!=null) requestMap.put(keycolumn,keyid);
            }

            try {
                BaseEntity query= (BaseEntity) Map2EntityUtil.createModel(getEntity(api.getData_entity()),requestMap);//将传入值更新

                DynamicDataSource.setDataSource(api.getDbsource_name());
                List<BaseEntity> entitys= getMapper(api.getData_entity()).select(query);

                if (entitys==null) {
                    return Result.failure("422", "记录不存在或查询条件有误！");
                }
                for(BaseEntity entity:entitys) {
                   Map<String,Object> row = E2MapUtil.E2Map(entity, getEntity(api.getData_entity()));
                    list.add(row);
                }
            }catch (Exception e){
                log.error("423",e);
                return Result.failure("423", "查询记录出错！");
            }finally {
                DynamicDataSource.clearDataSource();
            }

        }else {
            if (api.getData_sql().startsWith("com.")) {//class
                Result r = (Result) ExecClassMethodUtil.ExecMethod(api.getData_sql(), true, requestMap);
                if (!r.isSuccess()) {
                    return r;
                }
                list= (List<Map<String, Object>>) r.getData();
            } else {
                //基于SQL
                SqlSession targetSession = getSession(api.getDbsource_name());
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {
                    ;
                    List<Map<String,Object>>  rowList = targetSqlMapper.selectList(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getData_sql()), requestMap);
                    if (rowList == null) {
                        return Result.failure("424", "记录不存在或查询条件有误，查询记录失败！");
                    }
                    for(Map<String, Object> row:rowList) {//防止oracle返回的key是大写
                        Map<String, Object> newRow = new HashMap<String, Object>();
                        for (Map.Entry<String, Object> entry : row.entrySet()) {
                            newRow.put(entry.getKey().toLowerCase(), entry.getValue());
                        }
                        list.add(newRow);
                    }
                } catch (Exception e) {
                    log.error("425",e);
                    return Result.failure("425", "查询记录出错，请联系管理员！");
                    // throw e;
                } finally {
                    targetSession.close();
                }
            }
        }

        //事件后逻辑执行
        Result postR=execPosSQL(requestMap,api);
        if(!postR.isSuccess()) return postR;
        //根据返回值范围处理数据
        if(list!=null){
            ToolApiReturn query=new ToolApiReturn();
            query.setApi_id(api.getApi_id());
            List<ToolApiReturn> returnList=toolApiReturnMapper.select(query);
            if(returnList!=null){
                List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
                for(Map<String, Object> row:list) {
                    Map<String, Object> newRow = new HashMap<String, Object>();
                    for (ToolApiReturn rt : returnList) {
                        Object value = row.get(rt.getColumn_code());
                        if (!StringUtils.isEmpty(rt.getColumn_default())) {//空值处理
                            if (rt.getColumn_default().equals("{CURRENTDATE}")) {//当前日期
                                value = DateUtils.getDate();
                            } else if (rt.getColumn_default().equals("{CURRENTDATETIME}")) {//当前时间
                                value = DateUtils.getDateTime();
                            } else if (rt.getColumn_default().equals("{CURRENTYEARMONTH}")) {//当前年月
                                value = DateUtils.getYear() + "-" + DateUtils.getMonth();
                            } else {
                                value = rt.getColumn_default();
                            }
                        }
                        //转换处理
                        if (rt.getChange_type().equals("data")) {//数据字典

                        } else if (rt.getChange_type().equals("dept")) {//部门

                        } else if (rt.getChange_type().equals("user")) {//用户

                        } else if (rt.getChange_type().equals("member")) {//会员

                        } else {
                            newRow.put(rt.getColumn_code(), ConvertUtil.castFromObject(value, String.class));
                        }
                        //子API处理
                        if (!StringUtils.isEmpty(rt.getSub_api_id())) {
                            ToolApi subApi = toolApiMapper.selectByPrimaryKey(rt.getSub_api_id());
                            if (subApi != null) {
                                Result subRt = runAPI(row, subApi);
                                if (subRt.isSuccess()) {
                                    newRow.put(rt.getColumn_code(), subRt.getData());
                                } else {
                                    newRow.put(rt.getColumn_code(), subRt.getCode() + ":" + subRt.getMsg());
                                }
                            }
                        }
                    }
                    newList.add(newRow);
                }
                return Result.success(newList);
            }
        }

        return Result.success(list);
    }

    /**
     * 单条数据插入 3
     * @return
     */
    public Result oneRowAdd(Map<String, Object> requestMap,ToolApi api){
        String keyid = null;
        if(!StringUtils.isEmpty(api.getData_entity())){//基于Entity
            keyid=(String)requestMap.get("keyid");
            if(StringUtils.isEmpty(keyid)) {
                String keycolumn = getTableKeyIdColumn(api.getData_entity());//主键列
                keyid = (String) requestMap.get(keycolumn);
                //未设置主键值，创建一个
                if(StringUtils.isEmpty(keyid)){
                    keyid=getKeyUID();
                    requestMap.put(keycolumn,keyid);
                }
                requestMap.put("KEYID",keyid);
            }
            DynamicDataSource.setDataSource(api.getDbsource_name());
            try {
                BaseEntity entity= (BaseEntity) getMapper(api.getData_entity()).selectByPrimaryKey(keyid);
                if(entity!=null){
                    return Result.failure("431", "记录已存在或id重复！"+keyid);
                }
                entity= (BaseEntity) Map2EntityUtil.createModel(getEntity(api.getData_entity()),requestMap);//将传入值更新
                int count=getMapper(api.getData_entity()).insertSelective(entity);
                if (count==0) {
                    return Result.failure("432", "新增记录失败！"+keyid);
                }
            }catch (Exception e){
                log.error("433",e);
                return Result.failure("433", "新增记录出错！"+keyid);
            }finally {
                DynamicDataSource.clearDataSource();
            }

        }else {
            requestMap.put("KEYID",getKeyUID());
            if (api.getData_sql().startsWith("com.")) {//class
                Result r = (Result) ExecClassMethodUtil.ExecMethod(api.getData_sql(), true, requestMap);
                if (!r.isSuccess()) {
                    return r;
                }
            } else {
                //基于SQL
                SqlSession targetSession = getSession(api.getDbsource_name());
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {
                    ;
                    int count = targetSqlMapper.insert(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getData_sql()), requestMap);
                    if (count == 0) {
                        return Result.failure("434", "记录已存在或id重复，新增记录失败！");
                    }
                } catch (Exception e) {
                    log.error("435",e);
                    return Result.failure("435", "增记录出错，请联系管理员！");
                    // throw e;
                } finally {
                    targetSession.close();
                }
            }
        }
        
        //事件后逻辑执行
        Result postR=execPosSQL(requestMap,api);
        if(!postR.isSuccess()) return postR;
        return Result.success(keyid);
    }


    /**
     * 多条数据插入 4
     * @return
     */
    public Result listRowAdd(Map<String, Object> requestMap,ToolApi api){
        List<Map<String,Object>> datas=(List<Map<String,Object>>)requestMap.get("datas");
        if(datas==null||datas.isEmpty()){
            return Result.failure("441", "无传入可新增数据！");
        }
        List<String> keyids=new ArrayList<>();
        for(Map<String,Object> mp:datas) {
            String keyid = null;
            if (!StringUtils.isEmpty(api.getData_entity())) {//基于Entity
                keyid = (String) mp.get("keyid");
                if (StringUtils.isEmpty(keyid)) {
                    String keycolumn = getTableKeyIdColumn(api.getData_entity());//主键列
                    keyid = (String) mp.get(keycolumn);
                    //未设置主键值，创建一个
                    if (StringUtils.isEmpty(keyid)) {
                        keyid = getKeyUID();
                        mp.put(keycolumn, keyid);
                    }
                }
                DynamicDataSource.setDataSource(api.getDbsource_name());
                try {
                    BaseEntity entity = (BaseEntity) getMapper(api.getData_entity()).selectByPrimaryKey(keyid);
                    if (entity != null) {
                        return Result.failure("442", "记录已存在或id重复！"+keyid);
                    }
                    entity = (BaseEntity) Map2EntityUtil.createModel(getEntity(api.getData_entity()), mp);//将传入值更新
                    int count = getMapper(api.getData_entity()).insertSelective(entity);
                    if (count == 0) {
                        return Result.failure("443", "新增记录失败！"+keyid);
                    }
                } catch (Exception e) {
                    log.error("444",e);
                    return Result.failure("444", "新增记录出错！"+keyid);
                } finally {
                    DynamicDataSource.clearDataSource();
                }

            } else {
                mp.put("KEYID",getKeyUID());
                if (api.getData_sql().startsWith("com.")) {//class
                    Result r = (Result) ExecClassMethodUtil.ExecMethod(api.getData_sql(), true, mp);
                    if (!r.isSuccess()) {
                        return r;
                    }
                } else {
                    //基于SQL
                    SqlSession targetSession = getSession(api.getDbsource_name());
                    SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                    try {
                        ;
                        int count = targetSqlMapper.insert(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getData_sql()), mp);
                        if (count == 0) {
                            return Result.failure("445", "记录已存在或id重复，新增记录失败！");
                        }
                    } catch (Exception e) {
                        log.error("446",e);
                        return Result.failure("446", "新增记录出错，请联系管理员！");
                        // throw e;
                    } finally {
                        targetSession.close();
                    }
                }
            }
            keyids.add(keyid);
        }

        //事件后逻辑执行
        Result postR=execPosSQL(requestMap,api);
        if(!postR.isSuccess()) return postR;
        return Result.success(keyids);
    }


    /**
     * 单条数据修改 5
     * @return
     */
    public Result oneRowUpdate(Map<String, Object> requestMap,ToolApi api){
        String keyid = null;
        if(!StringUtils.isEmpty(api.getData_entity())){//基于Entity
            keyid=(String)requestMap.get("keyid");
            if(StringUtils.isEmpty(keyid)) {
                String keycolumn = getTableKeyIdColumn(api.getData_entity());//主键列
                keyid = (String) requestMap.get(keycolumn);

                if(StringUtils.isEmpty(keyid)){
                    return Result.failure("451", "修改记录主键id为空！");
                }
            }
            requestMap.put("KEYID",keyid);
            DynamicDataSource.setDataSource(api.getDbsource_name());
            try {
                BaseEntity entity= (BaseEntity) getMapper(api.getData_entity()).selectByPrimaryKey(keyid);
                entity= Map2EntityUtil.updateModel(entity,requestMap);//将传入值更新
                int count=getMapper(api.getData_entity()).updateByPrimaryKey(entity);
                if (count==0) {
                    return Result.failure("452", "记录不存在或id错误，修改记录失败！");
                }
            }catch (Exception e){
                log.error("453",e);
                return Result.failure("453", "修改记录出错！");
            }finally {
                DynamicDataSource.clearDataSource();
            }

        }else {
            if (api.getData_sql().startsWith("com.")) {//class
                Result r = (Result) ExecClassMethodUtil.ExecMethod(api.getData_sql(), true, requestMap);
                if (!r.isSuccess()) {
                    return r;
                }
            } else {
                //基于SQL
                SqlSession targetSession = getSession(api.getDbsource_name());
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {
                    ;
                    int count = targetSqlMapper.update(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getData_sql()), requestMap);
                    if (count == 0) {
                        return Result.failure("454", "记录不存在或id错误，修改记录失败！");
                    }
                } catch (Exception e) {
                    log.error("455",e);
                    return Result.failure("455", "修改记录出错，请联系管理员！");
                    // throw e;
                } finally {
                    targetSession.close();
                }
            }
        }        
        
        //事件后逻辑执行
        Result postR=execPosSQL(requestMap,api);
        if(!postR.isSuccess()) return postR;
        return Result.success(keyid);
    }


    /**
     * 多条数据修改 6
     * @return
     */
    public Result listRowUpdate(Map<String, Object> requestMap,ToolApi api){
        List<Map<String,Object>> datas=(List<Map<String,Object>>)requestMap.get("datas");
        if(datas==null||datas.isEmpty()){
            return Result.failure("461", "无传入可修改数据！");
        }
        List<String> keyids=new ArrayList<>();
        for(Map<String,Object> mp:datas) {
            String keyid = null;
            if(!StringUtils.isEmpty(api.getData_entity())){//基于Entity
                keyid=(String)mp.get("keyid");
                if(StringUtils.isEmpty(keyid)) {
                    String keycolumn = getTableKeyIdColumn(api.getData_entity());//主键列
                    keyid = (String) mp.get(keycolumn);

                    if(StringUtils.isEmpty(keyid)){
                        return Result.failure("462", "修改记录主键id为空！");
                    }
                }
                DynamicDataSource.setDataSource(api.getDbsource_name());
                try {
                    BaseEntity entity= (BaseEntity) getMapper(api.getData_entity()).selectByPrimaryKey(keyid);
                    entity= Map2EntityUtil.updateModel(entity,mp);//将传入值更新
                    int count=getMapper(api.getData_entity()).updateByPrimaryKey(entity);
                    if (count==0) {
                        return Result.failure("463", "记录不存在或id错误，修改记录失败！"+keyid);
                    }
                }catch (Exception e){
                    log.error("464",e);
                    return Result.failure("464", "修改记录出错！"+keyid);
                }finally {
                    DynamicDataSource.clearDataSource();
                }

            }else {
                if (api.getData_sql().startsWith("com.")) {//class
                    Result r = (Result) ExecClassMethodUtil.ExecMethod(api.getData_sql(), true, mp);
                    if (!r.isSuccess()) {
                        return r;
                    }
                } else {
                    //基于SQL
                    SqlSession targetSession = getSession(api.getDbsource_name());
                    SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                    try {
                        ;
                        int count = targetSqlMapper.update(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getData_sql()), mp);
                        if (count == 0) {
                            return Result.failure("465", "记录不存在或id错误，修改记录失败！");
                        }
                    } catch (Exception e) {
                        log.error("466",e);
                        return Result.failure("466", "修改记录出错，请联系管理员！");
                        // throw e;
                    } finally {
                        targetSession.close();
                    }
                }
            }
            keyids.add(keyid);
        }
        //事件后逻辑执行
        Result postR=execPosSQL(requestMap,api);
        if(!postR.isSuccess()) return postR;
        return Result.success(keyids);
    }




    /**
     * 单条数据删除 7
     * @return
     */
    public Result oneRowDelete(Map<String, Object> requestMap,ToolApi api){
        String keyid = null;
        if(!StringUtils.isEmpty(api.getData_entity())){//基于Entity
            keyid=(String)requestMap.get("keyid");
            if(StringUtils.isEmpty(keyid)) {
                String keycolumn = getTableKeyIdColumn(api.getData_entity());//主键列
                keyid = (String) requestMap.get(keycolumn);

                if(StringUtils.isEmpty(keyid)){
                    return Result.failure("471", "删除记录主键id为空！");
                }
            }
            requestMap.put("KEYID",keyid);
            DynamicDataSource.setDataSource(api.getDbsource_name());
            try {
                int count=getMapper(api.getData_entity()).deleteByPrimaryKey(keyid);
                if (count ==0) {
                    return Result.failure("472", "记录不存在或id错误，删除记录失败！");
                }
            }catch (Exception e){
                log.error("473",e);
                return Result.failure("473", "删除记录出错！");
            }finally {
                DynamicDataSource.clearDataSource();
            }

        }else {
            if(api.getData_sql().startsWith("com.")){//class
                Result r=(Result) ExecClassMethodUtil.ExecMethod(api.getData_sql(),true,requestMap) ;
                if(!r.isSuccess()){
                    return r;
                }
            }else {
                //基于SQL
                SqlSession targetSession = getSession(api.getDbsource_name());
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {
                    int count = targetSqlMapper.delete(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getData_sql()), requestMap);
                    if (count == 0) {
                        return Result.failure("474", "记录不存在或id错误，删除记录失败！");
                    }
                } catch (Exception e) {
                    log.error("475",e);
                    return Result.failure("475", "删除记录出错，请联系管理员！");
                    // throw e;
                } finally {
                    targetSession.close();
                }
            }
        }
        
        //事件后逻辑执行
        Result postR=execPosSQL(requestMap,api);
        if(!postR.isSuccess()) return postR;
        return Result.success(keyid);
    }


    /**
     * 多条数据删除 8
     * @return
     */
    public Result listRowDelete(Map<String, Object> requestMap,ToolApi api){
        List<Map<String,Object>> datas=(List<Map<String,Object>>)requestMap.get("datas");
        if(datas==null||datas.isEmpty()){
            return Result.failure("481", "无传入可修改数据！");
        }
        List<String> keyids=new ArrayList<>();
        for(Map<String,Object> mp:datas) {
            String keyid = null;
            if (!StringUtils.isEmpty(api.getData_entity())) {//基于Entity
                keyid = (String) mp.get("keyid");
                if (StringUtils.isEmpty(keyid)) {
                    String keycolumn = getTableKeyIdColumn(api.getData_entity());//主键列
                    keyid = (String) mp.get(keycolumn);

                    if (StringUtils.isEmpty(keyid)) {
                        return Result.failure("482", "删除记录主键id为空！");
                    }
                }
                DynamicDataSource.setDataSource(api.getDbsource_name());
                try {
                    int count = getMapper(api.getData_entity()).deleteByPrimaryKey(keyid);
                    if (count == 0) {
                        return Result.failure("483", "记录不存在或id错误，删除记录失败！");
                    }
                } catch (Exception e) {
                    log.error("484",e);
                    return Result.failure("484", "删除记录出错！");
                } finally {
                    DynamicDataSource.clearDataSource();
                }

            } else {
                if (api.getData_sql().startsWith("com.")) {//class
                    Result r = (Result) ExecClassMethodUtil.ExecMethod(api.getData_sql(), true, mp);
                    if (!r.isSuccess()) {
                        return r;
                    }
                } else {
                    //基于SQL
                    SqlSession targetSession = getSession(api.getDbsource_name());
                    SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                    try {
                        int count = targetSqlMapper.delete(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getData_sql()), mp);
                        if (count == 0) {
                            return Result.failure("485", "记录不存在或id错误，删除记录失败！");
                        }
                    } catch (Exception e) {
                        log.error("486",e);
                        return Result.failure("486", "删除记录出错，请联系管理员！");
                        // throw e;
                    } finally {
                        targetSession.close();
                    }
                }
            }
            keyids.add(keyid);
        }
        //事件后逻辑执行
        Result postR=execPosSQL(requestMap,api);
        if(!postR.isSuccess()) return postR;
        return Result.success(keyids);
    }


    public Result execPosSQL(Map<String, Object> requestMap,ToolApi api){
        //是否有定义,执行sql或class
        if(!StringUtils.isEmpty(api.getPost_sql1())){
            log.info("Exec getPost_sql1 ===" + api.getPost_sql1());

            if(api.getPost_sql1().startsWith("com.")){//class
                boolean r=(boolean) ExecClassMethodUtil.ExecMethod(api.getPost_sql1(),true,requestMap) ;
                if(!r){
                    return Result.failure("491", "执行后业务处理出错1，请联系管理员！");
                }
            }else {
                SqlSession targetSession = getSession(api.getDbsource_name());
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {;
                    targetSqlMapper.update(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getPost_sql1()), requestMap);
                } catch (Exception e) {
                    log.error("492",e);
                    return Result.failure("492", "执行后业务处理出错1，请联系管理员！");
                    // throw e;
                } finally {
                    targetSession.close();
                }
            }
        }
        //是否有定义,执行sql或class
        if(!StringUtils.isEmpty(api.getPost_sql2())){
            log.info("Exec getPost_sql2 ===" + api.getPost_sql2());

            if(api.getPost_sql2().startsWith("com.")){//class
                boolean r=(boolean) ExecClassMethodUtil.ExecMethod(api.getPost_sql2(),true,requestMap) ;
                if(!r){
                    return Result.failure("493", "执行后业务处理出错2，请联系管理员！");
                }
            }else {
                SqlSession targetSession = getSession(api.getDbsource_name());
                SqlMapper targetSqlMapper = new SqlMapper(targetSession);
                try {
                    targetSqlMapper.update(SQLFormatUtil.formatSQL(getDBType(api.getDbsource_name()), api.getPost_sql2()), requestMap);
                } catch (Exception e) {
                    log.error("494",e);
                    return Result.failure("494", "执行后业务处理出错2，请联系管理员！");
                    // throw e;
                } finally {
                    targetSession.close();
                }
            }
        }
        return Result.success();
    }


    /**
     * API 用户登录
     * @param request
     * @return
     */
    public Result login(HttpServletRequest request) {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        Result result=new Result();

        String username =(String) requestMap.get("username");
        String password =(String) requestMap.get("password");
        String passwordSalt= passwordHelper.getEncryptPassword("duo","duodazhe123",password);
        //登录校验
        ToolApiUser query=new ToolApiUser();
        query.setUser_code(username);
        ToolApiUser user= toolApiUserMapper.selectOne(query);
        if(user==null){
            return Result.failure("101","用户不存在！");
        }
        if(!user.getUser_status().equals("1")){
            return Result.failure("102","帐号被锁定，请联系管理员！");
        }
        if(!user.getUser_password().equals(passwordSalt)){
            return Result.failure("103","密码错误！");
        }

        String ip = getIpAddr(request);
        log.info("登录："+username+"  ip:"+ip);

        if(!StringUtils.isEmpty(user.getLimit_ip())
                &&user.getLimit_ip().indexOf(ip)<0){
            return Result.failure("104","IP未被允许！");
        }

        if(user.getValid_date()!=null&&DateUtils.getDistanceOfTwoDate(user.getValid_date(),new Date())>0){
            return Result.failure("105","账户已过期！");
        }
        String tokenID="TK"+getKeyUID();
        log.info("tokenID："+tokenID);
        result.setSuccess(true);
        result.setData("{tokenID:"+tokenID+"}");

        user.setLast_ip(ip);
        APISessionBean bean=new APISessionBean();
        bean.setTokenID(tokenID);
        bean.setIp(ip);
        bean.setLoginTime(DateUtils.getDateTime());
        bean.setToolApiUser(user);

        RedisUtil.hset("ApiTokens",tokenID, JSON.toJSONString(bean));//将对象写入Redis
        toolApiUserMapper.updateByPrimaryKey(user);
        return result;
    }

    /**
     * 退出登录
     * @return 200/500
     */
    public Result logout(HttpServletRequest request) {
        Result result=new Result();

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String tokenID = (String) requestMap.get("tokenID");
        RedisUtil.hdel("AppTokens",tokenID);

        result.setSuccess(true);
        return result;
    }


    /**
     * 获取真实IP
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if(StringUtils.isEmpty(ip)) return null;
        return ip.split(",")[0];
    }

}
