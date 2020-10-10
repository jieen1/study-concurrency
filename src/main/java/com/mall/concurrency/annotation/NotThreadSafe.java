package com.mall.concurrency.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标记线程不安全的写法
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)//编译忽略
public @interface NotThreadSafe {
    String value() default "";
}
