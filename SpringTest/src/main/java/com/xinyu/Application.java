package com.xinyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jiaoxy
 */
@SpringBootApplication
@ComponentScan("com.xinyu")
@MapperScan(basePackages = "com.xinyu.mapper")
@EnableScheduling
public class Application {
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
}
