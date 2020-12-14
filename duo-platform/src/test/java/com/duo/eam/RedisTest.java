package com.duo.eam;

import com.alibaba.fastjson.JSONObject;
import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.utils.DateUtils;
import com.duo.core.utils.RedisUtil;
import com.duo.modules.common.service.CommonService;
import com.duo.modules.system.entity.SystemBaiduid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//import org.junit.jupiter.api.Test;

/**
 * 将EAM系统的表结构导入新系统
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    private CommonService commonService;

    @Test
    public void aa(){

        Example example=new Example(SystemBaiduid.class);
        example.setOrderByClause("id desc");
        RowBounds rowBounds=new RowBounds(0,1);
        DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
        List<SystemBaiduid> lsdatas=commonService.getMapper("system_baiduid").selectByExampleAndRowBounds(example,rowBounds);
        log.info(JSONObject.toJSONString(lsdatas));

    }

    public String xx(String tableName, String dbSourceName, String codeType, String codeSQL, Map<String, Object> parameterMap, int length, String preCode){

        String fillchar="0";
        if(codeType.equals("yearseq")){
            //利用Redis产生序列
            if(!RedisUtil.hasKey("SystemCodes"+tableName+DateUtils.getYear()))
                RedisUtil.set("SystemCodes"+tableName+DateUtils.getYear(),0,3600*24*366);//一年失效
            long newcode=RedisUtil.incr("SystemCodes"+tableName+DateUtils.getYear(),1);
            String code=String.valueOf(newcode);

            System.out.println(code);
            for (int i = code.length(); i < length; i++) {
                code = fillchar + code;
            }
            code=DateUtils.getYear()+code;//加上年份前缀
            return preCode+code;
        }
        return "";
    }


    public void test(){
//        RedisUtil.expire("code",30);
//        RedisUtil.hset("code","table_code",122);
//        RedisUtil.hset("code","table_code2",11);
//        log.info(String.valueOf(RedisUtil.hincr("code","table_code2",1)));
//        log.info(String.valueOf(RedisUtil.hget("code","table_code")));
//        log.info(String.valueOf(RedisUtil.hget("code","table_code2")));
        BaseMessage msg=new BaseMessage();
        msg.setControl_id("2123");
        msg.setDevice_id("234");
        RedisUtil.lPush("listtest", JSONObject.toJSONString(msg));
//        log.info(String.valueOf(RedisUtil.lGetListSize("listtest")));
        String ss=(String)RedisUtil.rPop("listtest",3);
        log.info(ss);
        log.info(String.valueOf(RedisUtil.lGetListSize("listtest")));

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class BaseMessage implements Serializable {

        private String control_id;
        private String device_id;
        private String message_type;
        private String connect_message;

    }

}

