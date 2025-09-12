package com.borisov.howtodeservespring.infra;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Only on field
 */
@Retention(RetentionPolicy.RUNTIME)
@Autowired
public @interface InjectByType {
}
