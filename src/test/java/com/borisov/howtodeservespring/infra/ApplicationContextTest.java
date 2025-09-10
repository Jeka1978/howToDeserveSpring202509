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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationContextTest {
    @InjectMocks ApplicationContext applicationContext;
    @Spy         Reflections        reflections = new Reflections("com.borisov.howtodeservespring", new SubTypesScanner(),new ResourcesScanner());
    @Mock        ObjectFactory      objectFactory;


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

        ApContextTestSingletonObject    object  = applicationContext.getObject(ApContextTestSingletonObject.class);
        ApContextTestSingletonObject    object2 = applicationContext.getObject(ApContextTestSingletonObject.class);
        ApContextTestSingletonInterface object3 = applicationContext.getObject(ApContextTestSingletonInterface.class);

        assertThat(object)
                .as("All singleton objects are the same")
                .isSameAs(object2)
                .isSameAs(object3);
    }

    @Test
    void should_get_object_configured_by_injection_configurator() {
        when(objectFactory.createObject(ApContextTestObject.class)).thenAnswer(invocationOnMock -> new ApContextTestObject());

        ApContextTestObject object = (ApContextTestObject) applicationContext.getObject(ApContextTestInterface.class);

        verify(objectFactory, times(1)).createObject(ApContextTestObject.class);
//        assertThat(object.test)
//                .as("Should configured field of injected object")
//                .isNotNull();
    }
}