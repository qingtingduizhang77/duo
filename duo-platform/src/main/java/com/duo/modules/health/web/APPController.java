package com.duo.modules.health.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.MemCache;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseEntity;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.PageResultSet;
import com.duo.modules.common.service.CommonQueryService;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.health.entity.DeviceInfo;
import com.duo.modules.tool.entity.ToolFunction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author [ Yonsin ] on [2020/3/24]
 * @Description： [ 用于APP连接接口,设备要先签到判断ip是否是授权终端，app要登录帐号 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 * 直接通过浏览器输入url时，@RequestBody获取不到json对象，需要用java编程或者基于ajax的方法请求，将Content-Type设置为application/json
 */
@CrossOrigin
@RestController
@RequestMapping("/app")
@Api(tags="app类",value="app接口类")
@Slf4j
public class APPController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private CommonQueryService commonQueryService;
    @Autowired
    private LayoutService layoutService;

    private static Map<String,String> tablesMp=new  HashMap<String,String>();

    static {
        tablesMp.put("device","device_info");//设备
        tablesMp.put("user","system_user");//用户
        tablesMp.put("order","order_record");//订单
        tablesMp.put("dept","system_dept");//部门
        tablesMp.put("goods","goods_list");//商品
        tablesMp.put("goodstype","goods_type");//商品分类
    }
    /**
     * 登录用户
     * @param request
     * @return Json
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login( HttpServletRequest request) {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        Map<String, Object> result=new HashMap<String, Object>();

        String username =(String) requestMap.get("username");
        String password =(String) requestMap.get("password");
        String ip = getIpAddr(request);
        log.info("登录："+username+"  ip:"+ip);
        String tokenID=layoutService.getKeyUID();
        log.info("tokenID："+tokenID);
        result.put("success",true);
        result.put("message","");
        result.put("tokenID",tokenID);
        RedisUtil.hset("AppTokens",tokenID,ip);
        return JSON.toJSONString(result);
    }

    /**
     * 退出登录
     * @return 200/500
     */
    @RequestMapping(value = "/logout",method={RequestMethod.POST})
    public String logout(HttpServletRequest request) {

        Map<String, Object> result=new HashMap<String, Object>();
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String tokenID = (String) requestMap.get("tokenID");
        RedisUtil.hdel("AppTokens",tokenID);

        result.put("success",true);
        result.put("message","");
        return JSON.toJSONString(result);
    }


    /**
     * 设备签到
     * @param request
     * @return Json
     */
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public String sign(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        Map<String, Object> result=new HashMap<String, Object>();

        String device_id =(String) requestMap.get("device_id");
        String ip = getIpAddr(request);
        //查询ip是否在授权范围内

        log.info("登录："+device_id+"  ip:"+ip);
        String tokenID=layoutService.getKeyUID();
        log.info("tokenID："+tokenID);
        result.put("success",true);
        result.put("message","");
        result.put("tokenID",tokenID);
        RedisUtil.hset("AppTokens",tokenID,ip);
        return JSON.toJSONString(result);
    }


    /**
     * 获取数据列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDataList",method={RequestMethod.POST})
    public String getDataList(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        PageResultSet result=new PageResultSet();
        result.setSuccess(false);
        if(requestMap==null) requestMap=new HashMap<String, Object>();

        //操作数据类型
        String dataType=(String)requestMap.get("dataType");
        if(StringUtils.isEmpty(dataType)){
            result.setSuccess(false);
            result.setMessage("缺少dataType！");
            return JSON.toJSONString(result);
        }
        String funID=tablesMp.get(dataType);
        if(StringUtils.isEmpty(funID)){
            result.setSuccess(false);
            result.setMessage("dataType有误！");
            return JSON.toJSONString(result);
        }
        //判断tokenID是否存在
        Map<String, Object>   loginresult=isLogin(requestMap,request);
        if(!Boolean.parseBoolean(String.valueOf(loginresult.get("success")))) return JSON.toJSONString(loginresult);

        try {
             result=commonQueryService.findBySQLPage(funID,requestMap);
             result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询数据出错！");
        }

        return JSON.toJSONString(result);
    }

    /**
     * 获取单个数据信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDeviceInfo",method={RequestMethod.POST})
    public String getDataInfo(HttpServletRequest request){
        Map<String, Object> result=new HashMap<String, Object>();
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        if(requestMap==null) requestMap=new HashMap<String, Object>();

        //操作数据类型
        String dataType=(String)requestMap.get("dataType");
        if(StringUtils.isEmpty(dataType)){
            result.put("success",false);
            result.put("message","缺少dataType！");
            return JSON.toJSONString(result);
        }
        String funID=tablesMp.get(dataType);
        if(StringUtils.isEmpty(funID)){
            result.put("success",false);
            result.put("message","dataType有误！");
            return JSON.toJSONString(result);
        }
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funID);
        String tableName=funInfo.getTable_name().trim();
        String dbSource=funInfo.getDb_source().trim();

        //判断tokenID是否存在
        Map<String, Object>   loginresult=isLogin(requestMap,request);
        if(!Boolean.parseBoolean(String.valueOf(loginresult.get("success")))) return JSON.toJSONString(loginresult);

        DynamicDataSource.setDataSource(dbSource);//设置指定数据源
        try {
            String keycolumn = commonService.getTableKeyIdColumn(tableName);//主键列
            log.info(dataType+"  keycolumn=========="+keycolumn);
            String keyid=(String)requestMap.get(keycolumn);
            BaseEntity  entity = (BaseEntity) commonService.getMapper(tableName).selectByPrimaryKey(keyid);
            result.put("data",entity);
            result.put("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("message","查询数据出错！");
        }finally {
            DynamicDataSource.clearDataSource();
        }

        return JSON.toJSONString(result);
    }



    /**
     * 单个信息反馈写入
     * @param request
     * @return
     */
    @RequestMapping(value = "/setDataInfo",method={RequestMethod.POST})
    public String setDataInfo(HttpServletRequest request){
        Map<String, Object> result=new HashMap<String, Object>();
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        //操作数据类型
        String dataType=(String)requestMap.get("dataType");
        if(StringUtils.isEmpty(dataType)){
            result.put("success",false);
            result.put("message","缺少dataType！");
            return JSON.toJSONString(result);
        }
        String funID=tablesMp.get(dataType);
        if(StringUtils.isEmpty(funID)){
            result.put("success",false);
            result.put("message","dataType有误！");
            return JSON.toJSONString(result);
        }
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funID);
        String tableName=funInfo.getTable_name().trim();
        String dbSource=funInfo.getDb_source().trim();

        if(requestMap==null) requestMap=new HashMap<String, Object>();
        //判断tokenID是否存在
        Map<String, Object>   loginresult=isLogin(requestMap,request);
        if(!Boolean.parseBoolean(String.valueOf(loginresult.get("success")))) return JSON.toJSONString(loginresult);

        DynamicDataSource.setDataSource(dbSource);//设置指定数据源
        try {

            String keycolumn = commonService.getTableKeyIdColumn(tableName);//主键列
            log.info(dataType+"  keycolumn=========="+keycolumn);
            String keyid=(String)requestMap.get(keycolumn);
            log.info(keycolumn+"============"+keyid);
            if (StringUtils.isEmpty(keyid)) {
                requestMap.put(keycolumn, commonService.getKeyUID());
            }

            //通过页面参数创建对象
            Class clazz = MemCache._entitys.get(tableName);
            BaseEntity  entity = (BaseEntity) Map2EntityUtil.createModel(clazz, requestMap);
            result.put("data",entity);
            result.put("success",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success",false);
            result.put("message","写入数据出错！");
        }finally {
            DynamicDataSource.clearDataSource();
        }

        return JSON.toJSONString(result);
    }


    /**
     * 用于判断是否已登录
     * @param requestMap
     * @return
     */
    public Map<String, Object> isLogin(Map<String, Object> requestMap, HttpServletRequest request){
        Map<String, Object> result=new HashMap<String, Object>();
        String tokenID = (String) requestMap.get("tokenID");
        String ip = getIpAddr(request);
        if(tokenID==null||tokenID.equals("")) {//缺少tokenID
            result.put("success",false);
            result.put("message","缺少tokenID！");
            return result;
        }
        String machineIP=RedisUtil.hasKey("AppTokens")? (String)RedisUtil.hget("AppTokens",tokenID):null;
        //获取TokenID所对应的设备id信息是否和上次登录的一致
        log.info("machineIP："+machineIP+"  ip:"+ip);
        if(machineIP.equals(ip))
             result.put("success",true);
        else
            result.put("success",false);
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