package com.borisov.howtodeservespring;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectFactory {

    private static final ObjectFactory INSTANCE = new ObjectFactory();

    public static ObjectFactory getInstance() {
        return INSTANCE;
    }

    public <T> T createObject(Class<T> type) {
        // check interface or class (if interface search root package for impl, if 0 or more than 1 found ->exception
        //create Object from impl
        // configure this object with our configurators
        //return configured object
        //todo

        return null;
    }
}
