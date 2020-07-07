package com.xinyu.Controller;

import com.xinyu.Service.UserService;
import com.xinyu.app.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserController {
    public static void main(String[] args) {
        //ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(AppConfig.class);
        annotationConfigApplicationContext.setAllowBeanDefinitionOverriding(false);
        annotationConfigApplicationContext.refresh();
        System.out.println("context 启动成功");


        System.out.println(annotationConfigApplicationContext.getBean(UserService.class).getCusterService());

    }
}
