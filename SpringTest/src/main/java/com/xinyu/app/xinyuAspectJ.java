package com.xinyu.app;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class xinyuAspectJ {

    @Pointcut("execution (* com.xinyu.Service.UserService.*(..))")
    public void anyMethond(){

    }

    @Before("anyMethond()")
    public void before(){
        System.out.println("before=============aop");
    }
}
