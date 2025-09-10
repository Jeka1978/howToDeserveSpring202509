package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.ApplicationContext;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
        MockitoExtension.class,
        OutputCaptureExtension.class
})
public class LogTest {
    @NoArgsConstructor
    public static class MetaSpiderForTest implements MyClassWithLogAnnotationInterface {
        @PostConstruct
        public void init() {
            System.out.println("I'm meta spider with N lives");
        }

        @Override
        public void tryLog() {
            System.out.println("Super logging method, try enable it");
        }
    }

    public static interface MyClassWithLogAnnotationInterface {
        //TODO Попробуйте реализовать логирование, триггером к которому является аннотация над методом интерфейса
        //Место для вашей аннотации
        void tryLog();
    }

    @Test
    void should_log_when_spider_initialized(CapturedOutput capturedOutput) {
        //TODO подумайте какие проблемы могут возникать из указанного способа создания контекста
        // 1. Как это можно починить?
        // 2. А ещё как? (подумайте двух способах и их плюсах и минусах)
        //given
        ApplicationContext applicationContext = new ApplicationContext("com.borisov.howtodeservespring");

        //when
        MetaSpiderForTest object = applicationContext.getObject(MetaSpiderForTest.class);

        //then
        assertThat(object).isNotNull();
        assertThat(capturedOutput.getAll()).contains("I'm meta spider with N live");
    }
}
