package com.borisov.howtodeservespring.infra;

import lombok.SneakyThrows;

public interface ObjectConfigurator {
    @SneakyThrows
    void configure(Object o);
}
