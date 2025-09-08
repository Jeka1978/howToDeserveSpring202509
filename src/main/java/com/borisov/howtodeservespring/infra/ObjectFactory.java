package com.borisov.howtodeservespring.infra;


import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactory {


    private List<ObjectConfigurator> objectConfigurators = new ArrayList<>();


    private Reflections scanner = new Reflections("com.borisov.howtodeservespring");

    private static final ObjectFactory INSTANCE = new ObjectFactory();

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public ObjectFactory() {
        Set<Class<? extends ObjectConfigurator>> configuratorClasses = scanner.getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> configuratorClass : configuratorClasses) {
            if (configuratorClass.getModifiers() != Modifier.ABSTRACT) {
                objectConfigurators.add(configuratorClass.getDeclaredConstructor().newInstance());
            }
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {


        if (type.isInterface()) {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(type);
            if (classes.isEmpty() || classes.size() > 1) {
                throw new IllegalStateException("Zero ore More than one type of " + type);
            }
            type = (Class<T>) classes.iterator().next();
        }

        // check interface or class (if interface search root package for impl, if 0 or more than 1 found ->exception
        //create Object from impl
        // configure this object with our configurators
        //return configured object

        T t = type.getDeclaredConstructor().newInstance();


        objectConfigurators.forEach(configurator -> configurator.configure(t));


        return t;
    }
}
