package com.borisov.howtodeservespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HowToDeserveSpringApplication {



    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(HowToDeserveSpringApplication.class, args);
        context.getBean(GameMaster.class).fight();

        context.close();
//        GameMaster gameMaster1 = ObjectFactory.getInstance().createObject(GameMaster.class);
//        GameMaster gameMaster2 = ObjectFactory.getInstance().createObject(GameMaster.class);

//        ApplicationContext applicationContext = new ApplicationContext("com.borisov.howtodeservespring");
//        applicationContext.getObject(GameMaster.class).fight();


    }

}
