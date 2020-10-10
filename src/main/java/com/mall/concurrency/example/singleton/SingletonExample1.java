package com.mall.concurrency.example.singleton;

import com.mall.concurrency.annotation.ThreadSafe;

/**
 * 饿汉模式（类装载时创建对象）
 * 如果创建时计算过多可能会导致性能问题
 * 如果对象不被使用会造成资源浪费
 *
 * @author: JieEn
 * @date: 2020/10/10 21:52
 * @version: 1.0
 */
@ThreadSafe
public class SingletonExample1 {

    //私有构造函数
    private SingletonExample1(){
    }

    //单例对象
    private static SingletonExample1 instance = new SingletonExample1();

    //静态的工厂方法
    public static SingletonExample1 getInstance(){
        return instance;
    }
}
