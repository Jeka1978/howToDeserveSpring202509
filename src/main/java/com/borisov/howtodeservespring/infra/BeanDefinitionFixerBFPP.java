package com.borisov.howtodeservespring.infra;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class BeanDefinitionFixerBFPP implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] definitionNames = beanFactory.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(definitionName);
            if (beanDefinition.getBeanClassName() == null) {
                Class<?> beanClass = beanDefinition.getResolvableType().resolve();
                if (beanClass == null) {
                    continue;
                }
                if (beanClass.getPackageName().contains("borisov")) {
                    beanDefinition.setBeanClassName(beanClass.getName());
                }
            }
        }
    }
}
