package com.borisov.howtodeservespring.infra;


import com.borisov.howtodeservespring.GameMaster;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;

@RequiredArgsConstructor
public class ApplicationContext {
    private final Reflections scanner;
    private final ObjectFactory objectFactory;

    public ApplicationContext(Reflections scanner) {
        this.scanner = scanner;
        objectFactory = new ObjectFactory(this);

    }

    public <T> T getObject(Class<T> gameMasterClass) {
        return null;
    }

    public Reflections getScanner() {
        return scanner;
    }
}
