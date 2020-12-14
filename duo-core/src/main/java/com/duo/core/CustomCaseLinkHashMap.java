package com.duo.core;

/**
 * @author [ Yonsin ] on [2020/3/16]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义LinkHashMap（主要是为了在mapper.xml里面的resultType为map的时候，要兼容两套数据库，Oracle是大写的key，sqlserver是小写的key）
 *
 * @param <V>
 * @author Yonsin
 */
public class CustomCaseLinkHashMap<V> extends LinkedHashMap<String,V> {

    /**
     * 复写put方法，因为Mybatis底层给Map赋值就是调用的put方法
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(String key, V value) {
        return super.put(key.toUpperCase(), value);
    }

    /**
     * 复写get方法，为了防止小写问题，mapper传出去的map的key是大写，再使用map.put那么key还是大写，如果再在java硬编码小写，传进去的还是大写，如果不复写get是取不到的
     * @param key
     * @return
     */
    @Override
    public V get(Object key) {
        if(key instanceof String)
        {
            return super.get(((String)key).toUpperCase());
        }
        else
        {
            return super.get(key);
        }
    }

    /**
     * 复写putAll方法，HashMap底层并不是调用put方法，但是可以利用put把putAll也变成大写的key
     * @param map
     */
    @Override
    public void putAll(Map<? extends String, ? extends V> map) {

        if(!map.isEmpty()) {
            Iterator iterator = map.entrySet().iterator();

            while(iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                this.put((String)entry.getKey(), (V)entry.getValue());
            }

        }
    }

}
