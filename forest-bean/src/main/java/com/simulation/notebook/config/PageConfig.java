package com.simulation.notebook.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 分页功能支持
 * @author Administrator
 */
@Configuration
public class PageConfig {

    /**
     * 默认加载分页插件。最新版
     * @return 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor pi = new PaginationInterceptor();
        // 分页查询时，最大单页查询条数
        pi.setLimit(5000);
        // 开启count的join优化，只针对部分left join
        pi.setCountSqlParser(new JsqlParserCountOptimize());
        return pi;
    }

}
