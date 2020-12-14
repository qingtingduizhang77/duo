package com.duo.core.annotation;

/**
 * @author [ Yonsin ] on [2019/3/24]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtCacheable {

    String key() default "";

    int expireTime() default 1800;//30分钟

}

