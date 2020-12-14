package com.duo.modules.common.web;

import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.config.properties.SQLFormatProperties;
import com.duo.core.BaseController;
import com.duo.core.SpringContextHolder;
import com.duo.core.utils.*;
import com.duo.core.vo.Result;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.system.entity.SystemAttachment;
import com.duo.modules.system.mapper.SystemAttachmentMapper;
import com.duo.modules.tool.entity.*;
import com.duo.modules.tool.mapper.ToolReportMapper;
import com.github.abel533.sql.SqlMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.duo.modules.common.service.ToolService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 
 * @Description： 开发工具
 * @author [ Yonsin ] on [2019年1月31日上午10:39:37]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
@Controller
@RequestMapping("/tool")
public class ToolController extends BaseController{

    //在配置文件中配置的文件保存路径
    private static SQLFormatProperties sqlprop= SpringContextHolder.getBean("SQLFormatProperties");
    @Autowired
    private CommonService commonService;
    @Autowired
    private ToolService toolService;

    @GetMapping
    @RequestMapping("/page-tool-formdesign")
    public String page_tool_formdesign(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        String fkeyid=request.getParameter("foreignKeyId");
        List<ToolFunctionColumn> columnList=layoutService.getFunctionColumn(fkeyid);
        StringBuffer selectColumnList=new StringBuffer("");
        for(ToolFunctionColumn column:columnList){
            selectColumnList.append("<tr><td style=\"width:32%;\">")
                    .append(column.getColumn_name())
                    .append("</td><td style=\"width:35%;\">")
                    .append(column.getColumn_comment())
                    .append("</td><td style=\"width:20%;\">")
                    .append(column.getColumn_type())
                    .append("</td><td style=\"width:20%;display:none;\">")
                    .append(column.getColumn_id())
                    .append("</td></tr>");
        }
        // log.info("selectColumnList:::"+selectColumnList);
        model.addAttribute("selectColumnList",selectColumnList.toString());
        ToolFunction funinfo=layoutService.getFunctionInfo(fkeyid);
        model.addAttribute("datas",StringUtils.emptyToDefault(funinfo.getForm_map(),"[]"));
        return "tool/page-tool-formdesign";
    }


    @GetMapping
    @RequestMapping("/page-tool-formdesign-xls")
    public String page_tool_formdesign_xls(Model model, HttpServletRequest request, @RequestParam("funId") String funId) throws FileNotFoundException {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//

        if(!layoutService.initFuntionInfo(model,request,funId,"grid")) return "/505";
        String fkeyid=request.getParameter("foreignKeyId");
        List<ToolFunctionColumn> columnList=layoutService.getFunctionColumn(fkeyid);
        StringBuffer selectColumnList=new StringBuffer("");
        for(ToolFunctionColumn column:columnList){
            selectColumnList.append("<tr><td style=\"width:32%;\">")
                    .append(column.getColumn_name())
                    .append("</td><td style=\"width:35%;\">")
                    .append(column.getColumn_comment())
                    .append("</td><td style=\"width:20%;\">")
                    .append(column.getColumn_type())
                    .append("</td><td style=\"width:20%;display:none;\">")
                    .append(column.getColumn_id())
                    .append("</td></tr>");
        }
        // log.info("selectColumnList:::"+selectColumnList);
        model.addAttribute("selectColumnList",selectColumnList.toString());

        String filePath = (String) requestMap.get("filePath");
        log.info("filePath==========="+filePath);
        //获取模版
        if (filePath == null || filePath.equals("")) {
            model.addAttribute("templateHTML","<未定义模版>");
        }else{
            String fileUrl=sqlprop.getFilePath()+filePath;//System.getProperties().get("user.dir").toString();
            log.info("fileUrl==========="+fileUrl);
            File file=new File(fileUrl);
            if(file.exists()) {
                model.addAttribute("templateHTML", Xls2HtmlUtil.getExcelToHtml(new FileInputStream(file)));
            }else{
                model.addAttribute("templateHTML","<模版【"+fileUrl+"】不存在！>");
            }
        }

        return "tool/page-tool-formdesign-xls";
    }

    @GetMapping
    @RequestMapping("/page-tool-griddesign")
    public String page_tool_griddesign(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        String fkeyid=request.getParameter("foreignKeyId");
        List<ToolFunctionColumn> columnList=layoutService.getFunctionColumn(fkeyid);
        StringBuffer selectColumnList=new StringBuffer("");
        for(ToolFunctionColumn column:columnList){
            selectColumnList.append("<tr><td style=\"width:13%;\"><input type=\"checkbox\" "+(StringUtils.isNotEmpty(column.getIs_show())&&column.getIs_show().equals("1")?"checked":"")+"/></td><td style=\"width:30%;\">")
                    .append(column.getColumn_name())
                    .append("</td><td style=\"width:30%;\">")
                    .append(column.getColumn_comment())
                    .append("</td><td style=\"width:30%;\">")
                    .append(column.getColumn_width()==null||column.getColumn_width()==0?200:column.getColumn_width())
                    .append("</td><td style=\"width:20%;display:none;\">")
                    .append(column.getColumn_id())
                    .append("</td></tr>");
        }
       // log.info("selectColumnList:::"+selectColumnList);
        model.addAttribute("selectColumnList",selectColumnList.toString());
        return "tool/page-tool-griddesign";
    }

