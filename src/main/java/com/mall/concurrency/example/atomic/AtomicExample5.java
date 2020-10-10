package com.mall.concurrency.example.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author: JieEn
 * @date: 2020/10/10 17:08
 * @version: 1.0
 */
public class AtomicExample5 {
    private static Logger logger = LoggerFactory.getLogger(AtomicExample5.class);
    private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class,"count");
    public volatile int count = 100;
    private static AtomicExample5 example5 = new AtomicExample5();

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        if (updater.compareAndSet(example5,100,120)){
            logger.info("update success:{}",example5.getCount());
        }
        if (updater.compareAndSet(example5,100,120)){
            logger.info("update success:{}",example5.getCount());
        }else
            logger.info("update failed:{}",example5.getCount());
    }
}
