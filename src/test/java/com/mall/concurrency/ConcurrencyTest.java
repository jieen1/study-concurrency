package com.mall.concurrency;

import com.mall.concurrency.annotation.NotThreadSafe;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: JieEn
 * @date: 2020/10/10 16:52
 * @version: 1.0
 */
@NotThreadSafe
public class ConcurrencyTest {
    private Logger logger = LoggerFactory.getLogger(ConcurrencyTest.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 50;
    public static int count = 0;

    private static void add() {
        count++;
    }

    @Test
    public void test() throws InterruptedException {
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
        logger.info("count:{}", count);
        executorService.shutdown();
    }
}
