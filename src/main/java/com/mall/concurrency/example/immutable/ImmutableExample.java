package com.mall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mall.concurrency.annotation.NotThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author: JieEn
 * @date: 2020/10/11 9:50
 * @version: 1.0
 */
@NotThreadSafe
public class ImmutableExample {
    private static Logger logger = LoggerFactory.getLogger(ImmutableExample.class);

    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
    }

    public static void main(String[] args) {
        map.put(1, 5);
        logger.info("Maps:{}", map.get(1));
    }

    private void test(final int a) {
//        a= 2;   不能进行修改
    }
}
