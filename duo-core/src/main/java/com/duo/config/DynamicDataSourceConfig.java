package com.duo.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.duo.DataSourceNames;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 * @author yonsin
 * @date 2019/3/19 0:41
 */
@Configuration
@Slf4j
@MapperScan(basePackages = {"com.duo.modules.*.mapper"})
public class DynamicDataSourceConfig {
    /**
     * ${key}：从环境变量以及配置文件中获取值
     */
//    @Value("${spring.datasource.dbsources}")
//    private String dbsources;


    @Bean(name = "defaultSource")
    @ConfigurationProperties("spring.datasource.default")
    public DataSource defaultDataSource(){
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

    @Bean(name = "platformSource")
    @ConfigurationProperties("spring.datasource.platform")
    public DataSource platformDataSource(){
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

//    @Bean(name = "eamSource")
//    @ConfigurationProperties("spring.datasource.eam")
//    public DataSource eamDataSource(){
//        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
//    }
//
//    @Bean(name = "testSource")
//    @ConfigurationProperties("spring.datasource.test")
//    public DataSource testDataSource(){
//        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
//    }


    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        Map<String, DataSource> dsMap  = new HashMap<>();
        dsMap.put(DataSourceNames.DEFAULT, defaultDataSource());
        dsMap.put(DataSourceNames.PLATFORM, platformDataSource());
//        dsMap.put(DataSourceNames.EAM, eamDataSource());
//        dsMap.put(DataSourceNames.TEST, testDataSource());

        return new DynamicDataSource(defaultDataSource(), dsMap );
    }



    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        //手工配置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(true);
        configuration.setLazyLoadingEnabled(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setAggressiveLazyLoading(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean.getObject();
    }


    /**
     * 配置@Transactional注解事务
     * @return
     */
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dynamicDataSource());
//    }

    /**
     * 注入事物管理器
     * @return
     */
    @Bean
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
    /**
     * 创建模板
     *@param sqlSessionFactory
     *@return SqlSessionTemplate
     */
    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
