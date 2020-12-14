package com.duo.modules.common.bean;

import cn.hutool.core.lang.Func;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2020/7/17]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */

public class Kv extends LinkedCaseInsensitiveMap<Object> {
    private Kv() {
    }

    public static Kv create() {
        return new Kv();
    }

    public static HashMap newMap() {
        return new HashMap(16);
    }

    public Kv set(String attr, Object value) {
        this.put(attr, value);
        return this;
    }

    public Kv setAll(Map map) {
        this.putAll(map);
        return this;
    }

    public Kv setIgnoreNull(String attr, Object value) {
        if (null != attr && null != value) {
            this.set(attr, value);
        }

        return this;
    }

    public Object getObj(String key) {
        return super.get(key);
    }

    public <T> T get(String attr, T defaultValue) {
        Object result = this.get(attr);
        return result != null ? (T) result : defaultValue;
    }

    public String getStr(String attr) {
        return null != this.get(attr) && !this.get(attr).equals("null") ? String.valueOf(this.get(attr)) : "";
    }

    public Integer getInt(String attr) {
        if(attr==null) return -1;
        return Integer.parseInt(attr);
    }

    public Long getLong(String attr) {
        if(attr==null) return -1L;
        return Long.parseLong(attr);
    }

    public Float getFloat(String attr) {
        if(attr==null) return null;
        return Float.parseFloat(attr);
    }

    public Double getDouble(String attr) {
        if(attr==null) return null;
        return Double.parseDouble(attr);
    }

    public Boolean getBool(String attr) {
        if (attr != null) {
            String val = String.valueOf(attr);
            val = val.toLowerCase().trim();
            return Boolean.parseBoolean(val);
        } else {
            return null;
        }
    }

    public byte[] getBytes(String attr) {
        return (byte[])this.get(attr, (Object)null);
    }

    public Date getDate(String attr) {
        return (Date)this.get(attr, (Object)null);
    }

    public Time getTime(String attr) {
        return (Time)this.get(attr, (Object)null);
    }

    public Timestamp getTimestamp(String attr) {
        return (Timestamp)this.get(attr, (Object)null);
    }

    public Number getNumber(String attr) {
        return (Number)this.get(attr, (Object)null);
    }

    public Kv clone() {
        return (Kv)super.clone();
    }
}