package com.duo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;

/**
 * @author [ Yonsin ] on [2019/4/11]
 * @Description： [ 功能描述 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
@ConfigurationProperties(prefix = "hbase")
@Data
public class HBaseProperties {

    private Map<String, String> config;


}
