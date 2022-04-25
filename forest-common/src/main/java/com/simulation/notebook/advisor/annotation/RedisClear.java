package com.simulation.notebook.advisor.annotation;


import com.simulation.notebook.advisor.cache.RedisClearEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 清除指定RedisKey
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisClear {
    /**
     * 指定 RedisKey
     */
    String value();

    /**
     * 执行阶段
     */
    RedisClearEnum phase() default RedisClearEnum.AFTER;
}
