package com.mall.concurrency.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 推荐写法
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)//编译忽略
public @interface Recommend {
    String value() default "";
}
