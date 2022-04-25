package com.simulation.notebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * @author Administrator
 */
@EnableAsync
@Configuration
public class ExecutorConfig {

    public static final int cpu = Runtime.getRuntime().availableProcessors();

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 配置核心线程池数量
        taskExecutor.setCorePoolSize(cpu);
        // 配置最大线程池数量
        taskExecutor.setMaxPoolSize(cpu << 1);
        /// 线程池所使用的缓冲队列
        taskExecutor.setQueueCapacity(2);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        taskExecutor.setAwaitTerminationSeconds(60);
        // 空闲线程存活时间
        taskExecutor.setKeepAliveSeconds(60);
        // 等待任务在关机时完成--表明等待所有线程执行完
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程池名称前缀
        taskExecutor.setThreadNamePrefix("thread-pool-");
        // 线程池拒绝策略
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        // 线程池初始化
        taskExecutor.initialize();
        return taskExecutor;
    }
}
