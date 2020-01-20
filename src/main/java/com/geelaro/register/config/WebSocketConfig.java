package com.geelaro.register.config;

import com.geelaro.register.endpoint.ManyServerEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){ //暴露端点配置
        return new ServerEndpointExporter();
    }

    @Bean
    public ManyServerEndpoint manyServerEndpoint(){ //创建端点
        return new ManyServerEndpoint();
    }
}
