package com.mall.concurrency.example.aqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: JieEn
 * @date: 2020/10/11 22:35
 * @version: 1.0
 */
public class CyclicBarrierExample {
    private static Logger logger = LoggerFactory.getLogger(CyclicBarrierExample.class);
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);//同步等待的总线程数
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(()->{
                try {
                    race(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            executorService.shutdown();
        }
    }

    private static void race(final int num) throws Exception {
        Thread.sleep(1000);
        logger.info("{} is ready",num);
        cyclicBarrier.await();//线程执行完毕后调用await()方法
        logger.info("{} is continue",num);
    }
}
