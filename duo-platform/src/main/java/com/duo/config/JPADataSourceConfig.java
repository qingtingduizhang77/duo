package com.duo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author [ Yonsin ] on [2019/3/26]
 * @Description： [ JPA数据源的配置 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */

//@Configuration
public class JPADataSourceConfig {


//    @Bean(name = "dataSource")
//    @Qualifier("dataSource")
//    @ConfigurationProperties(prefix="spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }


//    @Bean(name = "secondaryDataSource")
//    @Qualifier("secondaryDataSource")
//    @Primary
//    @ConfigurationProperties(prefix="spring.datasource2")
//    public DataSource secondaryDataSource() {
//        return DataSourceBuilder.create().build();
//    }

}
