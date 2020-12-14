package com.duo.modules.common.web;

import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.BaseController;
import com.duo.core.SpringContextHolder;
import com.duo.core.utils.Map2EntityUtil;
import com.duo.core.utils.Xls2HtmlUtil;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.DBMSService;
import com.duo.modules.common.service.ReportService;
import com.duo.modules.tool.entity.ToolReport;
import com.duo.modules.tool.mapper.ToolReportMapper;
import lombok.extern.slf4j.Slf4j;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 *
 * @Description： EasyUI支持
 * @author [ Yonsin ] on [2019年1月31日上午10:39:37]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
@Controller
@RequestMapping("/tool")
public class ReportController extends BaseController{

    @Autowired
    private ReportService reportService;
    @Autowired
    private ToolReportMapper toolReportMapper;//
    //在配置文件中配置的文件保存路径
    private static SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormatProperties");



    @GetMapping
    @RequestMapping("/page-tool-reportdesign")
    public String page_tool_reportdesign(Model model, HttpServletRequest request) throws FileNotFoundException {


        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String report_id = (String) requestMap.get("foreignKeyId");
        log.info("report_id==========="+report_id);
        model.addAttribute("report_id",report_id);
        //获取模版
        if (report_id == null || report_id.equals("")) {
            model.addAttribute("templateHTML","<未定义模版>");
        }else{
            ToolReport report = toolReportMapper.selectByPrimaryKey(report_id);
            String fileUrl=sqlprop.getFilePath()+report.getReport_file();//System.getProperties().get("user.dir").toString();
            log.info("fileUrl==========="+fileUrl);
            File file=new File(fileUrl);
            if(file.exists()) {
                model.addAttribute("templateHTML", Xls2HtmlUtil.getExcelToHtml(new FileInputStream(file)));
            }else{
                model.addAttribute("templateHTML","<模版【"+fileUrl+"】不存在！>");
            }
        }

        return "tool/page-tool-reportdesign";
    }

    //根据ReportID取区域列表
    @ResponseBody
    @RequestMapping("/getAreaList")
    public Result<?> getAreaList(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.getAreaList(requestMap);
    }


    //根据AreaID取参数列表
    @ResponseBody
    @RequestMapping("/getAreaParamList")
    public Result<?> getAreaParamList(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.getAreaParamList(requestMap);
    }


    //根据AreaID取字段列表
    @ResponseBody
    @RequestMapping("/getAreaColumnList")
    public Result<?> getAreaColumnList(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.getAreaColumnList(requestMap);
    }


    //根据AreaID删除区域及明细
    @ResponseBody
    @RequestMapping("/deleteArea")
    public Result<?> deleteArea(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.deleteArea(requestMap);
    }


    //根据ParamID删除参数
    @ResponseBody
    @RequestMapping("/deleteParam")
    public Result<?> deleteParam(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.deleteAreaParam(requestMap);
    }


    //根据ParamID删除字段
    @ResponseBody
    @RequestMapping("/deleteColumn")
    public Result<?> deleteColumn(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.deleteAreaColumn(requestMap);
    }


    //新增/修改区域数据
    @ResponseBody
    @RequestMapping("/saveArea")
    public Result<?> saveArea(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.areaSave(requestMap);
    }


    //新增/修改参数
    @ResponseBody
    @RequestMapping("/saveParam")
    public Result<?> saveParam(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.areaParamSave(requestMap);
    }


    //新增/修改字段
    @ResponseBody
    @RequestMapping("/saveColumn")
    public Result<?> saveColumn(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        return reportService.areaColumnSave(requestMap);
    }

}
