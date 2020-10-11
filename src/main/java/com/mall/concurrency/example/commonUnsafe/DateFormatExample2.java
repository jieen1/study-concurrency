package com.mall.concurrency.example.commonUnsafe;

import com.mall.concurrency.annotation.ThreadSafe;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
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
public class DateFormatExample2 {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    private static Logger logger = LoggerFactory.getLogger(DateFormatExample2.class);

    public static int clientTotal = 5000;
    public static int threadTotal = 200;

    private static void add(){
        Date date = dateTimeFormatter.parseDateTime("2020-01-10").toDate();
        logger.info("date:{}",date.toString());
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
