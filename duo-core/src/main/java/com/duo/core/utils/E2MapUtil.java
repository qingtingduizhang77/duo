package com.duo.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class E2MapUtil {

	// 对象转Map
	  public static <E> Map<String, Object> E2Map(E item, Class<?> clasz)
	    throws  Exception
	  {
	    Map obj = new HashMap();

	    Field[] fields = clasz.getFields();

	    for (Field field : fields) {
			try {
			    Object value = ConvertUtil.getFieldValue(clasz, field.getName(), item) ;
			    value = ConvertUtil.castFromObject(value, field.getType());
			    if (obj.containsKey(field.getName().toLowerCase())) {
					continue;
				} 
			    obj.put(field.getName().toLowerCase(), value);
			    }
			  catch (Exception localException)
			  {
			  }
		}
	    fields = clasz.getDeclaredFields();

	    for (Field field : fields) {
			try {
			    Object value = ConvertUtil.getFieldValue(clasz, field.getName(), item)== null?"":ConvertUtil.getFieldValue(clasz, field.getName(), item);
			    value = ConvertUtil.castFromObject(value, field.getType());
			    if (obj.containsKey(field.getName().toLowerCase())) {
					continue;
				} 
			    obj.put(field.getName().toLowerCase(), value);
			  }
			  catch (Exception localException1)
			  {
			  }
		}
	    return obj;
	  }

	/**
	 * 根据对象key获取值
	 * @param item
	 * @param clasz
	 * @param key
	 * @param <E>
	 * @return
	 * @throws Exception
	 */
	public static <E> Object  getEValueByKey(E item, Class<?> clasz,String key)
			throws  Exception
	{
		Field[] fields = clasz.getFields();

		for (Field field : fields) {
			try {
				Object value = ConvertUtil.getFieldValue(clasz, field.getName(), item) ;
				value = ConvertUtil.castFromObject(value, field.getType());
				 if(field.getName().equals(key)) return value ;
			}
			catch (Exception localException)
			{
			}
		}
		fields = clasz.getDeclaredFields();

		for (Field field : fields) {
			try {
				Object value = ConvertUtil.getFieldValue(clasz, field.getName(), item)== null?"":ConvertUtil.getFieldValue(clasz, field.getName(), item);
				value = ConvertUtil.castFromObject(value, field.getType());
				if(field.getName().equals(key)) return value ;
			}
			catch (Exception localException1)
			{
			}
		}
		return null;
	}


	public static <E> void pringEntity(E item, Class<?> clasz) throws  Exception
	{
		Field[] fields = clasz.getFields();
		for (Field field : fields) {
			try {
				Object value = ConvertUtil.getFieldValue(clasz, field.getName(), item) ;
				value = ConvertUtil.castFromObject(value, field.getType());
				log.info(field.getName().toLowerCase()+":"+ value);
			}
			catch (Exception localException)
			{
			}
		}
		fields = clasz.getDeclaredFields();
		for (Field field : fields) {
			try {
				Object value = ConvertUtil.getFieldValue(clasz, field.getName(), item)== null?"":ConvertUtil.getFieldValue(clasz, field.getName(), item);
				value = ConvertUtil.castFromObject(value, field.getType());
				log.info(field.getName().toLowerCase()+":"+ value);
			}
			catch (Exception localException1)
			{
			}
		}
	}
	  
	// XML转Map
	public static Map XML2Map(String xml,Map module){ 
		E2Map obj=new E2Map();
		return obj.XML2Map(xml, module);
	}

	// 普通Map转格式Map
	public static Map Map2ModuleMap(Map mp,Map module){ 
		E2Map obj=new E2Map();
		return obj.Map2ModuleMap(mp, module);
	}

	// 格式Map转普通Map
	public static Map ModuleMap2Map(Map module){
		E2Map obj=new E2Map();
		return obj.Map2ModuleMap(module,null);
	}



}