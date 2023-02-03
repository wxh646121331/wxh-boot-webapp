package com.wxh.boot.Lock;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author wuxinhong
 * @date 2022/12/23 5:09 PM
 */
public class DistributedLockRedis implements Lock {

    private static final String LOCK_SCRIPT = "if redis.call('exists', KEYS[1]) == 0 or redis.call('hexists', KEYS[1], ARGV[1]) == 1\n" +
            "then\n" +
            "    redis.call('hincrby', KEYS[1], ARGV[1], 1)\n" +
            "    redis.call('expire', KEYS[1], ARGV[2])\n" +
            "    return 1\n" +
            "else\n" +
            "    return 0\n" +
            "end";

    private static final String RELEASE_SCRIPT = "if redis.call('hexists', KEYS[1], ARGV[1]) == 0\n" +
            "then\n" +
            "    return nil\n" +
            "elseif redis.call('hincrby', KEYS[1], ARGV[1], -1) == 0\n" +
            "then\n" +
            "    redis.call('del', KEYS[1])\n" +
            "    return 1\n" +
            "else\n" +
            "    return 0\n" +
            "end";
    private StringRedisTemplate stringRedisTemplate;

    private String lockName;

    private String uuid;

    private Long expire = 30L;

    public DistributedLockRedis(StringRedisTemplate redisTemplate, String lockName, String serverId){
        this.stringRedisTemplate = redisTemplate;
        this.lockName = lockName;
        this.uuid = serverId + ":" + Thread.currentThread().getId();
    }

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(-1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        if(time >= 0L){
            expire = unit.toSeconds(time);
        }
        while(!stringRedisTemplate.execute(new DefaultRedisScript<>(LOCK_SCRIPT, Boolean.class), Arrays.asList(lockName), uuid, String.valueOf(expire))){
            TimeUnit.MILLISECONDS.sleep(50L);
        }
        this.renewExpire();
        return true;
    }

    @Override
    public void unlock() {
        Long execute = stringRedisTemplate.execute(new DefaultRedisScript<>(RELEASE_SCRIPT, Long.class), Arrays.asList(lockName), uuid);
        if(null == execute){
            throw new IllegalMonitorStateException("this lock is not belong you");
        }
    }

    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * 重置过期时间
     * @return
     */
    private void renewExpire(){
        String script = "if redis.call('hexists', KEYS[1], ARGV[1]) == 1 then return redis.call('expire', KEYS[1], ARGV[2]) else return 0 end";
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Boolean flag = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList(lockName), uuid, String.valueOf(expire));
                if(flag){
                    renewExpire();
                }
            }
        }, expire * 1000 / 3);
    }
}
