package com.duo.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	private static PropertiesUtil util = null;
	private static Map<String,Properties> props = null;
	private PropertiesUtil(){
		
	}
	
	public static PropertiesUtil getInstance() {
		if(util==null) {
			props = new HashMap<>();
			util = new PropertiesUtil();
		}
		return util;
	}
	
	public Properties load(String name) {
		if(props.get(name)!=null) {
			return props.get(name);
		} else {
			Properties prop = new Properties();
			try {
				prop.load(PropertiesUtil.class.getResourceAsStream("/"+name+".properties"));
				props.put(name, prop);
				return prop;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String  getProperties(String filename,String property){
		//初始化DUO配置文件信息
		Properties prop= PropertiesUtil.getInstance().load(filename);
		Iterator<Map.Entry<Object, Object>> it = prop.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Object, Object> entry = it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if(property.equals(key)) return (String) value;
		}
		if(filename.equals("system")) {
			Properties properties = System.getProperties();
			// log.info(properties.get("user.dir").toString());
			return properties.get(property).toString();
		}
		return null;
	}
}
