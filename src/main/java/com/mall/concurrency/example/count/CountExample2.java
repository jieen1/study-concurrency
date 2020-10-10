package com.mall.concurrency.example.count;

import com.mall.concurrency.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: JieEn
 * @date: 2020/10/10 17:08
 * @version: 1.0
 */
@ThreadSafe
public class CountExample2 {
    private static Logger logger = LoggerFactory.getLogger(CountExample2.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 50;
    public static int count = 0;

    private synchronized static void add(){
        count++;
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
        logger.info("count:{}",count);
        executorService.shutdown();
    }
}
