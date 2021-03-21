package com.example.common.annotation.method;

import io.swagger.annotations.Api;

import java.lang.annotation.*;

/**
 * https://blog.csdn.net/qq_37435078/article/details/90523309
 * */
@Api(value="自定义方法注解",
        description="在Web项目（这里特指Spring项目）中使用自定义注解开发，其原理还是依赖于Spring的AOP机制")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KthLog {
    String value () default "";
}
