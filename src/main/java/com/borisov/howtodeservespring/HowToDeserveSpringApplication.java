package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.ApplicationContext;
import com.borisov.howtodeservespring.infra.ObjectFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class HowToDeserveSpringApplication {

    public static void main(String[] args) {

        GameMaster gameMaster1 = ObjectFactory.getInstance().createObject(GameMaster.class);
        GameMaster gameMaster2 = ObjectFactory.getInstance().createObject(GameMaster.class);
        System.out.println(gameMaster1==gameMaster2);

        ApplicationContext applicationContext = new ApplicationContext("com.borisov.howtodeservespring");
//        applicationContext.getObject(GameMaster.class).fight();


    }

}
