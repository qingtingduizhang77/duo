package com.duo.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
 

public class E2Map {
	private Map module; 
	private boolean isModule=true;; 
	
	//普通Map转格式化Map
	public  Map Map2ModuleMap(Map mp,Map module){ 
		if(module==null||module.isEmpty()) {//是否指定格式的Map赋值
			isModule=false;
			module=new HashMap();
		}
		this.module=module;
		 Iterator it = mp.entrySet().iterator();
         while (it.hasNext()) {
             Map.Entry entry = (Map.Entry) it.next(); 
        	 if(entry.getKey() instanceof String) {
	             String key = (String)entry.getKey();
	             Object value = entry.getValue();  
	             if(value instanceof String){
	            	 put2ModuleMap(key,(String)value);
	             }
	             if(value instanceof Date){
	            	 put2ModuleMap(key,(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)value)));
	             }
        	 }
         }
		
		return this.module;
	}

	//格式化Map转普通Map
	public  Map ModuleMap2Map(Map module,Map mp){ 
		if(mp==null||mp.isEmpty()) {//是否指定格式的Map赋值
			isModule=false;
			mp=new HashMap();
		}
		this.module=mp;
		
		ModuleMap2Map(module);//递归
		
		return this.module;
	}
	
	//递归
	private  void ModuleMap2Map(Map submp){ 
		 Iterator it = submp.entrySet().iterator();
         while (it.hasNext()) {
             Map.Entry entry = (Map.Entry) it.next(); 
             Object key=entry.getKey();
             if(key instanceof String) { 
	             Object value = entry.getValue();  
	             if(value instanceof String){
	            	 put2ModuleMap((String)key,(String)value);
	             }else if(value instanceof Map){  
		             ModuleMap2Map((Map) value); //递归
	             }
        	 }else if(key instanceof PropertyKey){
            	 Object value = entry.getValue();  
	             if(value instanceof String){
	            	 put2ModuleMap(((PropertyKey)key).getKey(),(String)value);
	             }
             }
         }
	}
	
	//XML格式转Map
	public  Map XML2Map(String xml,Map module){ 
		if(module==null||module.isEmpty()) {//是否指定格式的Map赋值
			isModule=false;
			module=new HashMap();
		}
		this.module=module;
		  Document doc = null;
		  try{
		   doc = DocumentHelper.parseText(xml);
		  }catch(DocumentException e){
		   e.printStackTrace();
		   return null;
		  }  
		   
		  Element root = doc.getRootElement();
	      //  System.out.println("根节点："+root.getName()+",内容："+root.getText());//+root.attributeValue("id")); 
	        
	        put2ModuleMap(root.getName(),root.getText());
	        for(int i=0,len = root.attributes().size();i < len;i++){  
        	 	Attribute at=(Attribute) root.attributes().get(i);  
        	// 	System.out.println("属性："+at.getName()+",值："+at.getText());  
		        put2ModuleMap(at.getName(),at.getText());
		        
        	  } 
	         //递归方法    
	        getElement(root); 
		
		return this.module;
	}

    //递归方法    
	 private  void getElement(Element element){   
         List list = element.elements();
         for(Iterator its =  list.iterator();its.hasNext();){   
             Element chileEle = (Element)its.next();
          //   System.out.println("节点："+chileEle.getName()+",内容："+chileEle.getText());
		        put2ModuleMap(chileEle.getName(),chileEle.getText());
		        
             for(int i=0,len = chileEle.attributes().size();i < len;i++){
            	 	Attribute at=(Attribute) chileEle.attributes().get(i);
            	 	//System.out.println("属性："+at.getName()+",值："+at.getText());
    		        put2ModuleMap(at.getName(),at.getText());
    		        
            	  } 

             getElement(chileEle);
         }
     }
	 
	 //赋值到指定格式Map
	 private void put2ModuleMap(String key,String value){  
		 if(value==null||value.equals("")) return;
			//System.out.println(key);
			//System.out.println(module);
		 if(!isModule||(module.containsKey(key)&&!(module.get(key) instanceof Map))){
			 module.put(key, value);
			 return ;
		 }
//		 if(module.containsKey(new PropertyString(key))){//属性
//			 module.put(new PropertyString(key), value);
//			 return ;
//		 }
		 Iterator it = module.entrySet().iterator();  
         while (it.hasNext()) {
             Map.Entry entry = (Map.Entry) it.next();  
             Object subMap = entry.getValue();  
             Object subkey = entry.getKey(); 
             if(subkey instanceof PropertyKey){//属性
            	 PropertyKey tmp= (PropertyKey) subkey;
            	 if(tmp.getKey().equals(key)){
            		 module.put(tmp, value);
        			 return ;
            	 }
              }
             //是否有子Map
            if(subMap instanceof Map){
            	Map submodule=(Map) subMap ;
            	submodule= putSubMapValue(key,value,submodule);
        		if(submodule!=null){
        			module.put(subkey, submodule);
           		 	return ; 
        		}
            } 
         }
        return ;
	}
	 
	 //递归更新子Map，一旦找到key即返回
	 private Map putSubMapValue(String key,String value,Map module){  
			//System.out.println(key);
			//System.out.println(module);
		 if(module.containsKey(key)&&!(module.get(key) instanceof Map)){
			 module.put(key, value);
			 return module;
		 }
//		 if(module.containsKey(new PropertyString(key))){//属性
//			 module.put(new PropertyString(key), value);
//			 return module;
//		 }
		 Iterator it = module.entrySet().iterator();  
         while (it.hasNext()) {
             Map.Entry entry = (Map.Entry) it.next();  
             Object subMap = entry.getValue();  
             Object subkey = entry.getKey();  
             if(subkey instanceof PropertyKey){//属性
            	 PropertyKey tmp= (PropertyKey) subkey;
            	 if(tmp.getKey().equals(key)){
            		 module.put(tmp, value);
        			 return module;
            	 }
              }
             //是否有子Map
            if(subMap instanceof Map){
            	Map submodule=(Map) subMap ;
            	submodule= putSubMapValue(key,value,submodule);
        		if(submodule!=null){
        			module.put(subkey, submodule);
           		 	return module; 
        		}
            } 
         }
        return null;
	}
}
