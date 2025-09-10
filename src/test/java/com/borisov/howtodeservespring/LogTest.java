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
    public static class MetaSpiderForTest {
        @PostConstruct
        public void init() {
            System.out.println("I'm meta spider with N lives");
        }
    }

    public static interface MyClassWithLogAnnotationInterface {
        void tryLog();
    }

    @Test
    void should_log_when_spider_initialized(CapturedOutput capturedOutput) {
        ApplicationContext applicationContext = new ApplicationContext("com.borisov.howtodeservespring");

        MetaSpiderForTest object = applicationContext.getObject(MetaSpiderForTest.class);
        assertThat(object).isNotNull();
        assertThat(capturedOutput.getAll()).contains("I'm meta spider with N live");
    }
}
