package com.simulation.notebook.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign配置注册（全局）
 * @author Administrator
 */
@Configuration
public class FeignSupportConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignBasicRequestInterceptor();
    }
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
