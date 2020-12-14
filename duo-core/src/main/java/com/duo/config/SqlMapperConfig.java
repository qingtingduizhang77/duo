package com.duo.config;

import com.github.abel533.sql.SqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author [ Yonsin ] on [2019/8/9]
 * @Description： [ SqlMapper 默认bean]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Configuration
public class SqlMapperConfig {
    @Autowired
    DataSource dataSource;

//    @Bean
//    @Primary
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);//更多参数请自行注入
//        return bean.getObject();
//    }


    @Bean
    public SqlMapper sqlMapper(SqlSessionFactory sqlSessionFactory){
        SqlSession session=sqlSessionFactory.openSession();
        SqlMapper sqlMapper = new SqlMapper(session);
        return sqlMapper;
    }


}