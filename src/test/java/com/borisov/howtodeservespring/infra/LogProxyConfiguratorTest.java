package com.borisov.howtodeservespring.infra;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith({
        MockitoExtension.class,
        OutputCaptureExtension.class
})
class LogProxyConfiguratorTest {
    @InjectMocks LogProxyConfigurator logProxyConfigurator;


    public static interface MyClassWithLogAnnotationInterface {
        void tryLog();

    }

    public static class MyFinalClassWithLogAnnotation {
        @Getter
        String myfield = "123124";

        @Log("myfield")
        public void tryLog() {

        }
    }

    public static final class MyClassWithLogAnnotation implements MyClassWithLogAnnotationInterface {
        @Getter
        String myfield = "123124";

        @Override
        @Log("myfield")
        public void tryLog() {

        }
    }


    @Test
    void should_return_javalangproxy() {
        //given
        var myClassWithLogAnnotation = new MyClassWithLogAnnotation();

        //when
        MyClassWithLogAnnotationInterface o = logProxyConfigurator.replaceWithProxy(
                myClassWithLogAnnotation,
                (Class<MyClassWithLogAnnotation>) myClassWithLogAnnotation.getClass(),
                myClassWithLogAnnotation
        );

        //then
        assertThat(Proxy.isProxyClass(o.getClass()))
                .as("По возможности делаем DynamicProxy для быстродействия")
                .isTrue();
    }


    @Test
    void should_log_to_out_myfield_because_log_annotation(CapturedOutput capturedOutput) {
        //given
        var myClassWithLogAnnotation = new MyClassWithLogAnnotation();

        MyClassWithLogAnnotationInterface o = logProxyConfigurator.replaceWithProxy(
                myClassWithLogAnnotation,
                (Class<MyClassWithLogAnnotation>) myClassWithLogAnnotation.getClass(),
                myClassWithLogAnnotation
        );

        //when
        o.tryLog();

        //then
        assertThat(capturedOutput.toString())
                .as("Should print field name with value because @Log('myfield')")
                .contains("myfield = \"123124\"");
    }

    @Test
    void should_log_to_out_myfield_on_final_class(CapturedOutput output) {
        //given
        var target = new MyFinalClassWithLogAnnotation();

        //when
        MyFinalClassWithLogAnnotation o = logProxyConfigurator.replaceWithProxy(
                target,
                (Class<MyFinalClassWithLogAnnotation>) target.getClass(),
                target
        );
        o.tryLog();

        //then
        assertAll(
                () ->
                        assertThat(output.toString())
                                .as("Should print field name with value because @Log('myfield')")
                                .contains("myfield = \"123124\"")
                ,
                () -> assertThat(Enhancer.isEnhanced(o.getClass()))
                        .as("Should enhanced via Enhancer")
                        .isTrue()
        );

    }
}