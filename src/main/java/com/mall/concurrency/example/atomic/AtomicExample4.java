package com.mall.concurrency.example.atomic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: JieEn
 * @date: 2020/10/10 17:08
 * @version: 1.0
 */
public class AtomicExample4 {
    private static Logger logger = LoggerFactory.getLogger(AtomicExample4.class);
    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0,2);//2
        count.compareAndSet(0,1);//no
        count.compareAndSet(1,3);//no
        count.compareAndSet(2,4);//4
        count.compareAndSet(3,5);//no
        logger.info("count:{}",count.get());//count:4
    }
}
