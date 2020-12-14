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
import com.duo.modules.common.feign.CommonFeign;
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
import org.springframework.http.MediaType;
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
public class CommonFeignController extends BaseController {
    @Autowired
    private CommonFeign commonFeign;


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

        return commonFeign.listmp(request);
    }

    /**
     * 基于SQL查询结果集
     *      ——支持分页
     *      ——支持统计行
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/list")
    public PageResultSet<Map<String,Object>> list(@RequestParam("funId") String funId,HttpServletRequest request) throws Exception {

        return  commonFeign.list(funId,request);
    }


    /**
     * 复制记录
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/copy")
    public Result<?> copy(HttpServletRequest request)  throws Exception {

        return commonFeign.copy(request);

    }

    /**
     * 用于更新页面指定行传过来的值
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Result<?> update(HttpServletRequest request)  throws Exception {

        return commonFeign.update(request);

    }

    /**
     * 删除记录
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/deletes")
    public Result<?> deletes(HttpServletRequest request)  throws Exception {

        return commonFeign.deletes(request);
    }

    /**
     * copy
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/gridsave")
    public Result<?> gridsave(HttpServletRequest request) throws Exception  {

        return commonFeign.gridsave(request);
    }

    /**
     * copy
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/savefile")
    public Result<?> saveFile( HttpServletRequest request) throws Exception  {

        return commonFeign.saveFile(request);
    }
    /**
     * copy
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Result<?> save(HttpServletRequest request) throws Exception  {

        return commonFeign.save(request);
    }


    /**
     * 数据导入
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/dataimport")
    public Result<?> dataimport(HttpServletRequest request)  throws Exception {

       return commonFeign.dataimport(request);

    }


    /**
     * 批量修改
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/batchUpdate")
    public Result<?> batchUpdate(HttpServletRequest request)  throws Exception {

        return commonFeign.batchUpdate(request);
    }

}
