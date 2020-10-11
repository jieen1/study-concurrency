package com.mall.concurrency.example.commonUnsafe;

import com.mall.concurrency.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * SimpleDateFormat
 * @author: JieEn
 * @date: 2020/10/11 18:18
 * @version: 1.0
 */
@ThreadSafe
public class DateFormatExample1 {

    private static Logger logger = LoggerFactory.getLogger(DateFormatExample1.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 200;

    private static void add(){
        //使用堆栈封闭不会有多线程问题
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse("2020-01-10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
//        logger.info("count:{}",stringBuilder.length());
        executorService.shutdown();
    }
}
