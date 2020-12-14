package com.duo.modules.redisadmin.web;

import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.PageResultSet;
import com.duo.core.vo.Result;
import com.duo.modules.redisadmin.entity.SysRedis;
import com.duo.modules.redisadmin.service.RedisAdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/11/2]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@RequestMapping("/redisadmin")
public class RedisAdminController {


    @Autowired
    private RedisAdminService redisAdminService;

//    @ModelAttribute
//    public SysRedis get(@RequestParam(required = false) SysRedis sysRedis) {
//        if (sysRedis == null) {
//            sysRedis = new SysRedis();
//        } else {
//            sysRedis = redisAdminService.get(sysRedis.getRedisKey());
//        }
//        return sysRedis;
//    }

    @ResponseBody
    @RequestMapping("/list")
    public PageResultSet<Map<String,Object>> list(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        return redisAdminService.findPage(request);
    }

    @ResponseBody
    @PostMapping("/save")
    @Transactional
    public Result<?>  save(SysRedis sysRedis, HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);

        redisAdminService.save(sysRedis);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/delete")
    @Transactional
    public Result<?>  delete(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String ids= (String) requestMap.get("ids");
        for(String redisKey:ids.split(";")) {
            if(redisKey==null||redisKey.equals("")) continue;
            redisAdminService.del(redisKey);
        }
        return Result.success();
    }

    /**
     * 删除集合里的元素
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/remove")
    @Transactional
    public Result<?>  remove(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String redisKey= (String) requestMap.get("redisKey");
        redisAdminService.remove(redisKey);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/rename")
    @Transactional
    public Result<?>  rename(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String oldRedisKey= (String) requestMap.get("oldRedisKey");
        String redisKey= (String) requestMap.get("redisKey");
        if (!oldRedisKey.equals(redisKey)) {
            redisAdminService.rename(oldRedisKey,redisKey);
        }
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/updateExpire")
    @Transactional
    public Result<?>  updateExpire(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        String redisKey= (String) requestMap.get("redisKey");
        String expire= (String) requestMap.get("expire");
        redisAdminService.updateExpire(redisKey,expire);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/addValue")
    @Transactional
    public Result<?> addValue(SysRedis sysRedis,HttpServletRequest request) {
        redisAdminService.addValue(sysRedis);
        return Result.success();
    }
}
