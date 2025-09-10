package com.borisov.howtodeservespring.infra;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InjectByTypeObjectTest {
    @InjectMocks InjectByTypeObjectConfigurator injectByTypeObjectConfigurator;

    // Context case
    public static class InjectTestClass {
        @InjectByType
        InjectedTestClass test;
    }

    public static class InjectBySetterTestClass {
        private InjectedTestClass test;

        @InjectByType
        public void setTest(InjectedTestClass test) {
            this.test = test;
        }
    }

    public static class InjectedTestClass {
    }
    // Context end

    @Test
    void should_inject_class_by_type() {
        //given
        InjectTestClass injectTestClass = new InjectTestClass();

        //when
        injectByTypeObjectConfigurator.configure(injectTestClass);

        //then
        assertThat(injectTestClass.test)
                .as("Test field should be injected")
                .isNotNull();
    }

    @Test
    @Disabled("Advanced homework")
    void should_inject_class_by_type_via_setter() {
        //given
        InjectBySetterTestClass injectTestClass = new InjectBySetterTestClass();

        //when
        injectByTypeObjectConfigurator.configure(injectTestClass);

        //then
        assertThat(injectTestClass.test)
                .as("Test field should be injected")
                .isNotNull();
    }
}