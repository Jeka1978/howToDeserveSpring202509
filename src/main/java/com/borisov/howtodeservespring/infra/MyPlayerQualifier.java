package com.borisov.howtodeservespring.infra;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PlayerQualifier
public @interface MyPlayerQualifier {
    @AliasFor(annotation = PlayerQualifier.class, attribute = "playerName")
    String test() default "";
}
