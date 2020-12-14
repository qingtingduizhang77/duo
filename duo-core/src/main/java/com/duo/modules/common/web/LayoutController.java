package com.duo.modules.common.web;

import com.duo.MemCache;
import com.duo.core.BaseController;
import com.duo.core.utils.StringUtils;
import com.duo.modules.common.service.LayoutService;
import com.duo.modules.tool.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @Description： EasyUI支持
 * @author [ Yonsin ] on [2019年1月31日上午10:39:37]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Controller
@RequestMapping("/layout")
public class LayoutController extends BaseController{


    @GetMapping
    @RequestMapping("/layout-common-grid")
    public String LayoutCommonGrid(Model model, HttpServletRequest request,@RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {
        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-grid";
    }


    @GetMapping
    @RequestMapping("/layout-common-stepgrid")
    public String LayoutCommonStepGrid(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {
        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-stepgrid";
    }

    @GetMapping
    @RequestMapping("/layout-common-itemlist")
    public String LayoutCommonItemList(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {
        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-itemlist";
    }


    @GetMapping
    @RequestMapping("/layout-common-charttab")
    public String LayoutCommonChartTab(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {
        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-charttab";
    }

    @GetMapping
    @RequestMapping("/layout-common-tree")
    public String LayoutCommonTree(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-tree";
    }


    @GetMapping
    @RequestMapping("/layout-common-tree-tabsubgrid")
    public String LayoutCommonTreeTabsubgrid(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-tree-tabsubgrid";
    }

    @GetMapping
    @RequestMapping("/layout-common-tabgrid")
    public String LayoutCommonTabgrid(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-tabgrid";
    }
    @GetMapping
    @RequestMapping("/layout-common-grid-tabsubgrid")
    public String LayoutCommonGridTabsubgrid(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-grid-tabsubgrid";
    }


    @GetMapping
    @RequestMapping("/layout-common-calendar")
    public String LayoutCommonCalendar(Model model, HttpServletRequest request) {

        return "/layout/layout-common-calendar";
    }


    @GetMapping
    @RequestMapping("/layout-common-photowall")
    public String LayoutCommonPhotowall(Model model, HttpServletRequest request) {

        return "/layout/layout-common-photowall";
    }



    @GetMapping
    @RequestMapping("/layout-common-iframe")
    public String LayoutIframeWindow(Model model, HttpServletRequest request,@RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {
        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "/layout/layout-common-iframe";
    }

}
