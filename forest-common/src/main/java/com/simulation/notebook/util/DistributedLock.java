package com.simulation.notebook.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * @author Administrator
 */
@Component
public class DistributedLock {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private DefaultRedisScript<Boolean> script;

    /**
     * 默认失效时间毫秒
     */
    private final static int DEFAULT_LOCK_TIME = 2000;

    /**
     * 分布式锁前缀
     */
    private final static String LOCK_PREFIX = "DistributedLock:";

    /**
     * 线程隔离
     */
    private ThreadLocal<String> localId = new ThreadLocal<>();

    /**
     * 尝试获取锁
     * @param key key
     * @return 成功 or 失败
     */
    public boolean tryLock(String key) {
        return tryLock(key, DEFAULT_LOCK_TIME);
    }

    /**
     * 尝试获取锁
     * @param key        key
     * @param expireTime 过期时间 毫秒
     * @return 成功 or 失败
     */
    public boolean tryLock(String key, int expireTime) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        String id = UUID.randomUUID().toString();
        try {
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(
                    LOCK_PREFIX + key,
                    id,
                    expireTime,
                    TimeUnit.MILLISECONDS
            );
            if (flag != null && flag) {
                localId.set(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 释放锁
     * @return 成功 or 失败
     */
    public boolean unlock(String key) {
        try {
            return Optional.ofNullable(
                    redisTemplate.execute(
                            script,
                            Collections.singletonList(LOCK_PREFIX + key),
                            localId.get())
            ).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            localId.remove();
        }
    }

    @Bean
    public DefaultRedisScript<Boolean> defaultRedisScript() {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
        script.setResultType(Boolean.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/lock.lua")));
        return script;
    }
}