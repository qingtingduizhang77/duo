package com.duo.core.cache;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [   刷新缓存线程池]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
public class ThreadTaskHelper {

    private static ExecutorService executorService= Executors.newFixedThreadPool(20);

    private static Map RUNNING_REFRESH_CACHE= Maps.newConcurrentMap();

    public static Map<String,String> getRunningRefreshCache(){
        return RUNNING_REFRESH_CACHE;
    }

    public static void putRefreshCacheTask(String cacheKey){
        if(!hasRunningRefreshCacheTask(cacheKey)){
            RUNNING_REFRESH_CACHE.put(cacheKey,cacheKey);
        }
    }

    public static void removeRefreshCacheTask(String cacheKey){
        if(hasRunningRefreshCacheTask(cacheKey)){
            RUNNING_REFRESH_CACHE.remove(cacheKey);
        }
    }

    public static boolean hasRunningRefreshCacheTask(String cacheKey){
        return RUNNING_REFRESH_CACHE.containsKey(cacheKey);
    }

    public static void run(Runnable runnable){
        executorService.execute(runnable);
    }
}
