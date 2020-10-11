package com.mall.concurrency.example.aqs;

import ch.qos.logback.core.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: JieEn
 * @date: 2020/10/11 22:09
 * @version: 1.0
 */
public class SemaphoreExample3 {
    private static Logger logger = LoggerFactory.getLogger(SemaphoreExample3.class);

    private static final int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(20);

        for (int i = 0; i < threadCount; i++) {
            final int j = i;
            executorService.execute(() -> {
                try {
                    if (semaphore.tryAcquire(5, TimeUnit.SECONDS)) {//尝试获取许可，获取失败则丢弃该线程
                        test(j);
                        semaphore.release();//释放多个许可
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                }
            });
        }
        executorService.shutdown();
    }

    private static void test(int num) throws InterruptedException {
        logger.info("{}", num);
        Thread.sleep(1000);
    }
}
