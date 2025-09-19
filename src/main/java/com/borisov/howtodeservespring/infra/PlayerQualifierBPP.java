package com.borisov.howtodeservespring.infra;

import com.borisov.howtodeservespring.Spider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.type.StandardMethodMetadata;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class PlayerQualifierBPP implements BeanPostProcessor, BeanFactoryPostProcessor {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Spider spider) {
            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);

            if (beanDefinition.getSource() instanceof StandardMethodMetadata source) {
                Method introspectedMethod = source.getIntrospectedMethod();
                if (introspectedMethod.isAnnotationPresent(PlayerQualifier.class)) {
                    PlayerQualifier annotation = introspectedMethod.getAnnotation(PlayerQualifier.class);
                    String          s          = annotation.playerName();
                    spider.setOwner(s);
                }
            }

            if (bean.getClass().isAnnotationPresent(PlayerQualifier.class)) {
                String s = bean.getClass().getAnnotation(PlayerQualifier.class).playerName();
                spider.setOwner(s);
            }
        }
        return bean;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
