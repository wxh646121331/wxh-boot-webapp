package com.wxh.boot.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author wuxinhong
 * @date 2022/12/23 5:13 PM
 */
@Component
public class DistributedLockClient {
    private String serverId;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public DistributedLockClient(){
        this.serverId = UUID.randomUUID().toString();
    }

    public DistributedLockRedis getRedisLock(String lockName){
        return new DistributedLockRedis(stringRedisTemplate, lockName, serverId);
    }
}
