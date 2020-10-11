package com.mall.concurrency.example.commonUnsafe;

import com.mall.concurrency.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * StringBuffer
 * @author: JieEn
 * @date: 2020/10/11 18:12
 * @version: 1.0
 */
@ThreadSafe
public class StringExample1 {
    private static Logger logger = LoggerFactory.getLogger(StringExample1.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 200;
    public static StringBuffer stringBuffer = new StringBuffer();

    private static void add(){
        stringBuffer.append("1");
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
        logger.info("count:{}",stringBuffer.length());
        executorService.shutdown();
    }
}
