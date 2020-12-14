package com.duo.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * @author [ Yonsin ] on [2019/8/13]
 * @Description： [ Map与实体Bean之间互转工具类 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class Bean2MapUtil {

    /**
     * Map转实体类共通方法 (Map2Bean)
     *
     * @param type
     * @param map
     * @return Object
     * @throws Exception
     */
    public static Object convertMap(Class type, Map map) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        Object obj=type.newInstance();
        PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor:propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)){
                Object value = map.get(propertyName);
                descriptor.getWriteMethod().invoke(obj,value);
            }
        }
        return obj;
    }


    /**
     * 实体类转Map共通方法 (Bean2Map)
     *
     * @param bean 实体类
     * @return Map
     * @throws Exception
     */
    public static Map convertBean(Object bean) throws Exception {
        Class type=bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor:propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")){
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result!=null){
                    returnMap.put(propertyName,result);
                }else {
                    returnMap.put(propertyName,"");
                }
            }
        }
        return returnMap;
    }
}
