package com.duo.core.utils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/14]
 * @Description： [ 根据entityname查找表名或主键、字段 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class EntityAnnotationUtil {

    /**
     *
     * Discription :获取表名
     * @param clazz
     * @return
     * String
     */
    public static String getTableName( Class<?> clazz)
    {
        Table table = (Table)clazz.getAnnotation(Table.class);
        return table.name();
    }

    /**
     *
     * Discription : 获取字段名称 并将数据放入map集合中
     *
     * @param clazz
     * @return Map<String,String>
     */
    public static Map<String, String> getColumnNames(Class<?> clazz) {
        Map<String, String> map = new HashMap<String, String>();
        Field[] fields=clazz.getDeclaredFields();
        for (Field field : fields) {
            Id d = field.getAnnotation(Id.class); // 获取注视中id的对象
            if (null != d) {
                map.put(field.getName(), "Id");
            } else {
                Transient t = field.getAnnotation(Transient.class); // 标记非字段
                if (null == t) {
                    map.put(field.getName(),"Column");
                }
            }
        }
        //都定义在字段里
//        Method[] methods = clazz.getMethods();
//        for (Method method : methods) {
//            Column c = method.getAnnotation(Column.class); // 获取注视中注解的对象
//            if (null != c) {
//                Id d = method.getAnnotation(Id.class); // 获取注视中id的对象
//                if (null != d) {
//                    map.put(c.name(), "Column");
//                } else {
//                    map.put(c.name(), "Id");
//                }
//            } else {
//                JoinColumn jc = method.getAnnotation(JoinColumn.class); // 获取外键的字段名称
//                if (null != jc) {
//                    map.put(jc.name(), "JoinColumn");
//                }
//            }
//        }
        return map;
    }
}
