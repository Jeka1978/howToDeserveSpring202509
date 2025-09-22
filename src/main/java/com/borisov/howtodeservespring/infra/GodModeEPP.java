package com.borisov.howtodeservespring.infra;

import com.borisov.howtodeservespring.Spider;
import org.reflections.Reflections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GodModeEPP implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Package     aPackage    = application.getMainApplicationClass().getPackage();
        Reflections reflections = new Reflections(aPackage.getName());

        Set<Class<? extends Spider>> subTypesOf = reflections.getSubTypesOf(Spider.class);

        if (subTypesOf.size() >= 3) {
            // Создаем новую Map для добавления свойства GodMode=true
            Map<String, Object> godModeProperty = new HashMap<>();
            godModeProperty.put("god.mode", true);

            // Добавляем это свойство в Environment
            PropertySource<Map<String, Object>> godModeSource = new MapPropertySource("godModeSource", godModeProperty);
            environment.getPropertySources().addLast(godModeSource);
        }

    }
}
