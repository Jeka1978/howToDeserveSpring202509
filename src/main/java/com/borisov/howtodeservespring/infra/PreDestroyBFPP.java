package com.borisov.howtodeservespring.infra;

import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(200)
public class PreDestroyBFPP implements BeanFactoryPostProcessor {

    @Override
    @SneakyThrows
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {


        String[] definitionNames = beanFactory.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(definitionName);
            if (beanDefinition.getDestroyMethodName() != null && beanDefinition.isPrototype()) {
                throw new FailException();
            }

            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName == null) {
                continue;
            }
            Class<?> beanClass = Class.forName(beanClassName);
            boolean existsPredestroyMethod = ReflectionUtils.getAllMethods(beanClass).stream()
                                                            .anyMatch(clazz -> clazz.isAnnotationPresent(PreDestroy.class));

            if (existsPredestroyMethod) {
                throw new FailException();
            }
        }


    }

}
