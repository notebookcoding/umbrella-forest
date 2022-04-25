package com.simulation.notebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Administrator
 */
@EnableFeignClients
@MapperScan("com.simulation.notebook.mapper")
@EnableScheduling
@SpringBootApplication
public class ForestServerLionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForestServerLionApplication.class, args);
    }
}
