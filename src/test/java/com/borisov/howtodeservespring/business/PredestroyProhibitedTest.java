package com.borisov.howtodeservespring.business;

import com.borisov.howtodeservespring.infra.FailException;
import com.borisov.howtodeservespring.infra.PreDestroyBFPP;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PredestroyProhibitedTest {
    @TestConfiguration
    public static class Config {
        @Bean
        public PreDestroyBFPP preDestroyBfpp() {
            return new PreDestroyBFPP();
        }

        @Bean
        @Scope("prototype")
        public ProptypeBean proptypeBean() {
            return new ProptypeBean();
        }
    }

    public static class ProptypeBean {
        @PreDestroy
        public void destroy() {

        }
    }

    @Test
    void should_fail_context_when_predestroy_anotation_found() {
        new ApplicationContextRunner()
                .withUserConfiguration(Config.class)
                .run(ctx -> {
                    assertThat(ctx)
                            .as("Should fail when @PreDestroy found")
                            .hasFailed()
                            .getFailure()
                            .isInstanceOf(FailException.class);
                });
    }

}
