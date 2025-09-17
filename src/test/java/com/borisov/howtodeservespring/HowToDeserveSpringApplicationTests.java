package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.aop.AspectConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class HowToDeserveSpringApplicationTests {
    @Autowired ApplicationContext applicationContext;

    @Test
    void contextLoads() {
    }

    @Test
    void should_init_aspect_config() {

        applicationContext.getBean(AspectConfig.class);
    }
}
