package com.duo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author [ Yonsin ] on [2019/4/1]
 * @Description： [ 启用WebSocket的支持 ]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 */
//@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
