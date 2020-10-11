package com.mall.concurrency.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mall.concurrency.annotation.ThreadSafe;

/**
 * @author: JieEn
 * @date: 2020/10/11 10:49
 * @version: 1.0
 */
@ThreadSafe
public class ImmutableExample2 {
    private final static ImmutableList list = ImmutableList.of(1,2,3);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap map = ImmutableMap.builder().put(1,2).put(3,4).build();

    public static void main(String[] args) {
//        list.add(4);//抛出异常
//        map.put(1,2);//抛出异常
    }
}
