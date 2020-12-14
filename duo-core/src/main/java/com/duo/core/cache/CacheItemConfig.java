package com.duo.core.cache;

import lombok.Data;

import java.io.Serializable;


/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ 缓存容器 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Data
public class CacheItemConfig implements Serializable {

    /**
     * 缓存容器名称
     */
    private String name;
    /**
     * 缓存失效时间
     */
    private long expiryTimeSecond;
    /**
     * 当缓存存活时间达到此值时，主动刷新缓存
     */
    private long preLoadTimeSecond;

}
