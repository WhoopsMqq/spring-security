package com.whoops.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {

//    @Before()
//
//
//    @After()
//
//
//    @AfterThrowing


    @Around("execution(* com.whoops.controller.UserController.*(..))")//UserController里所有方法执行时都会被调用
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
//        System.out.println("around_start");
        Object[] args = pjp.getArgs();//方法的参数
        Object obj = pjp.proceed();
//        System.out.println("around_end");
        return obj;
    }
}



















