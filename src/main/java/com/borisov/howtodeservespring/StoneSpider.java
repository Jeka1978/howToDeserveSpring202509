package com.borisov.howtodeservespring;

import org.springframework.stereotype.Component;

@Component
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
