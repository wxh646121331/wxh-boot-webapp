package com.wxh.boot.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuxinhong
 * @date 2022/12/24 9:59 PM
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");  // redis服务器地址
//                .setDatabase(0)  // 指定数据库编号
//                .setUsername("").setPassword("")  // 设置账号密码
//                .setConnectionMinimumIdleSize(10)  //  连接池最小空闲连接数
//                .setConnectionPoolSize(50)   // 连接池最大线程数
//                .setIdleConnectionTimeout(60000)  // 线程超时时间
//                .setConnectTimeout(600000)  // 客户端程序获取redis连接的超时时间
//                .setTimeout(60000);   // 响应超时时间
        return Redisson.create(config);
    }

    @Bean
    public RedissonClient redissonClient2(){
        return Redisson.create();
    }
}
