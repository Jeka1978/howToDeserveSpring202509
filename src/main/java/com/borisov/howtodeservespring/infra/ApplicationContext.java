package com.borisov.howtodeservespring.infra;

import com.borisov.howtodeservespring.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class ApplicationContext {
    @Getter
    private final Reflections scanner;
    private final ObjectFactory factory;
    private final Map<Class<?>, Object> singletonCache = new ConcurrentHashMap<>();

    public ApplicationContext(String... basePackages) {
        this.scanner = new Reflections((Object[]) basePackages);
        this.factory = new ObjectFactory(this);

        Set<Class<?>> singletons = scanner.getTypesAnnotatedWith(Singleton.class);
        for (Class<?> singleton : singletons) {
            getObject(singleton);
        }
    }


    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> type) {
        Class<?> concreteType = resolveConcreteType(type);
        boolean isSingleton = concreteType.isAnnotationPresent(Singleton.class);

        if (isSingleton) {
            return (T) singletonCache.computeIfAbsent(type, key -> factory.createObject((Class<T>) concreteType));
        } else {
            return factory.createObject((Class<T>) concreteType);
        }
    }

    @SneakyThrows
    private Class<?> resolveConcreteType(Class<?> type) {
        if (!type.isInterface()) return type;

        Set<?> impls = scanner.getSubTypesOf((Class<Object>) type);
        if (impls.size() != 1) {
            throw new IllegalStateException("Expected exactly one implementation of " + type + ", found: " + impls.size());
        }
        return (Class<?>) impls.iterator().next();
    }
}