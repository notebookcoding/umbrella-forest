package com.simulation.notebook.advisor.param;

import com.simulation.notebook.advisor.annotation.ParamBarrier;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * ParamBarrier注解Advice
 * @author Administrator
 */
@Slf4j
public class ParamBarrierAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        ParamBarrier paramBarrierAnnotation = invocation.getMethod().getAnnotation(ParamBarrier.class);
        String[] included = ArrayUtils.addAll(paramBarrierAnnotation.value(), paramBarrierAnnotation.included());
        List<String> excluded = Arrays.asList(paramBarrierAnnotation.excluded());
        boolean multiLevel = paramBarrierAnnotation.multiLevel();
        // 获取参数
        Object[] args = invocation.getArguments();

        Map<String, Object> registerCache = new HashMap<>();
        for (Object arg : args) {
            registerCache.putAll(
                    registerField(
                            arg.getClass(),
                            arg,
                            multiLevel,
                            null,
                            excluded)
            );
        }
        if (excluded.size() > 0) {
            excludedBarrier(excluded, registerCache);
        } else {
            includedBarrier(included, registerCache);
        }
        return invocation.proceed();
    }

    /**
     * 注册属性到注册表
     * @param clz           目标类
     * @param target        目标对象
     * @param multiLevel    是否多层注册
     * @param registerCache 注册表
     * @return 注册表
     * @throws IllegalAccessException 非法访问异常
     */
    @SuppressWarnings("all")
    private Map<String, Object> registerField(Class<?> clz, Object target, boolean multiLevel,
                                              Map<String, Object> registerCache, List<String> excluded) throws IllegalAccessException {
        if (Objects.isNull(registerCache)) {
            registerCache = new HashMap<>();
        }
        if (clz.equals(Object.class)) {
            return registerCache;
        }
        for (Field candidate : clz.getDeclaredFields()) {
            if (Collection.class.isAssignableFrom(candidate.getType()) || candidate.getType().isArray()) {
                candidate.setAccessible(true);
                String collectionName = candidate.getName();
                Object actualProperty = candidate.get(target);
                if (excluded.contains(collectionName)) {
                    continue;
                }
                if (!registerCache.containsKey(collectionName)) {
                    registerCache.put(collectionName, actualProperty);
                }
                if (Objects.nonNull(actualProperty)) {
                    if (actualProperty instanceof Collection) {
                        Field[] declaredFields = actualProperty.getClass().getDeclaredFields();
                        Collection collection = (Collection) actualProperty;
                        for (Object internal : collection) {
                            holderRegister(registerCache, internal, multiLevel, excluded);
                            break;
                        }
                    } else {
                        Object[] array = (Object[]) actualProperty;
                        for (Object internal : array) {
                            holderRegister(registerCache, internal, multiLevel, excluded);
                            break;
                        }
                    }
                }
            } else if (!Modifier.isStatic(candidate.getModifiers()) && !Modifier.isFinal(candidate.getModifiers())) {
                candidate.setAccessible(true);
                String name = candidate.getName();
                if (!registerCache.containsKey(name) && !name.contains("elementData")) {
                    registerCache.put(name, candidate.get(target));
                }
            }
        }
        return multiLevel ? registerField(clz.getSuperclass(), target, multiLevel, registerCache, excluded) : registerCache;
    }

    /**
     * 注册额外属性
     * @param registerCache 属性注册表
     * @param internal      额外属性
     * @throws IllegalAccessException 非法访问异常
     */
    private void holderRegister(Map<String, Object> registerCache, Object internal,
                                boolean multiLevel, List<String> excluded) throws IllegalAccessException {
        if (internal instanceof Number || internal instanceof String || internal instanceof Boolean) {
            return;
        }
        registerField(
                internal.getClass(),
                internal,
                multiLevel,
                registerCache,
                excluded
        );
    }

    /**
     * 校验包含字段
     * @param included      包含
     * @param registerCache 属性注册表
     */
    private void includedBarrier(String[] included, Map<String, Object> registerCache) {
        if (included.length == 0) {
            registerCache.forEach((fieldName, fieldVal) -> {
                if (Objects.isNull(fieldVal)) {
                    throw new ParamBarrierException(fieldName);
                }
            });
        } else {
            for (String fieldName : included) {
                if (registerCache.containsKey(fieldName)) {
                    if (Objects.isNull(registerCache.get(fieldName))) {
                        throw new ParamBarrierException(fieldName);
                    }
                } else {
                    log.warn("The property [" + fieldName + "] to be verified cannot be found");

                }
            }
        }
    }

    /**
     * 校验包含字段
     * @param excluded      排除字段
     * @param registerCache 属性注册表
     */
    private void excludedBarrier(List<String> excluded, Map<String, Object> registerCache) {
        for (String fieldName : excluded) {
            registerCache.remove(fieldName);
        }
        registerCache.forEach((fieldName, fieldVal) -> {
            if (Objects.isNull(fieldVal)) {
                throw new ParamBarrierException(fieldName);
            }
        });
    }
}
