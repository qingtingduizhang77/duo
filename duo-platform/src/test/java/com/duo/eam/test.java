package com.duo.eam;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duo.Application;
import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.utils.*;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.tool.entity.ToolDbsource;
import com.duo.modules.tool.entity.ToolFunction;
import com.duo.modules.tool.entity.ToolTable;
import com.duo.modules.tool.entity.ToolTableColumn;
import com.duo.modules.tool.mapper.ToolTableColumnMapper;
import com.duo.modules.tool.mapper.ToolTableMapper;
import com.duo.modules.view.entity.ViewFactTableinfo;
import com.duo.modules.view.mapper.ViewFactTableinfoMapper;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class test {
    //    @Autowired
//    CommonService commonService;
    @Test
    public void test() throws Exception {
//
//        ToolFunction  entity=new ToolFunction();
//        entity.setFun_id("111");
//        log.info((String)E2MapUtil.getEValueByKey(entity,commonService.getEntity("tool_function"),"fun_id"));
//
    }

    public static void main(String[] args) {
//        String ss="{\"data\":[{\"name\":\"aa\",\"age\":\"12\"},{\"name\":\"bb\",\"age\":\"13\"}]}";
//        JSONObject obj= JSON.parseObject(ss);
//        Map<String,Object>  mpp=JSONObject.toJavaObject(obj,Map.class);
//         List<Map> ls=(List<Map>)mpp.get("data");
//        for(Map<String, Object> mp:ls){
//            for (Map.Entry<String, Object> entry : mp.entrySet()) {
//                System.out.println("key = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
//            }
//        }
//        String tmp = HtmlUtils.htmlEscapeDecimal(null);
////        System.out.println(tmp);
////
////        String value = HtmlUtils.htmlUnescape(tmp);
////        System.out.println(value);

//        String passwordSalt= new SimpleHash(
//                "md5",
//                "123321",
//                ByteSource.Util.bytes("duo"+"duodazhe123"),
//                2).toHex();
//        System.out.println(passwordSalt);

    }

//    {
//        try {
//            StringBuilder filebody=FileUtils.readFile2String("F:\\RFID\\iEAM\\eam\\device\\grid_dbsel_dept.js");
//            String jsonBody="["+ filebody.toString().split("var cols = \\[")[1].split("config.param = \\{")[0].trim();
//        System.out.println(jsonBody.substring(0,jsonBody.length()-1));
//          //  JSONObject jsonObject = JSONObject.parseObject(jsonBody.substring(0,jsonBody.length()-1));
//          //  List<Map> list= JSONObject.parseArray("[{col:{header:'部门编码', width:100, sortable:true}, field:{name:'v_sys_dept__dept_code',type:'string'}}]",Map.class);
//        //    System.out.println(list.size());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}