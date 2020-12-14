package com.duo.core.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author [ Yonsin ] on [2019/3/25]
 * @Description： [ @ResponseBody修改统一返回值]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@ControllerAdvice(basePackages = {"com.duo.modules.system.web","com.duo.modules.api.web"})
public class ResponseBodyAdviceImpl implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("ResponseBody返回值为:{}", JSON.toJSONString(body));
        return body;
    }
}
