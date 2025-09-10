package com.borisov.howtodeservespring.infra;

import com.borisov.howtodeservespring.Singleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationContextTest {

    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new ApplicationContext("com.borisov.howtodeservespring.infra");
    }

    public interface ApContextTestInterface {
    }

    public static class ApContextTestObject implements ApContextTestInterface {
        @InjectByType
        InjectedTestClass test;
    }

    @Singleton
    public static class ApContextTestSingletonObject implements ApContextTestSingletonInterface {
    }

    public interface ApContextTestSingletonInterface {
    }

    public static class InjectedTestClass {
    }

    @Test
    void should_get_object_by_interface() {
        ApContextTestInterface object = applicationContext.getObject(ApContextTestInterface.class);

        assertThat(object)
                .isNotNull()
                .isInstanceOf(ApContextTestObject.class);
    }

    @Test
    void should_get_different_instances_for_non_singletons() {
        ApContextTestInterface object1 = applicationContext.getObject(ApContextTestInterface.class);
        ApContextTestInterface object2 = applicationContext.getObject(ApContextTestInterface.class);

        assertThat(object1)
                .isNotSameAs(object2);
    }

    @Test
    void should_get_same_instance_for_singletons() {
        ApContextTestSingletonObject object1 = applicationContext.getObject(ApContextTestSingletonObject.class);
        ApContextTestSingletonObject object2 = applicationContext.getObject(ApContextTestSingletonObject.class);
        ApContextTestSingletonInterface object3 = applicationContext.getObject(ApContextTestSingletonInterface.class);

        assertThat(object1)
                .isSameAs(object2)
                .isSameAs(object3);
    }

    @Test
    void should_configure_injected_fields() {
        ApContextTestObject object = (ApContextTestObject) applicationContext.getObject(ApContextTestInterface.class);

        assertThat(object.test)
                .as("Should be configured by ObjectConfigurator")
                .isNotNull();
    }
}