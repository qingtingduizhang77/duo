package com.duo.core.cache;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [  主动刷新缓存接口]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public interface CacheSupport {

	/**
	 * 刷新容器中所有值
	 * @param cacheName
     */
	void refreshCache(String cacheName);

	/**
	 * 按容器以及指定键更新缓存
	 * @param cacheName
	 * @param cacheKey
     */
	void refreshCacheByKey(String cacheName, String cacheKey);

}