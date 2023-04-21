package com.wxh.boot.redis;

import redis.clients.jedis.Jedis;

/**
 * @author wuxinhong
 * @date 2023/4/21 9:06 AM
 */
public class JedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");
        System.out.println(jedis.ping());
        jedis.set("testJedis", "hello jedis");
        System.out.println(jedis.get("testJedis"));
    }
}
