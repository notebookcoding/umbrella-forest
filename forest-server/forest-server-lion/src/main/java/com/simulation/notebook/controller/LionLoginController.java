package com.simulation.notebook.controller;

import com.simulation.notebook.config.LionConfig;
import com.simulation.notebook.result.Result;
import com.simulation.notebook.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 */

@Slf4j
@RestController
@RequestMapping("/lion/login")
public class LionLoginController {

    @Resource
    private LionConfig lionConfig;

    @Resource
    private RedisUtil redisUtil;


    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Result<?> viewAnswerSheet(@PathVariable Long userId) {

        log.info("commonManage:" + lionConfig.getCommonManage());
        redisUtil.set("manage", lionConfig.getCommonManage());

        return Result.success(userId + "--" + lionConfig.getNacosAddr());
    }
}
