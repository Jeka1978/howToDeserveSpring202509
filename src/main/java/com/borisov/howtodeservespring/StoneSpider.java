package com.borisov.howtodeservespring;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class StoneSpider extends AbstractSpider {

//    @PreDestroy
    public void killAll(){
        System.out.println("Killing all stones");
    }




    @Override
    public RPSEnum fight(Spider opponent, int battleId) {
        return RPSEnum.ROCK;
    }
}
