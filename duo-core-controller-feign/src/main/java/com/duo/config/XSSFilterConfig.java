package com.duo.config;

import com.duo.core.filter.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author [ Yonsin ] on [2019/3/25]
 * @Description： [ 注册XSS过滤器 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */

@Slf4j
@Configuration
public class XSSFilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new XssFilter());
        List<String> urlList = new ArrayList<String>();
        urlList.add("/*");
        registration.setUrlPatterns(urlList);
        registration.setName("xssFilter");
        registration.setOrder(1);
        log.info("注册XssFilter");
        return registration;
    }
}
