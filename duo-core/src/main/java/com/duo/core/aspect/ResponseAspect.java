package com.duo.core.aspect;

import com.duo.core.utils.ShiroUtils;
import com.duo.modules.system.entity.SystemOnline;
import com.duo.modules.system.mapper.SystemOnlineMapper;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ 定义一个ResponseAop切面类 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Aspect
@Order(5)
@Component
@Slf4j
public class ResponseAspect {
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Autowired
    SystemOnlineMapper systemOnlineMapper;

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/favicon.ico")));
    /**
     * 切点
     */
    @Pointcut("execution(public String com.duo.modules.*.web..*(..))")
    public void httpResponse() {
    }

    @Before("httpResponse()")
    public void doBefore(JoinPoint joinPoint){
        //开始计时
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //例外页面不执行
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        String[] exclusionsUrls = {".js",".gif",".jpg",".png",".css",".ico"};
        for (String str : exclusionsUrls) {
            if (path.contains(str)) {
                return;
            }
        }
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        if(allowedPath) return;

        //打印请求的内容
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));//获取请求头中的User-Agent
        log.info("接口路径：{}" , request.getRequestURL().toString());
        log.info("浏览器：{}", userAgent.getBrowser().toString());
        log.info("浏览器版本：{}",userAgent.getBrowserVersion());
        log.info("操作系统: {}", userAgent.getOperatingSystem().toString());
        log.info("IP : {}" , request.getRemoteAddr());
        log.info("请求类型：{}", request.getMethod());
        log.info("类方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 : {} " + Arrays.toString(joinPoint.getArgs()));

        SystemOnline userSession= systemOnlineMapper.selectByPrimaryKey(request.getSession().getId());
        if(userSession==null){
            userSession=new SystemOnline();
            userSession.setLogin_id(request.getSession().getId());
            userSession.setLogin_time( new Date());
            userSession.setLogin_status("1");
            userSession.setDept_id(ShiroUtils.getDeptId());
            userSession.setDept_name(ShiroUtils.getDeptName());
            userSession.setUser_id(ShiroUtils.getUserId());
            userSession.setUser_name(ShiroUtils.getUserName());
            userSession.setBrowser_type(userAgent.getBrowser().toString()+" "+userAgent.getBrowserVersion());
            userSession.setLogin_ip(request.getRemoteAddr());
            userSession.setLast_fleshtime(new Date());
            userSession.setLast_viewurl(request.getRequestURL().toString());
            systemOnlineMapper.insertSelective(userSession);
        }else {
            if (userSession.getUser_id() == null || userSession.getUser_id().equals("")) {
                userSession.setDept_id(ShiroUtils.getDeptId());
                userSession.setDept_name(ShiroUtils.getDeptName());
                userSession.setUser_id(ShiroUtils.getUserId());
                userSession.setUser_name(ShiroUtils.getUserName());
                userSession.setBrowser_type(userAgent.getBrowser().toString() + " " + userAgent.getBrowserVersion());
                userSession.setLogin_ip(request.getRemoteAddr());
            }
            userSession.setLast_fleshtime(new Date());
            userSession.setLast_viewurl(request.getRequestURL().toString());
            systemOnlineMapper.updateByPrimaryKey(userSession);
        }
    }

    @AfterReturning(returning = "ret" , pointcut = "httpResponse()")
    public void doAfterReturning(Object ret){
        //处理完请求后，返回内容
        log.info("方法返回值：{}" , ret);
        log.info("方法执行时间：{}毫秒", (System.currentTimeMillis() - startTime.get()));
    }
}
