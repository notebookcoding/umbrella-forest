package com.simulation.notebook.advisor.param;

import com.simulation.notebook.advisor.annotation.ParamBarrier;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * ParamBarrier注解PointCut
 * @author Administrator
 */
public class ParamBarrierPointCut implements Pointcut, MethodMatcher {

    @Override
    public boolean matches(@Nullable Method method, @Nullable Class<?> targetClass) {
        if (Objects.nonNull(method)) {
            Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            return AnnotatedElementUtils.hasAnnotation(specificMethod, ParamBarrier.class);
        }
        return false;
    }

    @Override
    public boolean isRuntime() {
        return false;
    }

    @Override
    public boolean matches(@Nullable Method method, @Nullable Class<?> targetClass, @Nullable Object... args) {
        return false;
    }

    @Override
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}