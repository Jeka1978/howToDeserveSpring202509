package com.borisov.howtodeservespring.infra;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectFactory {
    private final List<ObjectConfigurator> objectConfigurators;
    private final Reflections              scanner;
    private final ApplicationContext       applicationContext;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.applicationContext = context;
        this.scanner            = context.getScanner();

        this.objectConfigurators = scanner.getSubTypesOf(ObjectConfigurator.class).stream()
                                          .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                                          .map(this::instantiate)
                                          .peek(config -> config.setApplicationContext(applicationContext))
                                          .collect(Collectors.toList());
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {

        var instance = create(type);
        configure(instance);
        runInitMethods(instance);
        return instance;
    }

    @SneakyThrows
    private <T> void runInitMethods(T instance) {
        ReflectionUtils.getAllMethods(instance.getClass()).forEach(method -> {
            if(method.isAnnotationPresent(PostConstruct.class)){
                method.setAccessible(true);
                org.springframework.util.ReflectionUtils.invokeMethod(method, instance);
            }
        });
    }

    private static <T> T create(Class<T> type) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var instance = type.getDeclaredConstructor().newInstance();
        return instance;
    }

    private <T> void configure(T instance) {
        objectConfigurators.forEach(config -> config.configure(instance));
    }

    @SneakyThrows
    private <T> T instantiate(Class<T> clazz) {
        return clazz.getDeclaredConstructor().newInstance();
    }
}