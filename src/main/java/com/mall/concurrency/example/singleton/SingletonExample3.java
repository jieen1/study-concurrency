package com.mall.concurrency.example.singleton;

import com.mall.concurrency.annotation.NotThreadSafe;

/**
 * 懒汉模式（第一次使用时创建对象）
 * 双重同步锁单例模式
 *
 * @author: JieEn
 * @date: 2020/10/10 21:52
 * @version: 1.0
 */
@NotThreadSafe
public class SingletonExample3 {

    //私有构造函数
    private SingletonExample3() {
    }

    //单例对象
    private static SingletonExample3 instance = null;

    //静态的工厂方法
    public static SingletonExample3 getInstance() {
        if (null == instance) {//双重检测机制
            synchronized (SingletonExample3.class) {//同步锁
                if (null == instance) {
                    //1. memory = allocate() 分配对象的内存空间
                    //2. ctorInstance() 初始化对象
                    //3. instance = memory 设置instance指向刚分配的内存
                    //JVM和CPU优化会发生指令重排，  可能会导致1 -> 3 -> 2
                    //所以当一个线程执行到第3步时，其他线程判断instance不为null，就会返回未完成初始化的instance
                    instance = new SingletonExample3();
                }
            }
        }
        return instance;
    }
}
