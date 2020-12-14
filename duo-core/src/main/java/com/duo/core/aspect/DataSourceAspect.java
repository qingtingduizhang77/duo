package com.duo.core.aspect;

import com.duo.DataSourceNames;
import com.duo.config.DynamicDataSource;
import com.duo.core.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 * @author yonsin
 * @date 2019/3/16 22:20
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class DataSourceAspect implements Ordered {

    @Pointcut("@annotation(com.duo.core.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    @Before("@annotation(com.duo.core.annotation.DataSource)")
    public void beforeSwitchDS(JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSource ds = method.getAnnotation(DataSource.class);
        if(ds == null){
            DynamicDataSource.setDataSource(DataSourceNames.DEFAULT);
            log.info("set datasource is " + DataSourceNames.DEFAULT);
        }else {
            DynamicDataSource.setDataSource(ds.name());
            log.info("set datasource is " + ds.name());
        }

    }


    @After("@annotation(com.duo.core.annotation.DataSource)")
    public void afterSwitchDS(JoinPoint point){
        DynamicDataSource.clearDataSource();
        log.info("clean datasource");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
