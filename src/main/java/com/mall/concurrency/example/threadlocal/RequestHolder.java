package com.mall.concurrency.example.threadlocal;

/**
 * @author: JieEn
 * @date: 2020/10/11 11:00
 * @version: 1.0
 */
public class RequestHolder {

    private static final ThreadLocal<Long> requestHolder = new ThreadLocal<Long>();

    public static void add(Long id){
        requestHolder.set(id);
    }

    public static Long getId(){
        return requestHolder.get();
    }

    public static void remove(){
        requestHolder.remove();
    }
}
