package com.mall.concurrency.example.atomic;

import com.mall.concurrency.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: JieEn
 * @date: 2020/10/10 17:08
 * @version: 1.0
 */
@ThreadSafe
public class AtomicExample2 {
    private static Logger logger = LoggerFactory.getLogger(AtomicExample2.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 50;
    public static AtomicLong count = new AtomicLong(0);

    private static void add(){
        count.incrementAndGet();
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
        logger.info("count:{}",count.get());
        executorService.shutdown();
    }
}
