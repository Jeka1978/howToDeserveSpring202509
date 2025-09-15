package com.borisov.howtodeservespring.aop;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

public class RessurectionSpidersDetectorPointcut extends DynamicMethodMatcherPointcut {

    @Value("${spider.special.names}")
    private String specialNamesPartes;

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        return method.getName().contains("loseLife");
    }

    @Override
    public ClassFilter getClassFilter() {
        return clazz -> clazz.getSimpleName().contains(specialNamesPartes);
    }
}

