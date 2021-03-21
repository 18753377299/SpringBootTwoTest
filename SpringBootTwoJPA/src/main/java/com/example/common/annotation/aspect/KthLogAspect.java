package com.example.common.annotation.aspect;

import com.example.common.annotation.method.KthLog;
import io.swagger.annotations.Api;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

@Component
@Aspect
@Api(value="自定义注解KthLog的切面类")
public class KthLogAspect {
    @Pointcut("@annotation(com.example.common.annotation.method.KthLog)")
    private void pointcut(){ }

    @Before("pointcut() && @annotation(logger)")
    private void advice(JoinPoint joinPoint, KthLog logger){
        System.out.println("注解作用的方法名: " + joinPoint.getSignature().getName());
        System.out.println("所在类的类名: " + joinPoint.getSignature().getDeclaringType().getSimpleName());
        System.out.println("所在类的完整类名: " + joinPoint.getSignature().getDeclaringType());
        System.out.println("目标方法的声明类型: " + Modifier.toString(joinPoint.getSignature().getModifiers()));
        System.out.println("--- Kth日志的内容为[" + logger.value() + "] ---");
    }

    @Around("pointcut() && @annotation(logger)")
    private Object around(ProceedingJoinPoint joinPoint, KthLog logger){
//        System.out.println("注解作用的方法名: " + joinPoint.getSignature().getName());
//        System.out.println("所在类的类名: " + joinPoint.getSignature().getDeclaringType().getSimpleName());
//        System.out.println("所在类的完整类名: " + joinPoint.getSignature().getDeclaringType());
//        System.out.println("目标方法的声明类型: " + Modifier.toString(joinPoint.getSignature().getModifiers()));
        System.out.println("--- Kth日志的内容为[" +joinPoint.getSignature().getDeclaringType().getSimpleName()+"]"
                +"["+joinPoint.getSignature().getName()+"]"
                +"[" + logger.value() + "] ---");
        Object result =null;

        /**获取请求的参数值*/
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(args[i] instanceof Integer) {
                args[i] = (Integer)args[i] - 1;
                break;
            }
        }
        try {
            result =  joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}
