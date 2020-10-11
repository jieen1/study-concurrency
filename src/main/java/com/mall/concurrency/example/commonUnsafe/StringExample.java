package com.mall.concurrency.example.commonUnsafe;

import com.mall.concurrency.annotation.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * StringBuilder
 * @author: JieEn
 * @date: 2020/10/11 18:12
 * @version: 1.0
 */
@NotThreadSafe
public class StringExample {
    private static Logger logger = LoggerFactory.getLogger(StringExample.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 200;
    public static StringBuilder stringBuilder = new StringBuilder();

    private static void add(){
        stringBuilder.append("1");
    }

    public static void main(String args[]) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        logger.info("count:{}",stringBuilder.length());
        executorService.shutdown();
    }
}
