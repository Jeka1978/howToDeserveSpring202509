package com.borisov.howtodeservespring.infra.scope;

import org.springframework.context.annotation.Scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Scope(value = "fightScope")
public @interface FightScope {
    String value() default "fightScope";
}
