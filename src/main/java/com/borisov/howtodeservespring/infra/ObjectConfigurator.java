package com.borisov.howtodeservespring.infra;

import lombok.SneakyThrows;

public interface ObjectConfigurator extends Configurator {
    @SneakyThrows
    void configure(Object o);


}
