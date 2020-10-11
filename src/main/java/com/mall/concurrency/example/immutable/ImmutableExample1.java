package com.mall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mall.concurrency.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

/**
 * @author: JieEn
 * @date: 2020/10/11 9:50
 * @version: 1.0
 */
@ThreadSafe
public class ImmutableExample1 {
    private static Logger logger = LoggerFactory.getLogger(ImmutableExample1.class);

    private static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1, 5);//不允许操作
        logger.info("Maps:{}", map.get(1));
    }

}
