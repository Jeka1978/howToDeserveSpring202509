package com.borisov.howtodeservespring.infra;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Only on field
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectByType {
}
