package com.borisov.howtodeservespring;

import lombok.SneakyThrows;

public interface ObjectConfigurator {
    @SneakyThrows
    void configure(Object o);
}
