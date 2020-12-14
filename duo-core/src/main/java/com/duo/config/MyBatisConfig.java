package com.duo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@MapperScan(basePackages = {"com.duo.modules.*.mapper"})
public class MyBatisConfig {

//    @Bean(name = "dataSource")
//    @Qualifier("dataSource")
//    @ConfigurationProperties("spring.datasource.default")
//    public DataSource dataSource() {
//        return   DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        return sqlSessionFactoryBean.getObject();
//    }
//
//
//
//    /*
//     * 创建事务
//     *@param dataSource
//     *@return DataSourceTransactionManager
//     */
//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    /**
//     * 创建模板
//     *@param sqlSessionFactory
//     *@return SqlSessionTemplate
//     */
//    @Bean(name = "sqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }

    /**
    * description: MyBatis 分页插件配置   在application.yml配置即可
    */
//    @Bean
//    public PageHelper pageHelper(){
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//
//        // 设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
//        p.setProperty("offsetAsPageNum","true");
//
//        //设置为true时，使用RowBounds分页会进行count查询
//        p.setProperty("rowBoundsWithCount","true");
//        p.setProperty("reasonable","true");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }
}
