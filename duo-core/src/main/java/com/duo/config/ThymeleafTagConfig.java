package com.duo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.duo.tag.DuoDialect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author [ Yonsin ] on [2019/5/13]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "duo.tag")
public class ThymeleafTagConfig {
    private String classnames; //从配置文件取


    public String getClassnames() {
        return classnames;
    }
    public void setClassnames(String classnames) {
        this.classnames = classnames;
    }

    @Bean
    @ConditionalOnMissingBean
    public DuoDialect duoDialect(){
        log.info("添加Tag classnames：============="+classnames);
        return new DuoDialect(classnames);
    }

}
