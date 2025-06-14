package com.example.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义的Log注解
 * 用于动态挑选切片对象
 */

@Retention(RetentionPolicy.RUNTIME) //选择运行时机
@Target(ElementType.METHOD) //选择切片对象
public @interface Log {
}
