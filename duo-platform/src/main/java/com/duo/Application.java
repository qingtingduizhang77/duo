package com.duo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(exclude = {QuartzAutoConfiguration.class,
		DruidDataSourceAutoConfigure.class,
		//  ThymeleafAutoConfiguration.class,
		SecurityAutoConfiguration.class,
		MultipartAutoConfiguration.class,
		ProjectInfoAutoConfiguration.class
		//,
		//,org.activiti.spring.boot.SecurityAutoConfiguration.class
		//  org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthIndicatorAutoConfiguration.class
})
@EnableFeignClients
@EnableEurekaClient
// spring cloud 中服务发现有多种实现（eureka、consul、zookeeper等等），@EnableDiscoveryClient 基于 spring-cloud-commons, @EnableEurekaClient 基于 spring-cloud-netflix。
//如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient，如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient。
@ServletComponentScan
//@ConfigurationProperties
//@ComponentScan({"com.duo.modules"})
@EnableTransactionManagement
//@EnableAceCache
@Slf4j
public class Application extends SpringBootServletInitializer implements CommandLineRunner {
	// 打包war需要这个启动类，发布到服务器上
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(Application.class);
//        return builder.sources(Application.class);
	}


	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("服务启动完成!");
	}


}
