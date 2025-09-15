package com.borisov.howtodeservespring.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {

    @Bean
    public LogProxyConfigurator logProxyConfigurator() {
      return new LogProxyConfigurator();
    }

    @Bean
    public static InjectPropertyObjectConfigurator injectPropertyObjectConfigurator() {
      return new InjectPropertyObjectConfigurator();
    }

}
