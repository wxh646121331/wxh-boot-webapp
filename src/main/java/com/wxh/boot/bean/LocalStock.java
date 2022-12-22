package com.wxh.boot.bean;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wuxinhong
 * @date 2022/9/15 11:04 上午
 */
public class LocalStock {
    ReentrantLock lock = new ReentrantLock();
    private int count = 5000;

    public void deduct1(){
        count = count-1;
        System.out.println("deduct1剩余库存：" + count);
    }

    public synchronized void deduct2(){
        count = count -1;
        System.out.println("deduct2剩余库存：" + count);
    }

    public void deduct3(){
        lock.lock();
        try{
            count = count -1;
            System.out.println("deduct3剩余库存：" + count);
        }finally {
            lock.unlock();
        }
    }
}