    @GetMapping
    @RequestMapping("/page-tool-function")
    public String page_tool_function(Model model, HttpServletRequest request, @RequestParam("funId") String funId, @RequestParam(value="pageType",defaultValue = "grid") String pageType) {

        if(!layoutService.initFuntionInfo(model,request,funId,pageType)) return "/505";
        return "tool/page-tool-function";
    }

    /**
     * 用于表设计器生成Entity和Mapper文件
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/entity")
    public Result<?> entity(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String entitypath= (String) requestMap.get("entitypath");
        String mapperpath= (String) requestMap.get("mapperpath");
        String entityFileName= (String) requestMap.get("entityFileName");
        String entityFileBody= (String) requestMap.get("entityFileBody");
        String mapperFileName= (String) requestMap.get("mapperFileName");
        String mapperFileBody= (String) requestMap.get("mapperFileBody");
        entityFileBody=StringEscapeUtils.unescapeHtml(entityFileBody);
        logdb.info(entityFileBody);
        mapperFileBody=StringEscapeUtils.unescapeHtml(mapperFileBody);
        logdb.info(mapperFileBody);
        FileUtils.createUTF8File(entitypath,entityFileName,entityFileBody,false);
        FileUtils.createUTF8File(mapperpath,mapperFileName,mapperFileBody,false);
        return Result.success();
    }



    //保存Form设计图  生成form页面文件
    @ResponseBody
    @RequestMapping("/formupdate")
    public Result<?> formupdate(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);
       return toolService.createFormFile(requestMap);
    }



    //生成Grid页面
    @ResponseBody
    @RequestMapping("/saveToFile")
    public Result<?> saveToFile(HttpServletRequest request) {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        return toolService.createGridFile(requestMap);
    }


    //设置ICO
    @ResponseBody
    @RequestMapping("/setico")
    public Result<?> setico(HttpServletRequest request, @RequestParam("tableName") String tableName,
                            @RequestParam("dataID") String dataID, @RequestParam("columnName") String columnName, @RequestParam("icoclass") String icoclass) {
        String keycolumn=commonService.getTableKeyIdColumn(tableName);
        SqlSession session =commonService.getSession(DataSourceNames.PLATFORM);
        SqlMapper sqlMapper=new SqlMapper(session);
        try {
            sqlMapper.update("update "+tableName+" set "+columnName+"='"+icoclass+"' where "+keycolumn+"='"+dataID+"'");

        }catch (Exception e){
            logdb.error("执行sql出错！",e);
            return Result.failure("500","执行sql出错！");
        }finally {
            session.close();
        }
        return Result.success();
    }

    //将数据字典+选择窗口生成Formater
    @ResponseBody
    @RequestMapping("/formatter")
    public Result<?> formatter(HttpServletRequest request){
      return toolService.createDataFormatter();
    }

    /**
     * 建表工具导入表字段
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/importcolumn")
    public Result<?> importcolumn(HttpServletRequest request){
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        String funId=(String)requestMap.get("foreignKeyId");

        String dbSource=DataSourceNames.PLATFORM;
        SqlSession session = commonService.getSession(dbSource);
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        String tableName=funInfo.getTable_name();
        SqlMapper sqlMapper = new SqlMapper(session);
        try {
            List<Map<String, Object>> dataList = sqlMapper.selectList("SELECT column_name, column_name  AS column_statement,column_comment,column_type,column_length FROM tool_table_column,tool_table WHERE tool_table_column.table_id=tool_table.table_id and table_name=#{tableName}", tableName);
            if (dataList != null)
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, Object> mp = dataList.get(i);
                    String column_name = (String) mp.get("column_name");
                    if ("add_userid,add_date,modify_date,modify_userid,".indexOf(column_name + ",") > -1) continue;

                    mp.put("column_statement", tableName + "." + (String) mp.get("column_statement"));
                    String column_type = (String) mp.get("column_type");
                    column_type = column_type.equals("varchar") ? "text" : (column_type.equals("int") || column_type.equals("float") ? "number" : column_name.equals("auditing") ? "combobox":column_type);
                    mp.put("column_type", column_type);
                    mp.put("funId", funId);
                    mp.put("display_style", (column_type.equals("date") ? "dateFormatter":(column_type.equals("datetime") ? "dateTimeFormatter":(column_name.equals("auditing") ? "recordsatusFormatter":""))));
                    mp.put("newKeyID", commonService.getKeyUID());
                    mp.put("order_index", ((String) mp.get("column_statement")).indexOf("_id") > 1 ? 10000 : (i + 1) * 10);
                    sqlMapper.insert("INSERT INTO tool_function_column(column_id,fun_id,group_name,column_name,column_statement,column_comment,column_type,is_addedit,is_modifyedit,is_null,is_unique,record_flag,display_style,order_index,max_length) " +
                            "select #{newKeyID},#{funId},'sql',#{column_name},#{column_statement},#{column_comment},#{column_type},'1','1','0','0','0',#{display_style},#{order_index},#{column_length} from dual where not exists(select 1 from tool_function_column where fun_id=#{funId} and column_name=#{column_name})", mp);
                }
        }catch (Exception e){
            return Result.failure("500","导入错误！");
        }finally{
            session.close();
        }
        return Result.success();
    }


    /**
     *生效数据源
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/setDbsource")
//    @Transactional
    public Result<?> setDbsource(HttpServletRequest request)  throws Exception {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("setDbsource Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String ids=(String)requestMap.get("ids");
        log.info("ids========="+ids);
        String[] dbs=ids.split(";");
        //数据源设置
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        for(int i=0;i<dbs.length;i++) {
            String id=dbs[i];
            ToolDbsource dbsource = (ToolDbsource) layoutService.getMapper("ToolDbsource").selectByPrimaryKey(id);
//            if(dbsource.getAuditing().equals("1")) continue;
            if(DynamicDataSource.managerDbSource(dbsource))//设置是否成功
                dbsource.setAuditing("1");
            else {
                dbsource.setAuditing("0");
                return Result.failure("500","设置失败");
            }
            log.info("是否生效："+dbsource.getAuditing());
            layoutService.getMapper("tool_dbsource").updateByPrimaryKey(dbsource);
        }
        DynamicDataSource.clearDataSource();
        return Result.success();

    }

    /**
     *失效效数据源
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/removeDbsource")
//    @Transactional
    public Result<?> removeDbsource(HttpServletRequest request)  throws Exception {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("removeDbsource Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String ids=(String)requestMap.get("ids");
        log.info("ids========="+ids);
        String[] dbs=ids.split(";");
        //数据源设置
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        for(int i=0;i<dbs.length;i++) {
            String id=dbs[i];
            ToolDbsource dbsource = (ToolDbsource) layoutService.getMapper("tool_dbsource").selectByPrimaryKey(id);
//            if(dbsource.getAuditing().equals("0")) continue;
            if(DynamicDataSource.removeDbSource(dbsource.getDbsource_name()))//设置是否成功
                dbsource.setAuditing("0");
            log.info("是否生效："+dbsource.getAuditing());
            layoutService.getMapper("tool_dbsource").updateByPrimaryKey(dbsource);
        }
        DynamicDataSource.clearDataSource();
        return Result.success();

    }


    /**
     *获取codeEditor文件内容
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/readFileContend")
//    @Transactional
    public Result<?> readFileContend(HttpServletRequest request)  throws Exception {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String filepath=(String)requestMap.get("filepath");
        log.info("codeEditor::::::"+filepath);
        if(StringUtils.isEmpty(filepath)){
            return Result.failure("500","文件路径不能为空！");
        }
        StringBuilder filebody=new StringBuilder("");
        try {
            filebody = FileUtils.readFile2String(filepath);
        }catch(Exception e){
            e.printStackTrace();
        }
        Result result=new Result();
        result.setData(filebody.toString());
        result.setSuccess(true);
        return result;

    }

    /**
     *保存codeEditor文件内容
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/saveFileContend")
//    @Transactional
    public Result<?> saveFileContend(HttpServletRequest request)  throws Exception {
        Map<String, Object> requestMap = Map2EntityUtil.request2Map(request);//
        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        String filepath=(String)requestMap.get("filepath");
        log.info("codeEditor::::::"+filepath);
        if(StringUtils.isEmpty(filepath)){
            return Result.failure("500","文件路径不能为空！");
        }

        String filecontent= (String) requestMap.get("filecontent");
        filecontent= StringEscapeUtils.unescapeHtml(filecontent);

        String allpath=filepath.substring(0,filepath.lastIndexOf("/")+1);
        String newfilename=filepath.substring(filepath.lastIndexOf("/")+1);
        FileUtils.createUTF8File(allpath,newfilename,filecontent,false);
        Result result=new Result();
        result.setMsg("保存成功！");
        result.setSuccess(true);
        return result;

    }

}
