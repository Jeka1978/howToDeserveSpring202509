package com.borisov.howtodeservespring.infra;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Set;

@Configuration
public class InjectPropertyObjectConfigurator implements ObjectConfigurator, BeanPostProcessor {


    private Properties properties = loadProperties();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        configure(bean);
        return bean;
    }

    @SneakyThrows
    @Override
    public void configure(Object o) {
        Set<Field> allFields = ReflectionUtils.getAllFields(o.getClass());

        for (Field field : allFields) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String propertyName = annotation.value();
                String propertyValue = (String) properties.get(propertyName);
                field.setAccessible(true);
                field.set(o, convertValue(propertyValue, field.getType()));
            }
        }
    }

    private Object convertValue(String value, Class<?> targetType) {
        if (targetType == String.class) return value;
        if (targetType == Integer.class || targetType == int.class) return Integer.parseInt(value);
        if (targetType == Double.class || targetType == double.class) return Double.parseDouble(value);
        throw new IllegalArgumentException("Unsupported type: " + targetType);

    }


    private Properties loadProperties() {
        var props = new Properties();
        try (var inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("application.properties not found in classpath");
            }
            props.load(inputStream);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load application.properties", e);
        }
    }

}
