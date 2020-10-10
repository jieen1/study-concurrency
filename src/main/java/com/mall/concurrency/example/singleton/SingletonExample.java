package com.mall.concurrency.example.singleton;

import com.mall.concurrency.annotation.NotThreadSafe;

/**
 * 懒汉模式（第一次使用时创建对象）
 *
 * @author: JieEn
 * @date: 2020/10/10 21:52
 * @version: 1.0
 */
@NotThreadSafe
public class SingletonExample {

    //私有构造函数
    private SingletonExample(){
    }

    //单例对象
    private static SingletonExample instance = null;

    //静态的工厂方法
    public static SingletonExample getInstance(){
        if (null == instance){
            instance = new SingletonExample();
        }
        return instance;
    }
}
