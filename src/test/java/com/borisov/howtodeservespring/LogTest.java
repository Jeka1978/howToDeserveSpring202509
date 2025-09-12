package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.ApplicationContext;
import com.borisov.howtodeservespring.infra.Log;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        MockitoExtension.class,
        OutputCaptureExtension.class
})
public class LogTest {
    private ApplicationContext context;

    /***************************************************
     *** START Test environment specification **********
     **************************************************/

    @NoArgsConstructor
    public static class MetaSpiderForTest implements MyClassWithLogAnnotationInterface {
        @PostConstruct
        public void init() {
            System.out.println("I'm meta spider with N lives");
        }

        @Setter
        private String fieldToLog = "Log me please";

        @Override
        public void tryLog() {
            System.out.println("Super logging method, try enable it");
        }
    }

    public interface MyClassWithLogAnnotationInterface {
        //TODO Попробуйте реализовать логирование, триггером к которому является аннотация над методом интерфейса
        // Напишите тесты на это
        // Подумайте разные варианты тестирования слоёв где реализована логика обрабатывающая эту аннотацию
        //Место для вашей аннотации. Например @Log("fieldToLog"), где fieldName имя поля которое нужно напечатать
        @Log("fieldToLog")
        default void tryLog() {
        }

        ;

        @Log
        default void tryLogWithoutField() {
        }

        default void tryNotLog() {
        }
    }

    @BeforeEach
    void beforeEach() {
        //TODO - Подумайте, какая проблема может быть при таком подходе?
        context = new ApplicationContext("com.borisov.howtodeservespring");
    }

    /*************************************************
     *** END Test Environment specification **********
     ************************************************/


    @Test
    void should_not_log_field_from_log_annotation(CapturedOutput output) {
        //TODO Подумайте о краевых случаях @Log
        //given
        MetaSpiderForTest object = context.getObject(MetaSpiderForTest.class);

        //when
        object.tryNotLog();

        //then
        assertThat(output.getAll()).contains("Log me pleas");
    }

    @Test
    void should_log_field_from_log_annotation(CapturedOutput output) {
        //TODO напишите логику теста проверящую работу @Log с минимальным количеством вовлечённых компонент проекта
        //given
        MetaSpiderForTest object = context.getObject(MetaSpiderForTest.class);
        object.setFieldToLog("Custom field value");

        //when
        object.tryLog();

        //then
        assertThat(output.getAll()).contains("Custom field value");
    }

    @Test
    void should_log_when_field_name_is_unset_in_annotation(CapturedOutput output) {
        //TODO напишите логику теста проверящую работу @Log с минимальным количеством вовлечённых компонент проекта
        //given
        MetaSpiderForTest object = context.getObject(MetaSpiderForTest.class);
        object.setFieldToLog("Custom field value");

        //when
        object.tryLogWithoutField();

        //then
        assertThat(output.getAll())
                .contains("tryLogWithoutField")
                .doesNotContain("Log me please");
    }

    @Test
    void should_log_when_spider_initialized(CapturedOutput capturedOutput) {
        //TODO подумайте какие проблемы могут возникать из указанного способа создания контекста
        // 1. Как это можно починить?
        // 2. А ещё как? (подумайте двух способах и их плюсах и минусах)
        // 3. Как проверить что метод вызвался ровно один раз?
        //when
        MetaSpiderForTest object = context.getObject(MetaSpiderForTest.class);

        //then
        assertThat(object).isNotNull();
        assertThat(capturedOutput.getAll()).contains("I'm meta spider with N live");
    }
}
