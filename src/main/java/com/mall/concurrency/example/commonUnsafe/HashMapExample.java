package com.mall.concurrency.example.commonUnsafe;

import com.mall.concurrency.annotation.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: JieEn
 * @date: 2020/10/11 18:37
 * @version: 1.0
 */
@NotThreadSafe
public class HashMapExample {
    private static HashMap<Integer, Integer> map = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(HashMapExample.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 200;

    private static void add(final int i) {
        map.put(i, i);
    }

    public static void main(String args[]) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            final int j = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add(j);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        logger.info("size:{}", map.size());
        executorService.shutdown();
    }
}
