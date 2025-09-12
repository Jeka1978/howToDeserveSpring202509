package com.borisov.howtodeservespring.infra;

import com.borisov.howtodeservespring.Singleton;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.boot.autoconfigure.session.JdbcSessionDataSourceScriptDatabaseInitializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationContextTest {
    @InjectMocks ApplicationContext applicationContext;
    @Spy         Reflections        reflections = new Reflections("com.borisov.howtodeservespring");
    @Mock        ObjectFactory      objectFactory;


    /***************************************************
     *** START Test environment configuration **********
     **************************************************/
    public interface ApContextTestInterface {

    }

    public class ApContextTestObject implements ApContextTestInterface {
        @InjectByType
        InjectedTestClass test;
    }

    @Singleton
    public class ApContextTestSingletonObject implements ApContextTestSingletonInterface {
    }

    public interface ApContextTestSingletonInterface {

    }

    public class InjectedTestClass {
    }

    /*************************************************
     *** END Test environment configuration **********
     ************************************************/

    @Test
    void should_get_object_by_interface_singleton() {
        when(objectFactory.createObject(ApContextTestObject.class)).thenAnswer(invocationOnMock -> new ApContextTestObject());

        ApContextTestInterface object  = applicationContext.getObject(ApContextTestInterface.class);
        ApContextTestInterface object2 = applicationContext.getObject(ApContextTestObject.class);

        Assertions.assertThat(object)
                  .as("Should get object superclass with interface")
                  .isInstanceOf(ApContextTestInterface.class)
                  .isInstanceOf(ApContextTestObject.class);

        Assertions.assertThat(object2)
                  .as("Should get object superclass with interface")
                  .isInstanceOf(ApContextTestInterface.class)
                  .isInstanceOf(ApContextTestObject.class);
    }

    @Test
    void should_get_object_by_interface() {
        when(objectFactory.createObject(ApContextTestObject.class)).thenAnswer(invocationOnMock -> new ApContextTestObject());

        ApContextTestInterface object = applicationContext.getObject(ApContextTestInterface.class);

        Assertions.assertThat(object)
                  .isNotNull();
    }


    @Test
    void should_get_singleton_object() {
        when(objectFactory.createObject(ApContextTestSingletonObject.class)).thenAnswer(invocationOnMock -> new ApContextTestSingletonObject());

        //expect
        assertThat(applicationContext.getObject(ApContextTestSingletonObject.class))
                .as("All singleton objects are the same with object pulled by interface")
                .isSameAs(applicationContext.getObject(ApContextTestSingletonObject.class));
    }

    //TODO домашнее задание - реализуйте ApplicationContext таким образом, чтобы возвращался один и тот же конкретный объект
    @Test
    @Disabled("Advanced homework")
    void should_get_singleton_object_via_interface_and_class_are_the_same() {
        when(objectFactory.createObject(ApContextTestSingletonObject.class)).thenAnswer(invocationOnMock -> new ApContextTestSingletonObject());

        //expect
        ApContextTestSingletonObject    object  = applicationContext.getObject(ApContextTestSingletonObject.class);
        ApContextTestSingletonInterface object2 = applicationContext.getObject(ApContextTestSingletonInterface.class);

        assertThat(object)
                .as("All singleton objects are the same with object pulled by interface")
                .isSameAs(object2);
    }

    @Test
    void should_get_new_object_everytime_when_no_singleton() {
        when(objectFactory.createObject(ApContextTestObject.class)).thenAnswer(invocationOnMock -> new ApContextTestObject());

        ApContextTestInterface object  = applicationContext.getObject(ApContextTestInterface.class);
        ApContextTestInterface object2 = applicationContext.getObject(ApContextTestInterface.class);

        assertThat(object)
                .as("Prototype object should be new each time")
                .isNotSameAs(object2);
    }

    @Test
    void should_get_object_configured_by_injection_configurator() {
        when(objectFactory.createObject(ApContextTestObject.class)).thenAnswer(invocationOnMock -> new ApContextTestObject());

        applicationContext.getObject(ApContextTestInterface.class);

        //TODO достаточно ли проверить что метод create вызвался один раз?
        // - Подумайте, какие ещё тесты стоит написать чтобы "система" тестов для этой логики была полна
        verify(objectFactory, times(1)).createObject(ApContextTestObject.class);
    }
}