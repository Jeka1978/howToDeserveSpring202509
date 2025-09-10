package com.borisov.howtodeservespring.infra;

import lombok.SneakyThrows;
import org.reflections.Reflections;

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
        if (type.isInterface()) {
            var implementations = scanner.getSubTypesOf(type);
            if (implementations.size() != 1) {
                throw new IllegalStateException("Expected single implementation of " + type + ", found: " + implementations.size());
            }
            type = (Class<T>) implementations.iterator().next();
        }

        var instance = type.getDeclaredConstructor().newInstance();
        objectConfigurators.forEach(config -> config.configure(instance));
        return instance;
    }

    @SneakyThrows
    private <T> T instantiate(Class<T> clazz) {
        return clazz.getDeclaredConstructor().newInstance();
    }
}