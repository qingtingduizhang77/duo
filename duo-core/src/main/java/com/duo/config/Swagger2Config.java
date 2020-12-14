package com.duo.config;

import com.duo.MemCache;
import com.duo.core.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ Swagger2配置类 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Configuration
@EnableSwagger2
@Slf4j
@Order(99)
public class Swagger2Config {
    // 定义分隔符
    private static final String splitor = ";";
    @Bean
    public Docket createRestApi() {
        log.info("启动Swagger2");
        String packages= PropertiesUtil.getProperties("duo","swagger-packages");
        if(packages==null) packages="com.duo.modules.api.web";
        log.info("packages==="+packages);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(packages))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DUO系统api文档")
                .description("用于接口开发人员和测试人员使用")
             //   .termsOfServiceUrl("http://www.5gwl.com")
                .version("1.0")
                .build();
    }



    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

}
