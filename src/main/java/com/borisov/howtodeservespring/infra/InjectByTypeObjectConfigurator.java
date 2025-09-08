package com.borisov.howtodeservespring.infra;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class InjectByTypeObjectConfigurator implements ObjectConfigurator {


    @SneakyThrows
    @Override
    public void configure(Object target) {
        Set<Field> allFields = ReflectionUtils.getAllFields(target.getClass());

        for (Field field : allFields) {
            InjectByType annotation = field.getAnnotation(InjectByType.class);
            if (annotation != null) {
                Object dependency = ObjectFactory.getInstance().createObject(field.getType());
                if (dependency != null) {
                    field.setAccessible(true);
                    field.set(target, dependency);
                }
            }
        }
    }
}
