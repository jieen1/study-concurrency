package com.mall.concurrency.publicsh;

import com.mall.concurrency.annotation.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

/**
 * 对象逸出（不太理解）
 *
 * @author: JieEn
 * @date: 2020/10/10 21:28
 * @version: 1.0
 */
@NotThreadSafe
public class Escape {
    private static Logger logger = LoggerFactory.getLogger(Escape.class);

    private int thisCanBeEscape = 10;


    public Escape() {
        new InnerClass();
        logger.info("Escape构造完成，thisCanBeEscape={} - - {}",thisCanBeEscape,this.toString());
    }

    private class InnerClass{
        public InnerClass(){
            new Thread(()->{
                Escape.this.thisCanBeEscape ++;
                logger.info("{} - - {} - - {}",Thread.currentThread().getName(),Escape.this.thisCanBeEscape,Escape.this.toString());
            }).start();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("{}",Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        Escape escape = new Escape();

    }
}
