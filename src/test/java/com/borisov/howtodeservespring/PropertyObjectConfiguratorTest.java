package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.InjectPropertyObjectConfigurator;
import com.borisov.howtodeservespring.infra.ObjectConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PropertyObjectConfiguratorTest {
    @InjectMocks InjectPropertyObjectConfigurator injector;

    @Test
    void inject_property_should_work() {
        Spider spider = new PaperSpider();

        // Внедряем свойства
        injector.configure(spider);

        // Проверяем, что значение lives установилось из application.properties
        assertEquals(5, spider.getLives(), "Expected 5 lives to be injected from properties");
    }
}