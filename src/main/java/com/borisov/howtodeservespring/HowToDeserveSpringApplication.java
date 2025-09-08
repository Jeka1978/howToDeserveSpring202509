package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.ApplicationContext;
import com.borisov.howtodeservespring.infra.ObjectFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class HowToDeserveSpringApplication {

    public static void main(String[] args) {

//        ObjectFactory.getInstance().createObject(GameMaster.class).fight();
        ApplicationContext applicationContext = new ApplicationContext("com.borisov.howtodeservespring");
        applicationContext.getObject(GameMaster.class).fight();


    }

}
