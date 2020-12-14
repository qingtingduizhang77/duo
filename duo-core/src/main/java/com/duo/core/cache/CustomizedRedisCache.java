package com.duo.core.cache;

import com.duo.core.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisOperations;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [  自定义的redis缓存]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class CustomizedRedisCache extends RedisCache {

    private static final Logger logger = LoggerFactory.getLogger(CustomizedRedisCache.class);

    private RedisOperations redisOperations;

    private static final Lock REFRESH_CACKE_LOCK=new ReentrantLock();

    private CacheSupport getCacheSupport(){
        return SpringContextHolder.getApplicationContext().getBean(CacheSupport.class);
    }


    public CustomizedRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig, RedisOperations redisOperations) {
        super(name, cacheWriter,cacheConfig);
        this.redisOperations=redisOperations;
    }


    public ValueWrapper get(final Object key) {
        ValueWrapper valueWrapper= super.get(key);
        if(null!=valueWrapper){
            CacheItemConfig cacheItemConfig=CacheContainer.getCacheItemConfigByCacheName(key.toString());
            long preLoadTimeSecond=cacheItemConfig.getPreLoadTimeSecond();
            ;
            String cacheKey=this.createCacheKey(key);
            Long ttl= this.redisOperations.getExpire(cacheKey);
            if(null!=ttl&& ttl<=preLoadTimeSecond){
                logger.info("key:{} ttl:{} preloadSecondTime:{}",cacheKey,ttl,preLoadTimeSecond);
                if(ThreadTaskHelper.hasRunningRefreshCacheTask(cacheKey)){
                    logger.info("do not need to refresh");
                }
                else {
                    ThreadTaskHelper.run(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                REFRESH_CACKE_LOCK.lock();
                                if(ThreadTaskHelper.hasRunningRefreshCacheTask(cacheKey)){
                                    logger.info("do not need to refresh");
                                }
                                else {
                                    logger.info("refresh key:{}", cacheKey);
                                    CustomizedRedisCache.this.getCacheSupport().refreshCacheByKey(CustomizedRedisCache.super.getName(), key.toString());
                                    ThreadTaskHelper.removeRefreshCacheTask(cacheKey);
                                }

                            }
                            finally {
                                REFRESH_CACKE_LOCK.unlock();
                            }
                        }
                    });
                }
            }
        }
        return valueWrapper;
    }
}
