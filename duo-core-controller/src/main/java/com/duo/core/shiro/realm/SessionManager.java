package com.duo.core.shiro.realm;

import com.duo.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @author [ Yonsin ] on [2019/9/3]
 * @Description： [ 实现Shiro无状态访问，如通过传递sessionid参数即可实现会话访问 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
public class SessionManager extends DefaultWebSessionManager {

    public SessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 如果参数中包含“_sid”参数，则使用此sid会话。 例如：http://localhost/project?__sid=xxx&__cookie=true
        // 其实这里还可以使用如下参数：cookie中的session名称：如：JSESSIONID=xxx,路径中的 ;JESSIONID=xxx，但建议还是使用 __sid参数。
        String sid = request.getParameter("__sid");

        if (StringUtils.isNotBlank(sid)) {
            // 是否将sid保存到cookie，浏览器模式下使用此参数。
            if (WebUtils.isTrue(request, "__cookie")){
                HttpServletRequest rq = (HttpServletRequest)request;
                HttpServletResponse rs = (HttpServletResponse)response;
                Cookie template = getSessionIdCookie();
                Cookie cookie = new SimpleCookie(template);
                cookie.setValue(sid);
                cookie.saveTo(rq, rs);
            }
            // 设置当前session状态
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE); // session来源与url
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sid;
        }else{
            return super.getSessionId(request, response);
        }
    }

}