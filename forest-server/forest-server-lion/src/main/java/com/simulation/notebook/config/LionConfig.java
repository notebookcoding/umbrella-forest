package com.simulation.notebook.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Data
@Configuration
@RefreshScope
public class LionConfig {

    @Value("${nacos.addr}")
    private String nacosAddr;

    @Value("${commonManage}")
    private String commonManage;

}
