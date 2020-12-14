package com.duo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author [ Yonsin ] on [2020/7/20]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@SpringBootApplication(exclude = {QuartzAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
        SecurityAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        ProjectInfoAutoConfiguration.class,
        DataSourceAutoConfiguration.class
})
public class NettyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyServiceApplication.class, args);
    }

}
