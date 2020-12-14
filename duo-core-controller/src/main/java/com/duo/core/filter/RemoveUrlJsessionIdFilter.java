package com.duo.core.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author [ Yonsin ] on [2019/5/15]
 * @Description： [ 去掉url中有Jsessionid ]
 * @Modified By： [修改人] on [修改日期] for [ 改为通过： 去掉shiro登录时url里的JSESSIONID sessionManager.setSessionIdUrlRewritingEnabled(false); ]
 */

//@WebFilter(filterName="jsessionIdFilter",urlPatterns="/*" )
public class RemoveUrlJsessionIdFilter implements Filter{

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/favicon.ico", "/logout", "/register")));


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // skip non-http requests
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);


        //从url中删除jsessionid
        // isRequestedSessionIdFromURL():Checks whether the requested session ID came in as part of the request URL.
        if (httpServletRequest.isRequestedSessionIdFromURL()) {
            HttpSession session = httpServletRequest.getSession();
            if (null != session) {
                session.invalidate();
            }
        }
        // wrap response to remove URL encoding
        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpServletResponse) {
            @Override
            public String encodeRedirectUrl(String url) {
                return url;
            }
            @Override
            public String encodeRedirectURL(String url) {
                return url;
            }
            @Override
            public String encodeUrl(String url) {
                return url;
            }
            @Override
            public String encodeURL(String url) {
                return url;
            }
        };
        // process next request in chain
        chain.doFilter(request, wrappedResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }



    @Override
    public void destroy() {

    }

}
