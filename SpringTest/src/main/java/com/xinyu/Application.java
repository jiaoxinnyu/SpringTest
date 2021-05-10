package com.xinyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author jiaoxy
 */
@SpringBootApplication
@ComponentScan("com.xinyu")
@MapperScan("com.xinyu.mapper")
@EnableOpenApi
public class Application {
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
}
