package com.wxh.boot.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author wuxinhong
 * @date 2023/4/22 10:44 PM
 */
public class LettuceTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisURI redisURI = RedisURI.builder().withHost("127.0.0.1").withPort(6379).withPassword("123456").build();

        RedisClient redisClient = RedisClient.create(redisURI);

        StatefulRedisConnection<String, String> connect = redisClient.connect();

        RedisCommands<String, String> commands = connect.sync();

        commands.set("testLettuce", "hello, lettuce");

        List<String> keys = commands.keys("*");

        System.out.println(keys);

        String value = commands.get("testLettuce");

        System.out.println(value);

        connect.close();

        redisClient.shutdown();

    }
}
