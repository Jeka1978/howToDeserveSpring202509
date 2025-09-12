package com.borisov.howtodeservespring.infra;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogProxyConfigurator implements ProxyConfigurator {
    @Override
    public <T> T replaceWithProxy(T o, Class<T> type) {

        List<Method> logMethods = getMethodsAnnotatedWithLog(type);
        if(logMethods.isEmpty()) return o;

        Proxy.newProxyInstance(
                type.getClassLoader(), ClassUtils.getAllInterfaces(type), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                }
        )

        return null;
    }



    public List<Method> getMethodsAnnotatedWithLog(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                     .filter(method -> method.isAnnotationPresent(Log.class))
                     .collect(Collectors.toList());
    }

    // Альтернативный вариант для получения методов из всех родительских классов
    public List<Method> getAllMethodsAnnotatedWithLog(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods()) // getMethods() включает унаследованные методы
                     .filter(method -> method.isAnnotationPresent(Log.class))
                     .collect(Collectors.toList());
    }

    // Вариант с использованием рефлексии для поиска во всей иерархии классов
    public List<Method> getMethodsWithLogAnnotationFromHierarchy(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        Class<?> currentClass = clazz;

        while (currentClass != null) {
            Arrays.stream(currentClass.getDeclaredMethods())
                  .filter(method -> method.isAnnotationPresent(Log.class))
                  .forEach(methods::add);
            currentClass = currentClass.getSuperclass();
        }

        return methods;
    }
}
