package com.borisov.howtodeservespring.infra;

import com.borisov.howtodeservespring.Singleton;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(ApplicationContextTest.class)
@TestConfiguration
class ApplicationContextTest {
    @Bean
    public String string() {
      return new String("abcdf");
    }
    @Autowired ApplicationContext applicationContext;

    /***************************************************
     *** START Test environment configuration **********
     **************************************************/
    public interface ApContextTestInterface {

    }

    @TestComponent
    @Scope("prototype")
    public class ApContextTestObject implements ApContextTestInterface {
        @InjectByType
        InjectedTestClass test;
    }

    @Singleton
    public class ApContextTestSingletonObject implements ApContextTestSingletonInterface {
    }

    public interface ApContextTestSingletonInterface {

    }

    @TestComponent
    public class InjectedTestClass {
    }

    /*************************************************
     *** END Test environment configuration **********
     ************************************************/

    @Test
    void should_get_object_by_interface_singleton() {

        ApContextTestInterface object  = applicationContext.getBean(ApContextTestInterface.class);
        ApContextTestInterface object2 = applicationContext.getBean(ApContextTestObject.class);

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

        ApContextTestInterface object = applicationContext.getBean(ApContextTestInterface.class);

        Assertions.assertThat(object)
                  .isNotNull();
    }


    @Test
    void should_get_singleton_object() {

        //expect
        assertThat(applicationContext.getBean(ApContextTestSingletonObject.class))
                .as("All singleton objects are the same with object pulled by interface")
                .isSameAs(applicationContext.getBean(ApContextTestSingletonObject.class));
    }

    //TODO домашнее задание - реализуйте ApplicationContext таким образом, чтобы возвращался один и тот же конкретный объект
    @Test
    @Disabled("Advanced homework")
    void should_get_singleton_object_via_interface_and_class_are_the_same() {

        //expect
        ApContextTestSingletonObject    object  = applicationContext.getBean(ApContextTestSingletonObject.class);
        ApContextTestSingletonInterface object2 = applicationContext.getBean(ApContextTestSingletonInterface.class);

        assertThat(object)
                .as("All singleton objects are the same with object pulled by interface")
                .isSameAs(object2);
    }

    @Test
    void should_get_new_object_everytime_when_no_singleton() {

        ApContextTestInterface object  = applicationContext.getBean(ApContextTestInterface.class);
        ApContextTestInterface object2 = applicationContext.getBean(ApContextTestInterface.class);

        assertThat(object)
                .as("Prototype object should be new each time")
                .isNotSameAs(object2);
    }

}