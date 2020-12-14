package com.duo.core.utils;

import java.util.Properties;

/**
 * @author [ Yonsin ] on [2019/5/14]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class YamlConfigurerUtil {
    private static Properties ymlProperties = new Properties();
    public YamlConfigurerUtil(Properties properties){
        ymlProperties = properties;
    }
    public static String getStrYmlVal(String key){
        return ymlProperties.getProperty(key);
    }
    public static Integer getIntegerYmlVal(String key){
        return Integer.valueOf(ymlProperties.getProperty(key));
    }

}
