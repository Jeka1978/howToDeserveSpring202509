package com.borisov.howtodeservespring.infra;

import lombok.SneakyThrows;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class DefaultSpiderCondition implements ConfigurationCondition {
    @Override
    @SneakyThrows
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      return false;
    }


    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return ConfigurationPhase.REGISTER_BEAN;
    }
}

