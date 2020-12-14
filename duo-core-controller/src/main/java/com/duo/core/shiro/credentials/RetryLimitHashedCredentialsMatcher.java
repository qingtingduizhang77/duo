package com.duo.core.shiro.credentials;

import com.duo.Constants;
import com.duo.MemCache;
import com.duo.core.shiro.cache.ShiroRedisCacheManager;
import com.duo.core.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @Description： 密码匹配
 * @author [ Yonsin ] on [2019年1月24日下午5:29:33]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Slf4j
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager, RedisTemplate<String, Object> template) {
        //根据配置用Redis还是EH缓存
        String cacheType=  MemCache.getSystemParameter("cache-type");
        if(cacheType!=null&&cacheType.equals("redis")){
            passwordRetryCache = cacheManagerShiro(template).getCache("passwordRetryCache");
        }else{
            passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        }

    }


    /*
        Redis缓存
     */
    private ShiroRedisCacheManager cacheManagerShiro(RedisTemplate template){
        return new ShiroRedisCacheManager(template);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = null;
        try{
            retryCount=passwordRetryCache.get(username);
        }catch(Exception e){

        }
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        log.info("passwordRetryCache：："+retryCount.get());
        if(retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
