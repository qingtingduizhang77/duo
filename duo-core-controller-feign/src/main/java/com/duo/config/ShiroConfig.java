package com.duo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.duo.MemCache;
import com.duo.core.filter.DuoFormAuthenticationFilter;
import com.duo.core.filter.KickoutSessionControlFilter;
import com.duo.core.shiro.cache.ShiroRedisCacheManager;
import com.duo.core.shiro.realm.DUOSessionDAO;
import com.duo.core.shiro.realm.SessionManager;
import com.duo.core.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.duo.core.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.duo.core.shiro.realm.UserRealm;
import com.duo.core.shiro.spring.SpringCacheManagerWrapper;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.*;

@Slf4j
@Configuration
public class ShiroConfig {


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager,//KickoutSessionControlFilter kickoutSessionControlFilter,
                                              RedisTemplate<String, Object> template) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //shiro自定义过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        shiroFilterFactoryBean.setFilters(filters);
        //限制同一帐号同时在线的个数
        filters.put("authc", new DuoFormAuthenticationFilter(template));
    //    filters.put("kickout", kickoutSessionControlFilter);
        //配置记住我或认证通过可以访问的地址
        // 配置不会被拦截的链接 顺序判断
        // <!--
        // authc:所有url都必须认证通过才可以访问;
        // anon:所有url都都可以匿名访问;
        // user:配置记住我或认证通过可以访问；-->
        filterChainDefinitionMap.put("/login","authc");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //未授权界面;
        filterChainDefinitionMap.put("/authenticated", "authc");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/project/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("/app/**", "anon");
        filterChainDefinitionMap.put("/bi/**", "anon");
        filterChainDefinitionMap.put("/sso/**", "anon");
        filterChainDefinitionMap.put("/admin/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        //Activiti Modeler在线设计器
        filterChainDefinitionMap.put("/modeler.html", "anon");
        filterChainDefinitionMap.put("/model/**", "anon");
        //.do类
        filterChainDefinitionMap.put("/**/*.do", "anon");
        //.js .css jpg gif png json
        filterChainDefinitionMap.put("/**/*.js", "anon");
        filterChainDefinitionMap.put("/**/*.css", "anon");
        filterChainDefinitionMap.put("/**/*.jpg", "anon");
        filterChainDefinitionMap.put("/**/*.gif", "anon");
        filterChainDefinitionMap.put("/**/*.png", "anon");
        filterChainDefinitionMap.put("/**/*.json", "anon");

        log.info("-添加shiro-anon.properties例外地址：");
        Properties prop= PropertiesUtil.getInstance().load("shiro-anon");
        Iterator<Map.Entry<Object, Object>> it = prop.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            Object key = entry.getKey();
            Object url = entry.getValue();
            if(url!=null&&!url.equals("")){
                    log.info("-----添加例外地址："+url);
                     filterChainDefinitionMap.put(String.valueOf(url), "anon");
             }
        }

       // 首先要通过 kickout 后面的filter，然后再通过user后面对应的filter才可以访问
        filterChainDefinitionMap.put("/**", "user");//kickout,
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 并发登录控制
     * @return
     */
//    @Bean
//    public KickoutSessionControlFilter kickoutSessionControlFilter(SpringCacheManagerWrapper cacheManager, RedisTemplate<String, Object> template){
//        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
//        //用于根据会话ID，获取会话进行踢出操作的；
//        kickoutSessionControlFilter.setSessionManager(sessionManager());
//        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
//        //根据配置用Redis还是EH缓存
//        String cacheType= MemCache.getSystemParameter("cache-type");
//        if(cacheType!=null&&cacheType.equals("redis")){
//            kickoutSessionControlFilter.setCacheManager(cacheManagerShiro(template));
//        }else{
//            kickoutSessionControlFilter.setCacheManager(cacheManager);
//        }
//        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
//        kickoutSessionControlFilter.setKickoutAfter(false);
//        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
//        String maxSession=MemCache.getSystemParameter("maxSession");//同一个帐号最大会话数
//        if(maxSession==null) maxSession="1";
//        maxSession=maxSession.trim();
//        kickoutSessionControlFilter.setMaxSession(Integer.parseInt(maxSession));
//        //被踢出后重定向到的地址；
//        kickoutSessionControlFilter.setKickoutUrl("/login?kickout=1");
//        return kickoutSessionControlFilter;
//    }

    /**
     * 安全管理器
     * @param userRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(UserRealm userRealm, DefaultWebSessionManager sessionManager,
                                                     SpringCacheManagerWrapper cacheManager,RedisTemplate<String, Object> template){
        sessionManager.setSessionIdUrlRewritingEnabled(false);//去掉shiro登录时url里的JSESSIONID

        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);

        //根据配置用Redis还是EH缓存
        String cacheType=  MemCache.getSystemParameter("cache-type");
        if(cacheType!=null&&cacheType.equals("redis")){
            securityManager.setCacheManager(cacheManagerShiro(template));
        }else{
            securityManager.setCacheManager(cacheManager);
        }

        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /*
        Redis缓存
     */
    private ShiroRedisCacheManager cacheManagerShiro(RedisTemplate template){
        return new ShiroRedisCacheManager(template);
    }



    /**
//     * 缓存管理器
//     *
//     * @return
//     */
//    @Bean
//    public SpringCacheManagerWrapper cacheManager(EhCacheCacheManager springCacheManager) {
//        SpringCacheManagerWrapper cacheManager = new SpringCacheManagerWrapper();
//        cacheManager.setCacheManager(springCacheManager);
//        return cacheManager;
//    }



    /**
     * 凭证匹配器
     *
     * @return
     */
    @Bean
    public RetryLimitHashedCredentialsMatcher credentialsMatcher(SpringCacheManagerWrapper cacheManager,RedisTemplate<String, Object> template) {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager,template);
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(2);//散列次数
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * Realm实现
     *
     * @return
     */
    @Bean
    public UserRealm userRealm(RetryLimitHashedCredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCachingEnabled(false);
        return userRealm;
    }

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启Shiro 的AOP 注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 相当于调用SecurityUtils.setSecurityManager(securityManager)
     *
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(securityManager);
        return methodInvokingFactoryBean;
    }



    /**
     * 会话ID生成器
     *
     * @return
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 会话Cookie模板
     *
     * @return
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    /**
     * 自动登陆自动登陆cookie
     * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     *cookie管理对象;记住我功能,rememberMe管理器
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("DuoDazhe2018-2050=="));
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    /**
     * 会话DAO
     *
     * @return
     */
    @Bean
    public DUOSessionDAO sessionDAO() {
        DUOSessionDAO enterpriseCacheSessionDAO = new DUOSessionDAO();
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }

    /**
     * 会话管理器
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new SessionManager();
        String timeout=MemCache.getSystemParameter("SessionTimeout");
        if(timeout==null||timeout.equals("")) timeout="1800000";
        sessionManager.setGlobalSessionTimeout(Integer.parseInt(timeout));
        sessionManager.setDeleteInvalidSessions(true);
//        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }

    /**
     * 会话验证调度器
     *
     * @return
     */
    @Bean
    public QuartzSessionValidationScheduler sessionValidationScheduler(DefaultWebSessionManager sessionManager) {
        QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
        String timeout=MemCache.getSystemParameter("SessionTimeout");
        if(timeout==null||timeout.equals("")) timeout="1800000";
        sessionValidationScheduler.setSessionValidationInterval(Integer.parseInt(timeout));
        sessionValidationScheduler.setSessionManager(sessionManager);
        return sessionValidationScheduler;
    }
    //加上之后，前台的shiro标签才能生效
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

}
