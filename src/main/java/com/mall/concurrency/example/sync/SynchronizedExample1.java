package com.mall.concurrency.example.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Synchronized
 *
 * @author: JieEn
 * @date: 2020/10/10 19:20
 * @version: 1.0
 */
public class SynchronizedExample1 {
    private static Logger logger = LoggerFactory.getLogger(SynchronizedExample1.class);

    //修饰一个代码块
    public void test1(int j) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                logger.info("test1 - {} - {}", j, i);
            }
        }
    }

    //修饰一个方法
    public synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            logger.info("test2 - {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample1 synchronizedExample1 = new SynchronizedExample1();
        SynchronizedExample1 synchronizedExample2 = new SynchronizedExample1();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            synchronizedExample1.test2(1);
        });
        executorService.execute(() -> {
            synchronizedExample2.test2(2);
        });
    }
}
