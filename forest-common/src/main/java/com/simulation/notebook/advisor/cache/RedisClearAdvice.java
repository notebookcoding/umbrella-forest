package com.simulation.notebook.advisor.cache;

import com.simulation.notebook.advisor.annotation.RedisClear;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisClear注解Advice
 * @author Administrator
 */
public class RedisClearAdvice implements MethodInterceptor {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisClearAdvice(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        RedisClear redisClearAnnotation = invocation.getMethod().getAnnotation(RedisClear.class);
        String redisKey = redisClearAnnotation.value();
        RedisClearEnum phase = redisClearAnnotation.phase();

        if (phase.equals(RedisClearEnum.AFTER)) {
            Object proceed = invocation.proceed();
            redisTemplate.delete(redisKey);
            return proceed;
        } else {
            redisTemplate.delete(redisKey);
            return invocation.proceed();
        }
    }
}
