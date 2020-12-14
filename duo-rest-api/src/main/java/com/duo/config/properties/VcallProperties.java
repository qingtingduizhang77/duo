package com.duo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "videocall")
@Data
public class VcallProperties {

    private int sdkAppId;
    private String secretKey;
    private int expireTime;

}
