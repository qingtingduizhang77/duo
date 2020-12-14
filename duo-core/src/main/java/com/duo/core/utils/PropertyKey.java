package com.duo.core.utils;

public class PropertyKey implements java.io.Serializable{
	private String key;
	public PropertyKey(){
		
	}
	public PropertyKey(String key){
		this.key=key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
