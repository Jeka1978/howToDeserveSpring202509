package com.borisov.howtodeservespring.infra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.reflections.Reflections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationContextTest {
    @InjectMocks ApplicationContext applicationContext;
    @Mock        Reflections        reflections;
    @Mock        ObjectFactory      objectFactory;

    public interface ApContextTestInterface {

    }

    public class ApContextTestObject implements ApContextTestInterface {

    }

    @Test
    void should_get_object_by_interface_singleton() {
        ApContextTestInterface object  = applicationContext.getObject(ApContextTestInterface.class);
        ApContextTestInterface object2 = applicationContext.getObject(ApContextTestInterface.class);

        Assertions.assertThat(object)
                  .as("Should return equal ref object, because singleton")
                  .isSameAs(object2);
    }

    @Test
    void should_get_object_by_interface() {
        ApContextTestInterface object = applicationContext.getObject(ApContextTestInterface.class);

        Assertions.assertThat(object)
                  .isNotNull();
    }
}