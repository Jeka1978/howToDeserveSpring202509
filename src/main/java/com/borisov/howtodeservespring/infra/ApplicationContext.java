package com.borisov.howtodeservespring.infra;


import com.borisov.howtodeservespring.GameMaster;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApplicationContext {

    private final String packageToScan;


    public <T> T getObject(Class<T> gameMasterClass) {
        return null;
    }
}
