package com.duo.modules.common.web;

import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.BaseController;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.ShiroUtils;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.common.service.DBMSService;
import com.duo.modules.tool.entity.*;
import com.duo.modules.tool.mapper.*;
import com.duo.modules.view.entity.ViewFactTableinfo;
import com.duo.modules.view.mapper.ViewFactTableinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description： EasyUI支持
 * @author [ Yonsin ] on [2019年1月31日上午10:39:37]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Controller
@RequestMapping("/tool")
public class DBMSController extends BaseController{

    @Autowired
    private DBMSService dBMSService;


    @GetMapping
    @RequestMapping("/page-tool-table")
    public String page_tool_table(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "tool/page-tool-table";
    }


    //同步表信息
    @ResponseBody
    @RequestMapping("/syn")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {java.lang.RuntimeException.class ,Exception.class})
    public Result<?> syn(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
        dBMSService.syn(requestMap);
        return Result.success();
    }

}
