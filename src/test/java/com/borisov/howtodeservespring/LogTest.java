package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.aop.AspectConfig;
import com.borisov.howtodeservespring.aop.LogAspect;
import com.borisov.howtodeservespring.infra.Log;
import com.borisov.howtodeservespring.infra.LogProxyConfigurator;
import com.borisov.howtodeservespring.infra.MainConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({
        OutputCaptureExtension.class
})
@SpringJUnitConfig({
        MainConfiguration.class,
        LogTest.class,
        AspectConfig.class,
        LogAspect.class
})
@TestConfiguration
public class LogTest {
    @Autowired org.springframework.context.ApplicationContext context;

    /***************************************************
     *** START Test environment specification **********
     **************************************************/

    @NoArgsConstructor
    @TestComponent
    public static class MetaSpiderForTest implements MyClassWithLogAnnotationInterface {
        @PostConstruct
        public void init() {
            System.out.println("I'm meta spider with N lives");
        }

        @Setter
        private String fieldToLog = "Log me please";

        @Override
        @Log("fieldToLog")
        public void tryLog() {
            System.out.println("Super logging method, try enable it");
        }
    }

    public interface MyClassWithLogAnnotationInterface {
        //TODO Попробуйте реализовать логирование, триггером к которому является аннотация над методом интерфейса
        // Напишите тесты на это
        // Подумайте разные варианты тестирования слоёв где реализована логика обрабатывающая эту аннотацию
        //Место для вашей аннотации. Например @Log("fieldToLog"), где fieldName имя поля которое нужно напечатать

        default void tryLog() {
        }

        default void tryNotLog() {
        }
    }

    @Test
    void should_contain_configurator_in_context() {
        assertThrows(
                NoSuchBeanDefinitionException.class, () -> context.getBean(LogProxyConfigurator.class)
        );
    }

    /*************************************************
     *** END Test Environment specification **********
     ************************************************/


    @Test
    void should_not_log_field_from_log_annotation(CapturedOutput output) {
        //TODO Подумайте о краевых случаях @Log
        //given
        MyClassWithLogAnnotationInterface object = context.getBean(MyClassWithLogAnnotationInterface.class);

        //when
        object.tryNotLog();

        //then
        assertThat(output.getAll()).doesNotContain("Log me pleas");
    }

    @Test
    void should_log_field_from_log_annotation(CapturedOutput output) {
        //TODO напишите логику теста проверящую работу @Log с минимальным количеством вовлечённых компонент проекта
        //given
        MyClassWithLogAnnotationInterface object = context.getBean(MyClassWithLogAnnotationInterface.class);
//        object.setFieldToLog("Custom field value");

        //when
        object.tryLog();

        //then
        assertThat(output.getAll()).contains("Log me please");
    }


    @Test
    void should_log_when_spider_initialized(CapturedOutput capturedOutput) {
        //TODO подумайте какие проблемы могут возникать из указанного способа создания контекста
        // 1. Как это можно починить?
        // 2. А ещё как? (подумайте двух способах и их плюсах и минусах)
        // 3. Как проверить что метод вызвался ровно один раз?
        //when
        MyClassWithLogAnnotationInterface object = context.getBean(MyClassWithLogAnnotationInterface.class);

        //then
        assertThat(object).isNotNull();
        assertThat(capturedOutput.getAll()).contains("I'm meta spider with N live");
    }
}
