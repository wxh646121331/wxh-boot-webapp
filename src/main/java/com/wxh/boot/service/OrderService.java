package com.wxh.boot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wuxinhong
 * @date 2023/4/23 3:24 PM
 */
@Slf4j
@Service
public class OrderService {
    public static final String ORDER_KEY = "ord:";

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void addOrder(){
        int keyId = ThreadLocalRandom.current().nextInt(1000);
        String serialNo = UUID.randomUUID().toString();

        String key = ORDER_KEY + keyId;
        String value = "订单:" + serialNo;

        redisTemplate.opsForValue().set(key, value);

        log.info("key:{}, value:{}", key, value);

    }

    public String getOrderById(Integer id){
        String key = ORDER_KEY + id;
        return (String) redisTemplate.opsForValue().get(key);
    }

}
