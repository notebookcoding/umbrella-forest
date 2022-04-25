package com.simulation.notebook.advisor.cache;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * RedisClear注解Advisor
 * @author Administrator
 */
@Component
public class RedisClearAdvisor implements PointcutAdvisor {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Pointcut getPointcut() {
        return new RedisClearPointCut();
    }

    @Override
    public Advice getAdvice() {
        return new RedisClearAdvice(redisTemplate);
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
