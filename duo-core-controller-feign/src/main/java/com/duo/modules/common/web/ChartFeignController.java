package com.duo.modules.common.web;

import com.alibaba.fastjson.JSON;
import com.duo.core.BaseController;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.vo.Result;
import com.duo.modules.common.feign.ChartFeign;
import com.duo.modules.common.service.ChartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/8]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Controller
@RequestMapping("/common")
@Slf4j
public class ChartFeignController extends BaseController {
    @Autowired
    private ChartFeign chartFeign;

    /**
     * 获取图标数据
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping(value="/chart")
    public Result<?> chart(HttpServletRequest request)  throws Exception {

        return chartFeign.chart(request);

    }
}
