package com.duo.config.properties;

import com.duo.core.vo.SQLParseBean;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/4/11]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Component
@ConfigurationProperties(prefix = "duo")
@Data
public class SQLFormatProperties {

    private Map<String,SQLParseBean> sqlformat;
    private String fileServerType;
    private String filePath;

//    private SQLParseBean  DBO;//自定义对象的用户名,函数写法:{DBO}
//
//    private SQLParseBean  ISNULL;//返回字段为空的缺省值,函数写法:{ISNULL}
//
//    private SQLParseBean  SUBSTR;//获取子字符串，第一个字符的序号为1,函数写法:{SUBSTR}
//
//    private SQLParseBean  JOINSTR;//把字符串1、字符串2拼起来,函数写法:{JOINSTR}(v_str1, v_str2)
//
//    private SQLParseBean  TODAY;//取当前日期,函数写法:{TODAY}
//
//    private SQLParseBean  TO_STR;//日期型值转换成字符串型值,函数写法:{TO_STR}(v_date)
//
//    private SQLParseBean  TO_TIMESTR;//日期型值转换成日期时间字符串型值,函数写法:{TO_TIMESTR}(v_date)
//
//    private SQLParseBean  TO_YEARMONTH;//日期型值转换成年月字符串型值,函数写法:{TO_YEARMONTH}(v_date)
//
//    private SQLParseBean  TO_YEAR;//日期型值转换成年字符串型值,函数写法:{TO_YEAR}(v_date)
//
//    private SQLParseBean  TO_MONTH;//日期型值转换成月字符串型值,函数写法:{TO_MONTH}(v_date)
//
//    private SQLParseBean  TO_DATE;//字符串型值转换成日期型值,函数写法:{TO_DATE}(v_str)
//
//    private SQLParseBean  TO_DATETIME;//字符串型值转换成日期时间型值,函数写法:{TO_DATETIME}(v_str)
//
//    private SQLParseBean  DATEDIFF;//返回日期型值1-日期型值2 的天数,函数写法:{DATEDIFF}(v_date1, v_date2)
//
//    private SQLParseBean  MONTHDIFF;//返回日期型值1-日期型值2 的月数,函数写法:{MONTHDIFF}(v_date1, v_date2)
//
//    private SQLParseBean  DATEADD;//返回日期型值+天数,函数写法:{DATEADD}(v_date, v_day)
//
//    private SQLParseBean  MONTHADD;//返回日期型值+月数,函数写法:{MONTHADD}(v_date, v_mon)


}
