package com.wxh.boot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxh.boot.Lock.DistributedLockClient;
import com.wxh.boot.Lock.DistributedLockRedis;
import com.wxh.boot.config.RedissonConfig;
import com.wxh.boot.entity.Stock;
import com.wxh.boot.mapper.StockMapper;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuxinhong
 * @date 2022/12/20 3:23 PM
 */
@Service
// 多例模式导致JVM锁失效
//@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StockService {
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DistributedLockClient distributedLockClient;

    ReentrantLock lock = new ReentrantLock();

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 事务会导致JVM锁失效
     */
//    @Transactional
    public void deduct(){
        lock.lock();
        try{
            Stock stock = this.stockMapper.selectOne(new QueryWrapper<Stock>().eq("product_code", "1001"));
            if(null != stock && stock.getCount() >= 0){
                stock.setCount(stock.getCount() -1);
                stockMapper.updateById(stock);
            }
        }finally {
            lock.unlock();
        }

    }

    public void deduct1(){
//        lock.lock();
        try{
            stockMapper.updateStock("1001", 1);
        }finally {
//            lock.unlock();
        }
    }

    /**
     * 悲欢锁
     */
    @Transactional
    public void deduct2(){
        List<Stock> stocks = stockMapper.queryStockForUpdate("1001");
        for(Stock stock : stocks){
            if(stock.getCount() > 0){
                stock.setCount(stock.getCount() -1);
                stockMapper.updateById(stock);
                break;
            }
        }
    }

    public boolean deduct3(){
        while(true){
            boolean hasStock = false;
            List<Stock> stocks = stockMapper.selectList(new QueryWrapper<Stock>().eq("product_code", "1001"));
            for(Stock stock : stocks){
                int version = stock.getVersion();
                if(stock.getCount() <= 0){
                    continue;
                }
                hasStock = true;
                stock.setCount(stock.getCount() - 1);
                stock.setVersion(stock.getVersion() + 1);
                int res = stockMapper.update(stock, new QueryWrapper<Stock>().eq("id", stock.getId()).eq("version", version));
                if(res > 0){
                    System.out.println("扣减库存成功");
                    return true;
                }
            }
            if(!hasStock){
                System.out.println("没有足够的库存了");
                return false;
            }
        }
    }

    public boolean deduct4(){
        stringRedisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.watch("stock");
                String stock = stringRedisTemplate.opsForValue().get("stock");
                if(null != stock && stock.length()>0){
                    Integer st = Integer.valueOf(stock);
                    if(st>0){
                        redisOperations.multi();
                        redisOperations.opsForValue().set("stock", String.valueOf(--st));
                        List exec = redisOperations.exec();
                        if(CollectionUtils.isEmpty(exec)){
                            try {
                                TimeUnit.MILLISECONDS.sleep(40L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            deduct4();
                        }
                        return exec;
                    }
                }
                return null;
            }
        });
        return true;
    }

    public boolean deduct5(){
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 3, TimeUnit.SECONDS);
        while (!lock){
            try {
                TimeUnit.MILLISECONDS.sleep(50L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 3, TimeUnit.SECONDS);
        }
        boolean res = false;
        try{
            String stock = stringRedisTemplate.opsForValue().get("stock");
            if(null != stock && stock.length()>0) {
                Integer st = Integer.valueOf(stock);
                if(st<=0){
                    res = false;
                }else {
                    stringRedisTemplate.opsForValue().set("stock", String.valueOf(--st));
                    res = true;
                }
            }
        }finally {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] \n" +
                    "then return redis.call('del', KEYS[1]) \n" +
                    "else return 0 \n" +
                    "end";
            List<String> keys = Arrays.asList("lock");
            Boolean unlock = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), keys, uuid);
            System.out.println("释放锁结果：" + unlock);
        }
        return res;
    }

    /**
     * 可重入分布式锁
     * @return
     */
    public boolean deduct6(){
        DistributedLockRedis redisLock = distributedLockClient.getRedisLock("lock");
        redisLock.lock();
        boolean res = false;
        try{
            String stock = stringRedisTemplate.opsForValue().get("stock");
            if(null != stock && stock.length()>0) {
                Integer st = Integer.valueOf(stock);
                if(st<=0){
                    res = false;
                }else {
                    stringRedisTemplate.opsForValue().set("stock", String.valueOf(--st));
                    res = true;
                }
            }
            test();
        }finally {
            redisLock.unlock();
        }
        return res;
    }

    private void test(){
        DistributedLockRedis redisLock = distributedLockClient.getRedisLock("lock");
        redisLock.lock();
        System.out.println("测试重入锁");
        redisLock.unlock();
    }

    public boolean deduct7(){
        RLock rlock = redissonClient.getLock("lock");
        rlock.lock(30L, TimeUnit.SECONDS);
        boolean res = false;
        try{
            String stock = stringRedisTemplate.opsForValue().get("stock");
            if(null != stock && stock.length()>0) {
                Integer st = Integer.valueOf(stock);
                if(st<=0){
                    res = false;
                }else {
                    stringRedisTemplate.opsForValue().set("stock", String.valueOf(--st));
                    res = true;
                }
            }
        }finally {
            rlock.unlock();
        }
        return res;
    }

    public boolean readLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("readWriteLock");
        lock.readLock().lock(10L, TimeUnit.SECONDS);
        return true;
    }

    public boolean writeLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("readWriteLock");
        lock.writeLock().lock(10L, TimeUnit.SECONDS);
        return true;
    }

    public void semaphore() {
        RSemaphore semaphore = redissonClient.getSemaphore("semaphore");
        semaphore.trySetPermits(3);
        try {
            semaphore.acquire();
            // TODO 业务逻辑
            semaphore.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void testCountDownLatch(){
        RCountDownLatch anyCountDownLatch = redissonClient.getCountDownLatch("anyCountDownLatch");
    }

    public void latch(){
        RCountDownLatch anyCountDownLatch = redissonClient.getCountDownLatch("anyCountDownLatch");
        anyCountDownLatch.trySetCount(6L);
        try {
            anyCountDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("班长锁门了");
    }

    public void countdown() {
        RCountDownLatch anyCountDownLatch = redissonClient.getCountDownLatch("anyCountDownLatch");
        anyCountDownLatch.countDown();
        System.out.println("出来了一位同学");
    }


}
