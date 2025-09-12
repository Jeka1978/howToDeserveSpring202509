package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.ApplicationContext;
import com.borisov.howtodeservespring.infra.InjectByType;
import com.borisov.howtodeservespring.infra.ObjectFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class HowToDeserveSpringApplication {




    public static void main(String[] args) {


        ConfigurableApplicationContext context = SpringApplication.run(HowToDeserveSpringApplication.class, args);
        context.getBean(GameMaster.class).fight();

//        GameMaster gameMaster1 = ObjectFactory.getInstance().createObject(GameMaster.class);
//        GameMaster gameMaster2 = ObjectFactory.getInstance().createObject(GameMaster.class);

//        ApplicationContext applicationContext = new ApplicationContext("com.borisov.howtodeservespring");
//        applicationContext.getObject(GameMaster.class).fight();


    }

}
