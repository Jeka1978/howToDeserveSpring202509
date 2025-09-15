package com.borisov.howtodeservespring.infra;

public interface ProxyConfigurator extends Configurator {

    <T> T replaceWithProxy(T o, Class originalClass, Object originalObject);
}
