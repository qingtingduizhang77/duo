package com.duo.modules.common.service;

import com.alibaba.fastjson.JSONObject;
import com.duo.MemCache;
import com.duo.core.BaseService;
import com.duo.core.utils.FileUtils;
import com.duo.core.utils.StringUtils;
import com.duo.core.vo.Result;
import com.duo.modules.tool.entity.*;
import com.duo.modules.tool.mapper.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author [ Yonsin ] on [2020/1/22]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Service
public class ToolService extends BaseService {
    @Autowired
    private ToolFunctionMapper toolFunctionMapper;
    @Autowired
    private ToolFunctionColumnMapper toolFunctionColumnMapper;
    @Autowired
    public LayoutService layoutService;
    @Autowired
    private ToolDataMapper toolDataMapper;
    @Autowired
    private ToolSelectMapper toolSelectMapper;
    @Autowired
    private ToolSystemModuleMapper toolSystemModuleMapper;

    public  Result<?>  createFormFile(Map<String, Object> requestMap){

        for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
        String data=StringEscapeUtils.unescapeHtml((String) requestMap.get("data"));
        String fun_id= (String) requestMap.get("foreignKeyId");
        ToolFunction funinfo=toolFunctionMapper.selectByPrimaryKey(fun_id);
        funinfo.setForm_map(data);
        toolFunctionMapper.updateByPrimaryKey(funinfo);

        String isCreateFile= (String) requestMap.get("isCreateFile");
        if(isCreateFile!=null&&isCreateFile.equals("true")){//生成form页面文件
            List<Map> list = JSONObject.parseArray(data,Map.class);
            List<Map<String, Object>> fieldsetLs=new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> inputLs=new ArrayList<Map<String, Object>>();
            for(Map<String, Object> mp:list){// 将对象分类
                if(mp.get("type").equals("fieldset")) fieldsetLs.add(mp);
                else inputLs.add(mp);
                System.out.println(mp);
            }
            //对fieldsetLs 重新排序
            if (fieldsetLs != null && fieldsetLs .size() > 1) {
                Collections.sort(fieldsetLs , new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        Integer o1Value = Integer.valueOf(o1.get("top").toString());
                        Integer o2Value = Integer.valueOf(o2.get("top").toString());
                        return o1Value.compareTo(o2Value);//正序
                    }
                });
            }
            //对inputLs重新排序
            if (inputLs != null && inputLs .size() > 1) {
                Collections.sort(inputLs , new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        if(Integer.valueOf(o1.get("top").toString())==Integer.valueOf(o2.get("top").toString())) {
                            Integer o1Value = Integer.valueOf(o1.get("left").toString());
                            Integer o2Value = Integer.valueOf(o2.get("left").toString());
                            return o1Value.compareTo(o2Value);//正序
                        }else{
                            Integer o1Value = Integer.valueOf(o1.get("top").toString());
                            Integer o2Value = Integer.valueOf(o2.get("top").toString());
                            return o1Value.compareTo(o2Value);//正序
                        }
                    }
                });
            }
            System.out.println("========================================");
            for(Map mp:inputLs){
                System.out.println(mp);
            }

            //获取数据库字段设置
            List<ToolFunctionColumn> columns=layoutService.getFunctionColumn(fun_id);
            Map<String,ToolFunctionColumn> columnMp=new HashMap<String,ToolFunctionColumn>();
            for(ToolFunctionColumn col:columns){
                columnMp.put(col.getColumn_name(),col);
            }

            StringBuffer fileData=new StringBuffer("<!DOCTYPE html>\n<html xmlns:th=\"http://www.thymeleaf.org\"  xmlns:shiro=\"http://www.pollix.at/thymeleaf/shiro\">\n\n");
            fileData.append("<div  th:fragment=\"formPage\">\n");
            fileData.append("\t<form class=\"form-horizontal\" role=\"form\" id=\"infoform\" >\n");

            int fieldsetTop=0,fieldsetLeft=0,fieldsetWidth=0,fieldsetHeight=0,set=0;
            boolean writeFieldset=true;
            boolean isNewLine=true;
            if(inputLs!=null)
                for(int i=0;i<inputLs.size();i++){
                    Map mp=inputLs.get(i);
                    int top=  (Integer) mp.get("top") ;
                    int left=  (Integer) mp.get("left") ;
                    int width=  (Integer) mp.get("width") ;
                    int height=  (Integer) mp.get("height") ;
                    String column_name= (String) mp.get("column_name");
                    String column_comment= (String) mp.get("column_comment");
                    ToolFunctionColumn entity=columnMp.get(column_name);//非字段时null

                    if(!writeFieldset) {
                        if (fieldsetTop > 0 && (top > fieldsetTop + fieldsetHeight || i == inputLs.size() - 1)) {
                            fileData.append("\t\t</fieldset>\n");
                            set++;
                            writeFieldset = true;
                        }
                    }
                    //取分类框信息
                    if(writeFieldset&&fieldsetLs.size()>0&&set<fieldsetLs.size()){
                        fieldsetTop=  (Integer) fieldsetLs.get(set).get("top") ;
                        fieldsetLeft=  (Integer)  fieldsetLs.get(set).get("left") ;
                        fieldsetWidth=  (Integer) fieldsetLs.get(set).get("width") ;
                        fieldsetHeight=  (Integer) fieldsetLs.get(set).get("height");
                    }
                    if(writeFieldset&&fieldsetTop>0&&fieldsetTop<top&&fieldsetTop+fieldsetHeight>top){
                        fileData.append("\t\t<fieldset>\n\t\t\t<legend style=\"font-size:16px;\">"+((String) fieldsetLs.get(set).get("column_name"))+"</legend>\n");
                        writeFieldset=false;
                    }

                    if(left<100) {//新行
                        if(i>0){//结束行
                            fileData.append("\t\t</div>\n");
                        }
                        //如果前面行没有闭合，需要加闭合再建新行
                        fileData.append("\t\t<div class=\"form-group\">\n");
                    }
                    String classW="col-sm-9";
                    if(width<600){//半行
                        classW="col-sm-3_5";
                    }
                    String hide="";
                    if(mp.get("hidden").equals("true")||(Boolean) mp.get("hidden")) hide="style='display:none;'";
                    if(mp.get("type").equals("selectwindow")) {//选择窗口   //  default="" + StringUtils.emptyToDefault(entity.getDefault_value(), "") + ""
                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\"  "+hide+">"
                                + column_comment + (StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t\t<input type=\"text\" class=\"form-control input-sm\" readonly    name=\""+column_name+"\"    "+hide+"   style='width:80%;float:left; '  onclick='formSelectWindow(\""+entity.getControl_name()+"\",\""+entity.getControl_source()+"\",\""+entity.getControl_target()+"\",\"" + (entity.getControl_where()==null?"":HtmlUtils.htmlEscapeDecimal(entity.getControl_where())) + "\")'  "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"required=true":"")+"  />\n");
                        fileData.append("\t\t\t\t\t<button type='button'  name=\"btn_" + column_name + "\"   class='form-control' style='width:15%;float:left; ' onclick='formSelectWindow(\""+entity.getControl_name()+"\",\""+entity.getControl_source()+"\",\""+entity.getControl_target()+"\",\""+ (entity.getControl_where()==null?"":HtmlUtils.htmlEscapeDecimal(entity.getControl_where()))+"\")'  "+hide+"  >...</button>\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }else if(mp.get("type").equals("combobox")) {//下拉
                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\"  "+hide+">"
                                + column_comment + (StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t\t <select  class=\"form-control\"  name=\"" + column_name + "\"    "+hide+" "+(entity.getIs_modifyedit().equals("0")?"disabled":"")+" "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"required=true":"")+" >\n");
                        fileData.append("\t\t\t\t\t <script type=\"text/javascript\"> \n" +
                                "\t\t\t\t\t\t  var optionJson= "+entity.getControl_name()+ ";\n" +
                                "\t\t\t\t\t\t for(var key in optionJson)  $('select[name=\"" + column_name + "\"]').append(\"<option value='\"+key+\"' >\"+optionJson[key]+\"</option>\"); \n" +
                                "\t\t\t\t\t </script> \n");
                        fileData.append("\t\t\t\t\t </select>\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }else if(mp.get("type").equals("comboboxmult")) {//多选下拉
                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\"  "+hide+">"
                                + column_comment + (StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t\t <select  class=\"form-control select2\"  name=\"" + column_name + "\"  multiple  "+hide+" "+(entity.getIs_modifyedit().equals("0")?"disabled":"")+" "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"required=true":"")+" >\n");
                        fileData.append("\t\t\t\t\t <script type=\"text/javascript\"> \n" +
                                "\t\t\t\t\t\t  var optionJson= "+entity.getControl_name()+ ";\n" +
                                "\t\t\t\t\t\t for(var key in optionJson)  $('select[name=\"" + column_name + "\"]').append(\"<option value='\"+key+\"' >\"+optionJson[key]+\"</option>\"); \n" +
                                "\t\t\t\t\t </script> \n");
                        fileData.append("\t\t\t\t\t </select>\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }else if(mp.get("type").equals("text")) {//文本
                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\"  "+hide+">"
                                + column_comment + (StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t\t<input class=\"form-control\"  name=\"" + column_name + "\" type=\"text\"   "+hide+"  "
                                +(entity.getMax_length()!=null&&entity.getMax_length()>0?" maxlength=\""+entity.getMax_length()+"\" ":"")+"  "
                                +(StringUtils.isEmpty(entity.getBlur_js())? "":" onblur=\""+entity.getBlur_js()+"\"")+" "
                                +(entity.getIs_modifyedit().equals("0")?"readonly":"")+" "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"required=true":"")+" />\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }else if(mp.get("type").equals("number")) {//number
                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\"  "+hide+">"
                                + column_comment + (StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t\t<input class=\"form-control\"  name=\"" + column_name + "\" type=\"number\"   onkeyup=\"value=value.replace(/[^\\d.]/g,'')\"   "+hide+"  "
                                +(StringUtils.isEmpty(entity.getBlur_js())? "":" onblur=\""+entity.getBlur_js()+"\"")+" "
                                +(entity.getIs_modifyedit().equals("0")?"readonly":"")+" "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"required=true":"")+" />\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }else if(mp.get("type").equals("checkbox")) {//复选框
                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\"  "+hide+">"
                                + column_comment + (StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t\t<input   name=\"" + column_name + "\" type=\"checkbox\"    "+hide+"  "+(entity.getIs_modifyedit().equals("0")?"readonly":"")+" "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"required=true":"")+" />\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }else if(mp.get("type").equals("date")||mp.get("type").equals("datetime")||mp.get("type").equals("time")
                            ||mp.get("type").equals("text")) {//日期
                        String dateFmt= "yyyy-MM-dd HH:mm:ss";
                        if(mp.get("type").equals("date")) dateFmt="yyyy-MM-dd";
                        else if(mp.get("type").equals("time")) dateFmt="HH:mm:ss";
                        else if(mp.get("type").equals("timeminute")) dateFmt="HH:mm";
                        else if(mp.get("type").equals("year")) dateFmt="yyyy";
                        else if(mp.get("type").equals("yearmonth")) dateFmt="yyyy-MM";

                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\" "+hide+">"
                                + column_comment + (StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t\t<input class=\"form-control\"  name=\"" + column_name + "\" type=\"text\" "+(entity.getIs_modifyedit().equals("0")?"readonly":" onfocus=\"WdatePicker({dateFmt:'" + dateFmt + "', autoPickDate: true ,onpicked:function(){blur();},onpicking:function(dp){blur();},isShowClear:false,readOnly:true})\"   ")+" "+hide+"  "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"required=true":"")+" />\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }else if(mp.get("type").equals("label")){//label
                        fileData.append("\t\t\t\t<label class=\"col-sm-12 control-label \" style=\"height:"+height+"px\"  "+hide+" >" + column_comment +"：</label>\n");
                    }else  if(mp.get("type").equals("textarea")||mp.get("type").equals("editor")) {//textarea
                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\"  style=\"height:" + height + "px;"+(hide.length()>1?"display:none;":"")+"\"   >"
                                + column_comment + (StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t<textarea class=\"form-control\" style=\"height:" + (height-5) + "px\" name=\"" + column_name + "\"  "+(mp.get("type").equals("editor")?" id=\"editor1\" ":"")+"  "+hide+"   "
                                +(entity.getMax_length()!=null&&entity.getMax_length()>0?" maxlength=\""+entity.getMax_length()+"\" ":"")+" "
                                +(entity.getIs_modifyedit().equals("0")?"readonly":"")+"  "+(StringUtils.nullToempty(entity.getIs_null()).equals("1")?"required=true":"")+"  ></textarea>\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }else  if(mp.get("type").equals("image")) {//image图片上传控件
                        fileData.append("\t\t\t\t<label class=\"col-sm-2 control-label "+(entity!=null&&StringUtils.nullToempty(entity.getIs_null()).equals("1")?"thBlue":"")+"\"  style=\"height:" + height + "px;"+(hide.length()>1?"display:none;":"")+"\"   >"
                                + column_comment + (entity!=null&&StringUtils.nullToempty(entity.getIs_null()).equals("1")?"*":"")+"：</label>\n");
                        fileData.append("\t\t\t\t<div class=" + classW + ">\n");
                        fileData.append("\t\t\t\t\t<input class=\"form-control\"  name=\"" + column_name + "\" type=\"text\"   style='display:none;'   />\n");
                        fileData.append("\t\t\t\t\t<div id=\"imgPreview\"> \n\t\t\t\t\t <img src=\"\" name=\""+column_name+"_img\"  alt=\"\" class=\"img3\"  /> \n");
                        fileData.append("\t\t\t\t\t\t  <div class=\"imgcover\"  "+hide+"> \n");
                        fileData.append("\t\t\t\t\t\t\t  <button type=\"button\" class=\"btn btn-primary\" onclick=\"openUploadFileWindow(null,'img','page','"+column_name+"');\" >上传图片</button> \n");
                        fileData.append("\t\t\t\t\t\t</div>\n");
                        fileData.append("\t\t\t\t\t</div>\n");
                        fileData.append("\t\t\t\t</div>\n");
                    }
                    if(i==inputLs.size()-1){//结束行
                        fileData.append("\t\t</div>\n");
                    }

                }

            //获取功能设置信息
            ToolFunction funInfo=layoutService.getFunctionInfo(fun_id);
            if(funInfo==null)  return  Result.failure("500","ERROR!生成失败！");
            // String defaultName=funInfo.getFun_id()+"-grid.html";
            String path= MemCache.getSystemParameter("createDIR");//System.getProperty("user.dir");
            logdb.info("path=================="+path);
            String filename=funInfo.getForm_html();
            if(filename==null) {
                return  Result.failure("500","ERROR!filename为空！");
            }
            String allpath=path+filename.substring(0,filename.lastIndexOf("/")+1);
            String newfilename=filename.substring(filename.lastIndexOf("/")+1);
            logdb.info("allpath=================="+allpath);
            logdb.info("newfilename=================="+newfilename);

            fileData.append("\t</form>\n");
            fileData.append("</div>  \n");
            logdb.info("fileData=================="+fileData.toString());

            FileUtils.createUTF8File(allpath,newfilename,fileData.toString(),false);

        }
        return Result.success();
    }


    public Result<?> createGridFile(Map<String, Object> requestMap){
        String datas= StringEscapeUtils.unescapeHtml((String)requestMap.get("datas"));// " 被转译 成了&quot问题

        logdb.info("datas::"+datas);

        String funId=(String)requestMap.get("foreignKeyId");//找对应功能设置的Grid路径信息，无设置则默认一个
        //获取功能设置信息
        ToolFunction funInfo=layoutService.getFunctionInfo(funId);
        if(funInfo==null) new Throwable("ERROR!生成失败！");

        List<Map> list = JSONObject.parseArray(datas,Map.class);
        //Grid页面内容
        StringBuffer fileContent=new StringBuffer("<script  th:fragment=\"gridScript\"> \n\n");
        fileContent.append("//列字段定义  \n");
        fileContent.append("var _tableColumns=[  \n");
        fileContent.append("\t {field: 'state', width:30,checkbox: true,footerFormatter:totalFormatter },  \n");
        fileContent.append("\t {field: 'number',  title: 'No.',visible:false, width: 38, \n\t   formatter: function (value, row, index) { \n " +
                "\t\t   var pageSize = $table.bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条 \n" +
                "\t\t   var pageNumber =$table.bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页 \n" +
                "\t\t   return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号 \n \t}},  \n");

        StringBuffer editData=new StringBuffer("//可编辑列设置信息 \n editData={ \n");
        //将设置保存到表里
        for(int i=0;i<list.size();i++) {
            Map mp=list.get(i);
            ToolFunctionColumn bean = toolFunctionColumnMapper.selectByPrimaryKey(mp.get("column_id"));
            bean.setIs_show((String)mp.get("is_show"));
            bean.setOrder_index(10*(i+1));
            bean.setColumn_width(Integer.parseInt((String)mp.get("column_width")));
            toolFunctionColumnMapper.updateByPrimaryKeySelective(bean);

            fileContent.append("\t {field: '"+bean.getColumn_name()+"', title: '"+bean.getColumn_comment()+(bean.getIs_null().equals("1")?"*":"")+"', width:"+bean.getColumn_width()
                    +",visible:"+(bean.getIs_show().equals("1")?"true":"false")+", halign: 'center'"
                    +(StringUtils.emptyToDefault(bean.getRecord_flag(),"0").equals("1")? ",sortable:true":"")
                    +(StringUtils.isEmpty(bean.getDisplay_style())?"":(bean.getDisplay_style().equals("chekboxFormatter")?",align: 'center'":"")
                    +",formatter:"+bean.getDisplay_style())+" }"+(i==list.size()-1?"":",")+" \n");

            editData.append("\t  "+bean.getColumn_name()+ ":{addedit:"+(bean.getIs_addedit().equals("1")?"true":"false")+",edit:"+(bean.getIs_modifyedit().equals("1")?"true":"false")
                    +",type:'"+bean.getColumn_type()+"',required:"+(bean.getIs_null().equals("1")?"true":"false")+",default:'"+HtmlUtils.htmlEscapeDecimal(StringUtils.nullToempty(bean.getDefault_value()))+"'"
                    +(  ",orgfield:'"+bean.getColumn_statement().split(" as ")[0]+"'")//只要as的前部
                    +(StringUtils.isEmpty(bean.getBlur_js())?"": ",blur_js:'"+bean.getBlur_js()+"'")
                    +(StringUtils.isEmpty(bean.getControl_name())?"": ",control_name:'"+bean.getControl_name()+"'")
                    +(StringUtils.isEmpty(bean.getControl_source())?"": ",source_cols:'"+bean.getControl_source()+"'")
                    +(StringUtils.isEmpty(bean.getControl_target())?"": ",target_cols:'"+bean.getControl_target()+"'")
                    +(StringUtils.isEmpty(bean.getControl_where())?"": ",control_where:'"+(bean.getControl_where()==null?"":HtmlUtils.htmlEscapeDecimal(bean.getControl_where())) +"'")
                    +(bean.getMax_length()==null||bean.getMax_length()==0?"": ",max_length:"+bean.getMax_length()+"")
                    +(StringUtils.isEmpty(bean.getDisplay_style())?"": ",formatter:'"+bean.getDisplay_style()+"'")+"}"+(i==list.size()-1?"":",")+" \n");
        }
        logdb.info("toolFunctionColumnMapper Save Success!");
        //生成页面文件

        // String defaultName=funInfo.getFun_id()+"-grid.html";
        String path= MemCache.getSystemParameter("createDIR");//System.getProperty("user.dir");
        logdb.info("path=================="+path);
        String filename=funInfo.getGrid_html();
        if(filename==null) new Throwable("ERROR!filename为空！");
        String allpath=path+filename.substring(0,filename.lastIndexOf("/")+1);
        String newfilename=filename.substring(filename.lastIndexOf("/")+1);
        logdb.info("allpath=================="+allpath);
        logdb.info("newfilename=================="+newfilename);


        fileContent.append("\t ]; \n\n");
        fileContent.append(editData);
        fileContent.append("\t }; \n");
        fileContent.append("</script>  \n");
        logdb.info("fileContent=================="+fileContent.toString());

        FileUtils.createUTF8File(allpath,newfilename,fileContent.toString(),false);
        return Result.success();
    }


    public Result<?> createDataFormatter(){
        Example example = new Example(ToolData.class);
        example.setOrderByClause("order_index");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("data_level",2);//第二级是控件名
        List<ToolData> formatterList=toolDataMapper.selectByExample(example);

        StringBuffer dataJason=new StringBuffer("");
        StringBuffer fileContent=new StringBuffer("<script  th:fragment=\"formatterScript\"> \n\n");
        for(ToolData formatter:formatterList){
            fileContent.append("\t //"+formatter.getData_text()+"\n");
            fileContent.append("\t function "+formatter.getData_value()+"Formatter(value, row, index){ \n \t\t if(value!=null&&value.indexOf(';')>0) return value");

            dataJason.append("//" + formatter.getData_text() + "\n var "+formatter.getData_value()+"Data={");
            //查询下拉内容
            Example subexample = new Example(ToolData.class);
            subexample.setOrderByClause("order_index");
            Example.Criteria subcriteria = subexample.createCriteria();
            subcriteria.andEqualTo("data_level",3);//第二级是控件名
            subcriteria.andLike("data_id",formatter.getData_id()+"%");

            StringBuffer subsb=new StringBuffer("");
            List<ToolData> subList=toolDataMapper.selectByExample(subexample);
            for(int i=0;i<subList.size();i++) {
                ToolData sub=subList.get(i);
                if(StringUtils.isNotEmpty(sub.getData_value())) fileContent.append(".replace('"+sub.getData_value()+";','"+sub.getData_text()+";')");
                subsb.append("\t\t if(value=='"+sub.getData_value()+"') return "+(StringUtils.isNotEmpty(sub.getData_color())?"'<span style=\"color:"+sub.getData_color().replace("bold","")+";"+(sub.getData_color().indexOf("bold")>-1?"font-weight:bold;":"")+"\">"+sub.getData_text()+"</span>'":"'"+sub.getData_text()+"'")+";\n");
                dataJason.append("'"+sub.getData_value()+"':'"+sub.getData_text()+"'"+(i<subList.size()-1?",":""));
            }
            fileContent.append(";\n");
            fileContent.append(subsb);
            dataJason.append("};\n");
            fileContent.append("\t\t return value;\n");
            fileContent.append("\t}\n\n");
        }
        //begin  tool_system
        ToolSystemModule query=new ToolSystemModule();
        query.setLevel(1);
        query.setIs_show("1");
        List<ToolSystemModule> systemLs=toolSystemModuleMapper.select(query);
        fileContent.append("\t //system系统\n");
        fileContent.append("\t function systemFormatter(value, row, index){ \n \t\t if(value!=null&&value.indexOf(';')>0) return value");
        StringBuffer subsb=new StringBuffer("");
        dataJason.append("//system系统\n var systemData={");
        for(int i=0;i<systemLs.size();i++){
            ToolSystemModule formatter=systemLs.get(i);
            if(StringUtils.isNotEmpty(formatter.getSystem_id()))  fileContent.append(".replace('"+formatter.getSystem_id()+";','"+formatter.getSystem_name()+";')");
            subsb.append("\t\t if(value=='"+formatter.getSystem_id()+"') return  '"+formatter.getSystem_name()+"' ;\n");
            dataJason.append("'"+formatter.getSystem_id()+"':'"+formatter.getSystem_name()+"'"+(i<systemLs.size()-1?",":""));

        }
        fileContent.append(";\n");
        fileContent.append(subsb);
        dataJason.append("};\n");
        fileContent.append("\t\t return value;\n");
        fileContent.append("\t}\n\n");
        // end tool_system

        fileContent.append("\n\n\n//定义Data的Json对象\n"+dataJason);
        fileContent.append("\n\n");

        List<ToolSelect> selectLs=toolSelectMapper.selectAll();
        for(ToolSelect select:selectLs){
            fileContent.append("\n//选择窗口——"+select.getSelect_name());
            fileContent.append("\n var "+select.getSelect_code()+" ={'layout_url':'"+select.getLayout_url()+"','fun_id':'"+select.getFun_id()+"'};");
        }

        fileContent.append("\n</script>");
        String path= MemCache.getSystemParameter("createDIR")+"/common/";//System.getProperty("user.dir");
        logdb.info("path=================="+path);
        FileUtils.createUTF8File(path,"common-data-formatter.html",fileContent.toString(),false);
        return Result.success();
    }
}
