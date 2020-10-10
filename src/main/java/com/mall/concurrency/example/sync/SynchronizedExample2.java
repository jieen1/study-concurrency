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
public class SynchronizedExample2 {
    private static Logger logger = LoggerFactory.getLogger(SynchronizedExample2.class);

    //修饰一个代码块
    public static void test1(int j) {
        synchronized (SynchronizedExample2.class) {
            for (int i = 0; i < 10; i++) {
                logger.info("test1 - {} - {}", j, i);
            }
        }
    }

    //修饰一个方法
    public static synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            logger.info("test2 - {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        SynchronizedExample2 synchronizedExample1 = new SynchronizedExample2();
        SynchronizedExample2 synchronizedExample2 = new SynchronizedExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            synchronizedExample1.test1(1);
        });
        executorService.execute(() -> {
            synchronizedExample2.test1(2);
        });
    }
}
