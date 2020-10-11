package com.mall.concurrency.example.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: JieEn
 * @date: 2020/10/11 22:09
 * @version: 1.0
 */
public class CountDownLatchExample1 {
    private static Logger logger = LoggerFactory.getLogger(CountDownLatchExample1.class);

    private static final int threadCount =200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int j = i;
            executorService.execute(() ->{
                try {
                    test(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(10, TimeUnit.MILLISECONDS);//等待10毫秒
        logger.info("finish");
        executorService.shutdown();
    }

    private static void test(int num) throws InterruptedException {
//        Thread.sleep(100);
        logger.info("{}",num);
//        Thread.sleep(100);
    }
}
