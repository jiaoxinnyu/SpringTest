package com.xinyu.Service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class xinyuBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("扩展方法");
        GenericBeanDefinition  custerService = (GenericBeanDefinition) configurableListableBeanFactory.getBeanDefinition("custerService");
        //custerService.setBeanClass(UserService.class);
    }
}
