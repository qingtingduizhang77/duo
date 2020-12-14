package com.duo.core.utils;

import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author [ Yonsin ] on [2019/5/21]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class Map2EntityUtil {

    /**
     * 更新模型对象
     *
     * @param <E>
     * @param obj    模型对象
     * @param prefix 参数前缀
     * @param map    参数
     * @return 更新后的模型对象
     * @throws Exception
     */
    public static <E> E updateModel(E obj, String prefix, Map<?, ?> map) throws Exception {
        Class<?> clasz = obj.getClass();
        for (Field f : clasz.getFields()) {
            String key = prefix + "[" + f.getName() + "]";
            String value = null;
            if (map.containsKey(key)) {
                value = (String) map.get(key);
            } else {
                key = prefix + "." + f.getName();
                value = (String) map.get(key);
            }
//            String[] value = (String[])map.get(prefix+"["+f.getName()+"]");
            if (value == null) {
                continue;
            }
            f.setAccessible(true);
            f.set(obj, ConvertUtil.castFromObject(value, f.getType()));
        }

        for (Field f : clasz.getDeclaredFields()) {
            String key = prefix + "[" + f.getName() + "]";
            String value = null;
            if (map.containsKey(key)) {
                value = (String) map.get(key);
            } else {
                key = prefix + "." + f.getName();
                value = (String) map.get(key);
            }
//            String[] value = (String[])map.get(prefix+"["+f.getName()+"]");
            if (value == null) {
                continue;
            }
            f.setAccessible(true);
            f.set(obj, ConvertUtil.castFromObject(value, f.getType()));
        }
        return obj;
    }

    /**
     * 建立模型对象
     *
     * @param <E>
     * @param c   模型类
     * @param map 参数
     * @return 模型对象
     * @throws Exception
     */
    public static <E> E createModel(Class<E> c, Map<?, ?> map) throws Exception {
        E obj = c.newInstance();
        return updateModel(obj, map);
    }

    /**
     * 更新模型对象
     *
     * @param <E>
     * @param obj 模型对象
     * @param map 参数
     * @return 更新后的模型对象
     * @throws Exception
     */
    public static <E> E updateModel(E obj, Map<?, ?> map) throws Exception {
        if(map==null) return null;
        Class<?> clasz = obj.getClass();

        for (Field f : clasz.getFields()) {
            String value = (String) ConvertUtil.castFromObject(map.get(f.getName()),String.class);
            if (value == null) {
                continue;
            }
            if (f.getName().equals("add_date") || f.getName().equals("modify_date")) {//过滤add_date,modify_date值
                continue;
            }
            f.setAccessible(true);
            f.set(obj, ConvertUtil.castFromObject(value, f.getType()));
        }

        for (Field f : clasz.getDeclaredFields()) {
            String value = (String) ConvertUtil.castFromObject( map.get(f.getName()),String.class);
            if (value == null) {
                continue;
            }
            if (f.getName().equals("add_date") || f.getName().equals("modify_date")) {//过滤add_date,modify_date值
                continue;
            }
            f.setAccessible(true);
            f.set(obj, ConvertUtil.castFromObject(value, f.getType()));
        }
        return obj;
    }

    /**
     * Request转换成Map
     * @param request
     * @return
     */
    public static Map<String, Object> request2Map(HttpServletRequest request){
        Map<String, Object> requestMap = new HashMap<String, Object>();
        Enumeration paramNames = request.getParameterNames();
        //所有参数存到map中
        while (paramNames.hasMoreElements()) {
            String name = (String) paramNames.nextElement();
            if (name.indexOf("attributes[") > -1) continue;
            String[] value = request.getParameterValues(name);
            if (value == null) {
                requestMap.put(name,null);
            } else if (value.length == 1) {
                if(name.equals("offset")||name.equals("limit")){
                    requestMap.put(name, Integer.valueOf(value[0]));
                }else{
                    if(name.startsWith("query__")){
                        if(value[0]!=null&&value[0].indexOf("'")>-1){
                            value[0]=value[0].replaceAll("'","‘");//防止查询参数sql注入
                        }
                    }
                    requestMap.put(name, value[0]==null?null:StringEscapeUtils.unescapeHtml(value[0]));
                }
            } else {
                requestMap.put(name, value);
            }

        }
        //添加全局变量  当前人信息、当前时间


        return requestMap;
    }



		/*
	 * Map 递归序列化 isProperty 是否仅取属性值
	 */

    public static String Map2XML(Map mp,boolean isProperty){
        if(mp==null||mp.isEmpty()) return "";
        StringBuffer xml=new StringBuffer("");
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (isProperty) {// 是否取属性
                if(entry.getKey() instanceof PropertyKey) {
                    PropertyKey key=(PropertyKey)entry.getKey();
                    Object value = entry.getValue();
                    xml.append(" "+key.getKey()+"=\""+value+"\"");
                }
            }else{
                if(entry.getKey() instanceof String) {
                    String key = (String)entry.getKey();
                    Object value = entry.getValue();
                    if(value instanceof String){
                        xml.append("<"+key+">"+value+"</"+key+">");
                        // System.out.println("key=="+key+",value=="+value);
                    }else if(value instanceof Map){
                        xml.append("<"+key+" "+Map2XML((Map)value,true)+">"+Map2XML((Map)value,false)+"</"+key+">");
                    }
                }
            }
        }
        return xml.toString();
    }

    /**
     * 把map的key转换成驼峰命名
     * @param map
     * @return
     */
    public static Map<String, Object> UnderlineToHump(Map<String, Object> map) {
        Map re_map = new HashMap();
        if (re_map != null) {
            Iterator var2 = map.entrySet().iterator();

            while (var2.hasNext()) {
                Entry<String, Object> entry = (Entry) var2.next();
                re_map.put(lineToHump((String) entry.getKey()), map.get(entry.getKey()));
            }
            map.clear();
        }

        return re_map;
    }
    /**
     * 把map的key驼峰命名转换成下划线
     * @param map
     * @return
     */
    public static Map<String, Object> HumpToUnderline(Map<String, Object> map) {
        Map re_map = new HashMap();
        if (re_map != null) {
            Iterator var2 = map.entrySet().iterator();

            while (var2.hasNext()) {
                Entry<String, Object> entry = (Entry) var2.next();
                re_map.put(humpToLine2((String) entry.getKey()), map.get(entry.getKey()));
            }

        //    map.clear();
        }

        return re_map;
    }


    private static Pattern linePattern = Pattern.compile("_(\\w)");
    /**下划线转驼峰*/
    public static String lineToHump(String str){
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    /**驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})*/
    public static String humpToLine(String str){
        if(_hunpLines.containsKey(str)){
            return _hunpLines.get(str);
        }
        String sb=str.replaceAll("[A-Z]", "_$0").toLowerCase();
        _hunpLines.put(str,sb);//放进缓存
        return sb;
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    public static ConcurrentHashMap<String ,String> _hunpLines=new ConcurrentHashMap<String ,String>();//缓存转换名字
    /**驼峰转下划线,效率比上面高*/
    public static String humpToLine2(String str){
        if(_hunpLines.containsKey(str)){
            return _hunpLines.get(str);
        }
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        _hunpLines.put(str,sb.toString());//放进缓存
        return sb.toString();
    }

    public static void printMap(Map<String ,Object> mp){
        for (Map.Entry<String, Object> entry : mp.entrySet()) {
            System.out.println("wfupdateKey = " + entry.getKey() + ", Value = " + StringEscapeUtils.unescapeHtml((String)entry.getValue()));
        }
    }

}
