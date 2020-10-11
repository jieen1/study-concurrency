package com.mall.concurrency.example.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: JieEn
 * @date: 2020/10/11 22:09
 * @version: 1.0
 */
public class SemaphoreExample {
    private static Logger logger = LoggerFactory.getLogger(SemaphoreExample.class);

    private static final int threadCount =200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(20);

        for (int i = 0; i < threadCount; i++) {
            final int j = i;
            executorService.execute(() ->{
                try {
                    semaphore.acquire();//获取许可
                    test(j);
                    semaphore.release();//释放许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                }
            });
        }
        logger.info("finish");
        executorService.shutdown();
    }

    private static void test(int num) throws InterruptedException {
        logger.info("{}",num);
        Thread.sleep(1000);
    }
}
