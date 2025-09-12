package com.borisov.howtodeservespring.infra;

import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

//@Component
public class LogProxyConfigurator implements ProxyConfigurator, BeanPostProcessor {


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return replaceWithProxy(null,null,bean);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T replaceWithProxy(T currentObject, Class<T> originalClass, Object originalObject) {
        if (getLogMethods(originalClass).isEmpty()) return currentObject;

        if (!Modifier.isFinal(originalClass.getModifiers())) {
            // CGLIB Proxy - приоритетный выбор для не-final классов
            return (T) Enhancer.create(originalClass,
                                       createMethodInterceptor(currentObject, originalClass, originalObject));
        }

        Class<?>[] interfaces = ClassUtils.getAllInterfacesForClass(originalClass);
        if (interfaces.length > 0) {
            // JDK Proxy для final классов с интерфейсами
            return (T) Proxy.newProxyInstance(
                    originalClass.getClassLoader(),
                    interfaces,
                    createInvocationHandler(currentObject, originalClass, originalObject));
        }

        throw new IllegalArgumentException(
                "Зачем вы издеваетесь! Я не буду инструментировать ваш класс, а другого выбора вы мне не оставляете. " +
                        "Класс " + originalClass.getSimpleName() + " является final и не имеет интерфейсов.");
    }

    private InvocationHandler createInvocationHandler(Object currentObject, Class<?> originalClass, Object originalObject) {
        return (proxy, method, args) -> {
            processLogAnnotation(method, originalClass, originalObject);
            return method.invoke(currentObject, args);
        };
    }

    private MethodInterceptor createMethodInterceptor(Object currentObject, Class<?> originalClass, Object originalObject) {
        return (obj, method, args, methodProxy) -> {
            processLogAnnotation(method, originalClass, originalObject);
            return method.invoke(currentObject, args);
        };
    }

    private void processLogAnnotation(Method method, Class<?> originalClass, Object originalObject) throws NoSuchMethodException {
        Log annotation = originalClass.getMethod(method.getName(), method.getParameterTypes())
                                      .getAnnotation(Log.class);
        if (annotation != null) {
            FieldPrinterUtils.printFieldsWithValues(annotation.value(), originalObject);
        }
    }

    private List<Method> getLogMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                     .filter(method -> method.isAnnotationPresent(Log.class))
                     .toList();
    }
}