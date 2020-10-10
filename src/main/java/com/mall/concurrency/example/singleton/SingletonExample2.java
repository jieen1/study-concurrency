package com.mall.concurrency.example.singleton;

import com.mall.concurrency.annotation.NotRecommend;
import com.mall.concurrency.annotation.ThreadSafe;

/**
 * 懒汉模式（第一次使用时创建对象）
 * 性能较差
 *
 * @author: JieEn
 * @date: 2020/10/10 21:52
 * @version: 1.0
 */
@ThreadSafe
@NotRecommend
public class SingletonExample2 {

    //私有构造函数
    private SingletonExample2(){
    }

    //单例对象
    private static SingletonExample2 instance = null;

    //静态的工厂方法
    public synchronized static SingletonExample2 getInstance(){
        if (null == instance){
            instance = new SingletonExample2();
        }
        return instance;
    }
}
