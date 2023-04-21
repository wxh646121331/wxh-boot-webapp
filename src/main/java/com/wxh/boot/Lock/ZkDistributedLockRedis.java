package com.wxh.boot.Lock;

import lombok.AllArgsConstructor;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author wuxinhong
 * @date 2023/3/6 11:13 PM
 */
@AllArgsConstructor
public class ZkDistributedLockRedis implements Lock {

    private static final String ROOT_PATH = "/locks";
    private ZooKeeper zooKeeper;

    private String lockName;


    @Override
    public void lock() {
        try {
            this.zooKeeper.create(ROOT_PATH + "/" + lockName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            this.zooKeeper.create(ROOT_PATH + "/" + lockName, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }

}
