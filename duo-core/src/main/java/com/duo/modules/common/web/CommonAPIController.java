package com.duo.modules.common.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.RedisUtil;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.CommonAPIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ 通用API接口工具 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
//@EnableEurekaClient
@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class CommonAPIController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private CommonAPIService commonAPIService;

    @RequestMapping(value = "/{project}/{apiurl}")
    public Result APIMain(HttpServletRequest request,@PathVariable String project, @PathVariable String apiurl){
        return commonAPIService.mainAPI(request,project,apiurl);
    }


}