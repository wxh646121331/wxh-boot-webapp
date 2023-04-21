package com.wxh.boot.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wuxinhong
 * @date 2023/2/3 1:54 PM
 */
public class ZkClientTest {
    public static void main(String[] args) throws InterruptedException {
        ZooKeeper zooKeeper = null;
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 30 * 1000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(Event.KeeperState.SyncConnected.equals(event.getState()) && Event.EventType.None.equals(event.getType())){
                        System.out.println("zk 连上了吗？状态："+event.getState());
                        countDownLatch.countDown();
                    }else if(Event.KeeperState.Closed.equals(event.getState())){
                        System.out.println("zk 已关闭");
                    }else {
                        System.out.println("节点事件...");
                    }
                }
            });
            countDownLatch.await();
            System.out.println("一顿操作");
            // stat 操作
            Stat stat = zooKeeper.exists("/wxh", true);
            if(null == stat){
                zooKeeper.create("/wxh", "value1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            zooKeeper.create("/wxh/test", "value2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            zooKeeper.create("/wxh/test", "value3".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            zooKeeper.create("/wxh/test", "value4".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            ZooKeeper finalZooKeeper = zooKeeper;
            // ls
            List<String> children = zooKeeper.getChildren("/wxh", event -> System.out.println("/wxh 的子节点发生变化"));
            System.out.println(children);
            // get
            byte[] data = zooKeeper.getData("/wxh", true, stat);
            System.out.println(new String(data));
            // 更新：版本号必须一致，或者设置成-1则不关心版本号
            zooKeeper.setData("/wxh", "hello".getBytes(), stat.getVersion());
            if(null != zooKeeper.exists("/wxh/test1", true)){
                zooKeeper.delete("/wxh/test1", -1);
            }
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != zooKeeper){
                zooKeeper.close();
            }
        }
    }
}
