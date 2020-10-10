package com.mall.concurrency.example.singleton;

import com.mall.concurrency.annotation.Recommend;
import com.mall.concurrency.annotation.ThreadSafe;

/**
 * 枚举实现单例,最安全
 *
 * @author: JieEn
 * @date: 2020/10/10 22:26
 * @version: 1.0
 */
@ThreadSafe
@Recommend
public class SingletonExample6 {
    //私有构造函数
    private SingletonExample6(){
    }

    public static SingletonExample6 getInstance(){
        return Singleton.INSTANCE.getSingleton();
    }

    private enum Singleton{
        INSTANCE;

        private SingletonExample6 singleton;

        //JVM保证方法只被调用一次
        Singleton(){
            singleton = new SingletonExample6();
        }

        public SingletonExample6 getSingleton(){
            return singleton;
        }

    }
}
