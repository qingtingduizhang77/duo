package com.duo.config;

import java.util.Map;
import java.util.Set;

import com.duo.config.properties.HBaseProperties;
//import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 * @author [ Yonsin ] on [2019/4/11]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
//@Configuration
//@EnableConfigurationProperties(HBaseProperties.class)
public class HBaseConfig {
//
//    private final HBaseProperties properties;
//
//    public HBaseConfig(HBaseProperties properties) {
//        this.properties = properties;
//    }
//
//    @Bean
//    public HbaseTemplate hbaseTemplate() {
//        HbaseTemplate hbaseTemplate = new HbaseTemplate();
//        hbaseTemplate.setConfiguration(configuration());
//        hbaseTemplate.setAutoFlush(true);
//        return hbaseTemplate;
//    }
//
//    public org.apache.hadoop.conf.Configuration configuration() {
//
//        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
//
//        Map<String, String> config = properties.getConfig();
//        Set<String> keySet = config.keySet();
//        for (String key : keySet) {
//            configuration.set(key, config.get(key));
//        }
//
//        return configuration;
//    }

}
