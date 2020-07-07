package com.xinyu.demo;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class xinyuImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

       /**
        * 在这之前还需要自己定一个扫描注解 类似mybatis的 mapperscan 值就是自己对应的dao 的路径  然后添加到java配置类上面 和mapperscan一样
        * 这里可以通过AnnotationMetadata 拿到注解的值 然后做扫描得到一个集合，这里可以自己做扫描也可以让Spring做扫描 mybatis就是依赖给Spring做 Spring的doScan方法，
        * 拿到所有的BeanDefinition然后注册到registry集合中去
        **/


        BeanDefinitionBuilder beanDefinitionBuilder =  BeanDefinitionBuilder.genericBeanDefinition(xinyuFactoryBean.class);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        //对应的是我们factorybean的 属性方法名 和具体值 factoryBean 通过代理反射出我们dao接口生成一个具体对象，
        // 然后在invoke方法里面提供具体的sql实现
        beanDefinition.getPropertyValues().add("","");

        registry.registerBeanDefinition("xxxx",beanDefinition);
    }
}
