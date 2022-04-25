package com.simulation.notebook.advisor.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数拦截器
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamBarrier {

    @AliasFor("included")
    String[] value() default {};

    /**
     * 包含字段
     */
    String[] included() default {};

    /**
     * 排除字段
     */
    String[] excluded() default {};

    /**
     * 多层级的是否校验
     */
    boolean multiLevel() default false;
}
