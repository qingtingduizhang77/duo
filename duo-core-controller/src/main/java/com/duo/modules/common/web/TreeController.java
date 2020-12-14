package com.duo.modules.common.web;

import com.alibaba.fastjson.JSONObject;
import com.duo.core.BaseController;
import com.duo.core.TreeBean;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.TreeService;
import com.duo.modules.common.service.TreeQueryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 树形页面的   【增删改查】  实现 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Controller
@RequestMapping("/tree")
public class TreeController  extends BaseController {

    @Autowired
    TreeService treeService;

    @Autowired
    TreeQueryService treeServiceQuery;

    @ResponseBody
    @RequestMapping("/load")
    public List<TreeBean> findTree(HttpServletRequest request) throws Exception{
        Map<String, Object> requestMap =Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String tableName=(String) requestMap.get("tableName") ;
        String dbSource=(String) requestMap.get("dbSource") ;
        String pId=(String) requestMap.get("pId") ;
        String funId= (String) requestMap.get("funId")  ;
        return treeServiceQuery.findTree(tableName,pId,funId,dbSource);
    }


    @ResponseBody
    @RequestMapping("/loadnormal")
    public List<TreeBean> loadnormal(HttpServletRequest request) throws Exception{
        Map<String, Object> requestMap =Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String tableName=(String) requestMap.get("tableName") ;
        String pId=(String) requestMap.get("pId") ;
        String funId= (String) requestMap.get("funId")  ;
        return treeServiceQuery.findNormalTree(tableName,pId,funId);
    }

    @ResponseBody
    @PostMapping("/create")
    public Result<?> create(HttpServletRequest request) throws Exception{
        Map<String, Object> requestMap =Map2EntityUtil.request2Map(request);

        treeService.create(requestMap);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/update")
    public Result<?> update(HttpServletRequest request) throws Exception{
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        treeService.update(requestMap);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/delete")
    public Result<?> delete(HttpServletRequest request,@RequestParam("ids") String... ids) {
        Map<String, Object> requestMap =Map2EntityUtil.request2Map(request);

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        Arrays.asList(ids).forEach(id ->
                treeService.delete(requestMap,id)
        );

        return Result.success();
    }



    /**
     * copy
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
        treeService.copy(funId,ids.split(";"));

        return Result.success();
    }

    /**
     * copy
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
        treeService.deletes(funId,ids.split(";"));

        return Result.success();
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
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String datas= StringEscapeUtils.unescapeHtml((String)requestMap.get("datas"));// " 被转译 成了&quot问题
        logdb.info("datas::"+datas);
        List<Map> list = JSONObject.parseArray(datas,Map.class);
//        for(Map mp:list){
//            for (Object key : mp.keySet()) {
//                System.out.println("Key = " + key + ", Value = " + mp.get(key));
//            }
//        }
        String funId=(String)requestMap.get("funId");
        treeService.gridSave(funId,list);
        return Result.success();
    }
}
