package com.mall.concurrency.publicsh;

import com.mall.concurrency.annotation.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 任何线程可以通过UnsafePublish对象所提供的getStates()公共方法来获取其中私有域states的对象的引用，
 * 即所有线程都可以修改该对象的私有域，当我们使用states时，就无法确定其值，所以是线程不安全的。
 * @author: JieEn
 * @date: 2020/10/10 21:20
 * @version: 1.0
 */
@NotThreadSafe
public class UnsafePublish {
    private static Logger logger = LoggerFactory.getLogger(UnsafePublish.class);

    private String[] states = {"a","b","c"};

    public String[] getStates() {
        return states;
    }

    public static void main(String[] args) {
        UnsafePublish unsafePublish = new UnsafePublish();
        logger.info("{}", Arrays.toString(unsafePublish.getStates()));

        unsafePublish.getStates()[0] = "d";
        logger.info("{}", Arrays.toString(unsafePublish.getStates()));
    }
}
