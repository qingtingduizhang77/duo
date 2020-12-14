package com.duo.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author [ Yonsin ] on [2020/7/20]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Configuration
@EnableFeignClients
@Slf4j
public class FeignClientsConfigurationCustom implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();

                    log.warn("name:"+name+";value:"+value);
                    template.header(name, value);
                }
            }
        }
//        System.out.println("request.getSession().getId()....." +request.getSession().getId() );
//        System.out.println("requestAttributes.getSessionId()....." +requestAttributes.getSessionId() );
//        try {
//            String sessionId = requestAttributes.getSessionId();
//            if (null != sessionId) {
//
//                log.warn("SESSIONID:"+sessionId);
//                template.header("Cookie", "SESSION=" + sessionId);
//            }
//        } catch (Exception e) {
//            log.error("MyRequestInterceptor exception: ", e);
//        }



        //下面注释掉的代码，如果你放开，默认是post请求（如果你的接口是get请求放开了此代码，回报错：405不支持post请求。）
//        Enumeration<String> bodyNames = request.getParameterNames();
//          StringBuffer body =new StringBuffer();
//          if (bodyNames != null) {
//              while (bodyNames.hasMoreElements()) {
//                String name = bodyNames.nextElement();
//                String values = request.getParameter(name);
//                body.append(name).append("=").append(values).append("&");
//              }
//          }
//         if(body.length()!=0) {
//            body.deleteCharAt(body.length()-1);
//             template.body(body.toString());
//            log.info("feign interceptor body:{}",body.toString());
//        }


    }
}

