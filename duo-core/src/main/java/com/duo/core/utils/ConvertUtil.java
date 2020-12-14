package com.duo.core.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author [ Yonsin ] on [2019/5/21]
 * @Description： [  数据转换实用类 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class ConvertUtil {
    private static ConcurrentHashMap<Class<?>, Converter> converters = new ConcurrentHashMap<Class<?>, Converter>();

    public static void register(Converter converter, Class<?> clasz) {
        converters.put(clasz, converter);
    }

    public static void unregister(Class<?> clasz) {
        converters.remove(clasz);
    }

    public static Object castFromObject(Object obj, Class<?> destType) {
        if (obj == null) {
            return null;
        } else if (obj.getClass().equals(destType)) {
            return obj;
        }

        Converter converter = converters.get(destType);
        if (converter != null) {
            return converter.convert(obj);
        }

        String typeName = obj.getClass().getCanonicalName();
        if (typeName.equals("java.lang.String")) {
            return castFromString((String) obj, destType);
        } else if (typeName.equals("byte[]")) {
            return castFromString(new String((byte[]) obj), destType);
        } else if (typeName.equals("boolean") || typeName.equals("java.lang.Boolean")) {
            return castFromString(Boolean.toString((Boolean) obj), destType);
        } else if (typeName.equals("int") || typeName.equals("java.lang.Integer")) {
            return castFromString(Integer.toString((Integer) obj), destType);
        } else if (typeName.equals("short") || typeName.equals("java.lang.Short")) {
            return castFromString(Short.toString((Short) obj), destType);
        } else if (typeName.equals("long") || typeName.equals("java.lang.Long")) {
            return castFromString(Long.toString((Long) obj), destType);
        } else if (typeName.equals("double") || typeName.equals("java.lang.Double")) {
            return castFromString(Double.toString((Double) obj), destType);
        } else if (typeName.equals("float") || typeName.equals("java.lang.Float")) {
            return castFromString(Float.toString((Float) obj), destType);
        } else if (typeName.equals("java.math.BigInteger")) {
            return castFromString(((java.math.BigInteger) obj).toString(), destType);
        } else if (typeName.equals("java.math.BigDecimal")) {
            return castFromString(((java.math.BigDecimal) obj).toString(), destType);
        } else if (typeName.equals("java.util.Date")) {
            return castFromString(((java.util.Date) obj).toString(), destType);
        } else if (typeName.equals("java.sql.Date")) {
            return castFromString(((java.sql.Date) obj).toString(), destType);
        } else if (typeName.equals("java.sql.Time")) {
            return castFromString(((java.sql.Time) obj).toString(), destType);
        } else if (typeName.equals("java.sql.Timestamp")) {
            return castFromString(((java.sql.Timestamp) obj).toString(), destType);
        } else {
            return obj;
        }
    }

    /**
     * 把字符串转换成指定的数据类型
     *
     * @param val      字符串
     * @param destType 目标数据类型
     * @return 转换后的数值
     */
    private static Object castFromString(String val, Class<?> destType) {
        String typeName = destType.getCanonicalName();
        if (typeName.equals("java.lang.String")) {
            return val;
        }

        if (StringUtils.isEmpty(val)||val==null||val.equals("") || val.equals("null") || val.equals("undefined")) {
            return null;
        } else if (typeName.equals("boolean") || typeName.equals("java.lang.Boolean")) {
            return Boolean.parseBoolean(val);
        } else if (typeName.equals("int") || typeName.equals("java.lang.Integer")) {
            if(val.toString().indexOf(".")>0) val=val.toString().substring(0,val.toString().indexOf("."));
            return Integer.parseInt(val);
        } else if (typeName.equals("short") || typeName.equals("java.lang.Short")) {
            return Short.parseShort(val);
        } else if (typeName.equals("long") || typeName.equals("java.lang.Long")) {
            return Long.parseLong(val);
        } else if (typeName.equals("double") || typeName.equals("java.lang.Double")) {
            return Double.parseDouble(val);
        } else if (typeName.equals("float") || typeName.equals("java.lang.Float")) {
            return Float.parseFloat(val);
        } else if (typeName.equals("java.math.BigInteger")) {
            return new java.math.BigInteger(val);
        } else if (typeName.equals("java.math.BigDecimal")) {
            return new java.math.BigDecimal(val);
        }  else if (typeName.equals("java.util.Date")) {
            return format(val);
        } else if (typeName.equals("java.sql.Date")) {
            return java.sql.Date.valueOf(val.substring(0, 10));    //yyyy-mm-dd
        } else if (typeName.equals("java.sql.Time")) {
            return java.sql.Time.valueOf(val);
        } else if (typeName.equals("java.sql.Timestamp")) {
            if (val.trim().length() == 10) {
                return java.sql.Timestamp.valueOf(val.trim() + " 00:00:00");
            }
            if (val.trim().length() == 16) {
                return java.sql.Timestamp.valueOf(val.trim() + ":00");
            }
            return java.sql.Timestamp.valueOf(val);
        }
        return null;
    }

    public Converter lookup(Class<?> clasz) {
        return converters.get(clasz);
    }

    //将日期格式化成yyyy-MM-dd形式
    public static Date format(String datestr) {
        try {
            if(StringUtils.isNumeric(datestr)){//时间戳格式转换
                java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                String d = myFormat.format(new Long(datestr));
                Date date = myFormat.parse(d);
                return date;
            }else if(datestr.indexOf(":")>0){
                if (datestr.trim().length() == 16) {
                    java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm");
                    Date date = myFormat.parse(datestr);
                    return date;
                }
                java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                Date date = myFormat.parse(datestr);
                return date;
            }else{
                java.text.SimpleDateFormat myFormat = new SimpleDateFormat(
                        "yyyy-MM-dd");
                Date date = myFormat.parse(datestr);
                return date;
            }

        } catch (Exception e) {
          //  e.printStackTrace();
            return new Date(datestr);
        }
    }


    public static Object getFieldValue(Class<?> clasz, String field, Object obj) throws Exception{
        try{

            Field f = clasz.getDeclaredField(field);
            f.setAccessible(true);
            return f.get(obj);
        }
        catch(Exception e){
            try{
                Field f = clasz.getField(field);
                f.setAccessible(true);
                return f.get(obj);
            }catch(Exception e2){
                String err = "get field " + field + " value error.";
                throw new Exception(err, e2);
            }
        }

    }


    public static void setFieldValue(Class<?> clasz, String field, Object obj, Object value) throws Exception{


        try{
            Field f = clasz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, value);
        }
        catch(Exception e){
            try{
                Field f = clasz.getField(field);
                f.setAccessible(true);
                f.set(obj, value);
            }
            catch(Exception ee){
                String err = "assign field " + field + " width value " + value + " error.";
                throw new Exception(err, ee);
            }
        }
    }

    public static <E> void printObject(E obj, Class<?> clasz)
    {
        Field[] fields = clasz.getFields();
        for (Field field : fields)
            try {
                Object value = getFieldValue(clasz, field.getName(), obj);
                System.out.println(field.getName() + ":" + value);
            }
            catch (Exception localException)
            {
            }
        fields = clasz.getDeclaredFields();

        for (Field field : fields)
            try {
                Object value = getFieldValue(clasz, field.getName(), obj);
                System.out.println(field.getName() + ":" + value);
            }
            catch (Exception localException1)
            {
            }
    }
}
