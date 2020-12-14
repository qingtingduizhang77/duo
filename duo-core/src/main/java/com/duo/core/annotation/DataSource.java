package com.duo.core.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author yonsin
 * @date 2019/3/16 22:16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "default";
}
