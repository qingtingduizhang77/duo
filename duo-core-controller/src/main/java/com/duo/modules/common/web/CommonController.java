package com.duo.modules.common.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.*;
import com.duo.core.utils.JsonUtil;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.PageResultSet;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.CommonQueryService;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.tool.entity.ToolFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ 通用页面的   【增删改查】  实现]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Controller
@RequestMapping("/common")
public class CommonController  extends BaseController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private CommonQueryService commonQueryService;


    /**
     * 基于SQL查询结果集
     *      ——支持分页
     *      ——支持统计行
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/listmp")
    public PageResultSet<Map<String,Object>> listmp(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        return  commonQueryService.findBySQLPageMp(requestMap);
    }

    /**
     * 基于SQL查询结果集
     *      ——支持分页
     *      ——支持统计行
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageResultSet<Map<String,Object>> list(@RequestParam("funId") String funId,HttpServletRequest request) throws Exception {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        log.info("sessionid======="+request.getSession().getId());
        return  commonQueryService.findBySQLPage(funId,requestMap);
    }


    /**
     * 复制记录
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/copy")
//    @Transactional
    public Result<?> copy(HttpServletRequest request)  throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String ids=(String)requestMap.get("ids");
        String funId=(String)requestMap.get("funId");
        return commonService.copy(funId,ids.split(";"),requestMap);

    }

    /**
     * 用于更新页面指定行传过来的值
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
//    @Transactional
    public Result<?> update(HttpServletRequest request)  throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        if(!requestMap.containsKey("ids")||!requestMap.containsKey("funId")){
            return Result.failure("500","参数错误！");
        }

        String ids=(String)requestMap.get("ids");
        return commonService.update(ids.split(";"),requestMap);

    }

    /**
     * 用于更新页面指定行传过来的值
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/exec")
//    @Transactional
    public Result<?> exec(HttpServletRequest request)  throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        return commonService.exec(requestMap);

    }

    /**
     * 删除记录
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/deletes")
//    @Transactional
    public Result<?> deletes(HttpServletRequest request)  throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String ids=(String)requestMap.get("ids");
        String funId=(String)requestMap.get("funId");
        return commonService.delete(funId,ids.split(";"),requestMap);

    }

    /**
     * copy
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/gridsave")
//    @Transactional
    public Result<?> gridsave(HttpServletRequest request) throws Exception  {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String datas= StringEscapeUtils.unescapeHtml((String)requestMap.get("datas"));// " 被转译 成了&quot问题
        logdb.info("datas::"+datas);
        List<Map> list = JSONObject.parseArray(datas,Map.class);
//        for(Map mp:list){
//            for (Object key : mp.keySet()) {
//                System.out.println("Key = " + key + ", Value = " + mp.get(key));
//            }
//        }
        String funId=(String)requestMap.get("funId");
        String tableName=(String)requestMap.get("tableName");


        return commonService.gridSave(funId,tableName,list,requestMap);
    }

    /**
     * copy
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/savefile")
//    @Transactional
    public Result<?> saveFile( HttpServletRequest request) throws Exception  {

        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart( request);
        Map<String, MultipartFile> fileMap = new HashMap<>();//multipartRequest.getFileMap();
        MultipartFile imgFile  =  multipartRequest.getFile("file");
        logdb.info("CommonService imgFile:" + imgFile.getName()  );
        fileMap.put("file",imgFile);
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String funId=(String)requestMap.get("funId");
        logdb.info("CommonService saveFile:" + funId  );
        logdb.info("CommonService saveFile:" + (fileMap==null?"0":fileMap.size() ) );
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        commonService.saveFile(fileMap,request,funId,requestMap);
        return Result.success();
    }
    /**
     * copy
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
//    @Transactional
    public Result<?> save(HttpServletRequest request) throws Exception  {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String funId=(String)requestMap.get("funId");
        logdb.info("CommonService save:" + funId  );
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        return commonService.save(funId,requestMap);
    }


    /**
     * 数据导入
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/dataimport")
//    @Transactional
    public Result<?> dataimport(HttpServletRequest request)  throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String ids=(String)requestMap.get("ids");
        String eventId=(String)requestMap.get("eventId");
        String sourceFunId=(String)requestMap.get("sourceFunId");
        String targetFunId=(String)requestMap.get("targetFunId");
        String importforeignKeyId=(String)requestMap.get("importforeignKeyId");
        String selectId=(String)requestMap.get("selectId");
       return commonService.dataflow(eventId,sourceFunId,targetFunId,importforeignKeyId,ids.split(";"),selectId);

    }


    /**
     * 批量修改
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/batchUpdate")
//    @Transactional
    public Result<?> batchUpdate(HttpServletRequest request)  throws Exception {

        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String ids=(String)requestMap.get("ids");
        String funId=(String)requestMap.get("funId");
        commonService.batchUpdate(funId,ids.split(";"),requestMap);

        return Result.success();
    }


    /**
     * 获取单号
     */
    @ResponseBody
    @RequestMapping("/getCode")
    public Result<?> getCode(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        Result data=new Result();
        String funId=(String)requestMap.get("funId");
        String colName=(String)requestMap.get("colName");
        String code=commonService.getRuleCode(funId,colName,requestMap);
        data.setSuccess(true);
        data.setData(code);
        return data;
    }

}
