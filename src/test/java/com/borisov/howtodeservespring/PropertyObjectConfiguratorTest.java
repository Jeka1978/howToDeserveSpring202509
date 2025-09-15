package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.InjectProperty;
import com.borisov.howtodeservespring.infra.InjectPropertyObjectConfigurator;
import com.borisov.howtodeservespring.infra.MainConfiguration;
import com.borisov.howtodeservespring.infra.ObjectConfigurator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyObjectConfiguratorTest {
    public static class MySpider {
        @InjectProperty("not.found") int notFound;
    }

    @TestConfiguration
    public static class InvalidConfig {
        @Bean
        public MySpider mySpider() {
            return new MySpider();
        }

    }

    @TestConfiguration
    public static class Config {
        @Bean
        public PaperSpider paperSpider() {
            return new PaperSpider();
        }

    }

    @Test
    void should_not_thrown_when_no_props() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(MainConfiguration.class))
                .withUserConfiguration(InvalidConfig.class)
                .run(context -> assertAll(
                        "Содержит бин с аннотацией InjectProperty и дефолтным значением - 0 для не найденного property",
                        () -> assertThat(context)
                                .hasSingleBean(MySpider.class)
                                .doesNotHaveBean(PaperSpider.class),
                        () -> {
                            var spider = context.getBean(MySpider.class);
                            assertThat(spider)
                                    .hasFieldOrProperty("notFound")
                                    .hasFieldOrPropertyWithValue("notFound", 0);
                        }
                ));
    }

    @Test
    void inject_property_should_work() {

        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(MainConfiguration.class))
                .withUserConfiguration(Config.class)
                .withPropertyValues("spring.default.life=5")
                .run(context ->
                             assertAll(
                                     "Содержит бин с аннотацией InjectProperty и значением из environment",
                                     () -> assertThat(context)
                                             .hasSingleBean(PaperSpider.class)
                                             .doesNotHaveBean(MySpider.class),
                                     () -> {
                                         var spider = context.getBean(PaperSpider.class);
                                         assertThat(spider)
                                                 .hasFieldOrProperty("lives")
                                                 .hasFieldOrPropertyWithValue("lives", 5);
                                     }
                             ));
        // Внедряем свойства

        // Проверяем, что значение lives установилось из application.properties
    }
}