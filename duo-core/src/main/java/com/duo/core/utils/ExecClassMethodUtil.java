package com.duo.core.utils;

import com.duo.core.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/8/14]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class ExecClassMethodUtil {

    public static void main(String[] args) {

		Object bl=invokeMethod(creatObject("com.duo.core.utils.ExecClassMethodUtil"), "xxx", "测试");
		System.out.println(bl);
    }

    public String xxx(String ss){

        return ss;
    }

    //
    public static Object  ExecMethod(String sClassNameMethod,Object defaultRetrun, Object... strArgs){
        if(sClassNameMethod==null||sClassNameMethod.equals("")||sClassNameMethod.indexOf(":")<0) return defaultRetrun;
        String[] ss = sClassNameMethod.split(":");
        Object object = invokeMethod(creatObject(ss[0]), ss[1], strArgs);
        return object == null ? defaultRetrun : object;
    }

    /**
     * 创建普通对象。 触发类名称
     *
     * @param sClassName
     *            类全名，含包名
     * @return
     */
    public static Object creatObject(String sClassName) {
        // String ClassName = _z.getCLASS_NAME();
        Object ret = null;
        if (sClassName == null || sClassName.length() == 0)
            return ret;
        Class clazz = null;
        try {
            clazz = Class.forName(sClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return ret;
        }
        try {
            ret = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 创建普通对象。 触发类方法名称
     *
     * @param object
     *            类全名，含包名
     * @return
     */
    public static Object invokeMethod(Object object, String sMethodName,Object... strArgs) {
        // 创建组件的方法对象
        Method method = null;
        // 调用该对象的方法
        Object result = null;
        try {
            if (strArgs != null) {
                Class[] classes=new Class[strArgs.length];
                for(int i=0;i<strArgs.length;i++){
                    classes[i]=strArgs[i].getClass();
                }

                try {
                    method = object.getClass().getMethod(sMethodName, classes);
                } catch (Exception e) {
                    for(int i=0;i<strArgs.length;i++){
                        // 如果当前类型方法没找到，Class实体转换
                        if (classes[i].isAssignableFrom(HashMap.class)) {
                            classes[i] = Map.class;
                        } else if (classes[i].isAssignableFrom(ArrayList.class)) {
                            classes[i] = ArrayList.class;
                        } else if (BaseEntity.class
                                .isAssignableFrom(classes[i])) {// 是否PublicEntity子类
                            classes[i] = BaseEntity.class;
                        }
                    }
                    try{
                        method = object.getClass().getMethod(sMethodName, classes);
                    }catch(Exception ae){
                        if(classes.length==1){
                            classes[0]=Object.class;
                            method = object.getClass().getMethod(sMethodName, classes);
                        }
                    }
                }
            } else {
                method = object.getClass().getMethod(sMethodName);
            }
            if (method == null) {
                log.error("ExecClassMethod ERROR:1.Assigned Class File Executed Failed!!..[" + " : " + sMethodName + "]");
                return null;
            }
            if (strArgs != null) {
                result = method.invoke(object, strArgs);
            } else {
                result = method.invoke(object);
            }
        } catch (Exception e) {
            log.error("ExecClassMethod ERROR:1.Assigned Class File Executed Failed!!..[" + " : " + sMethodName + "]",e);
            return null;
        }

        if (result != null) {
            log.info("ExecClassMethod excute["+ object + " " + sMethodName+"] result:" + result);
        } else {
            log.info("ExecClassMethod excute["+ object + " " + sMethodName+"] result is null!!!![" + object.toString() + " : " + sMethodName + "]");
        }
        // 组件方法要求返回boolean值
        return result;
    }

}
