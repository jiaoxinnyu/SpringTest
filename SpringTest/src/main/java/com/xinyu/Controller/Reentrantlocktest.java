package com.xinyu.Controller;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Reentrantlocktest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        countDownLatch.await();
        countDownLatch.countDown();


    }

    public void hh(){
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        for (;;){

        }
    }
}
