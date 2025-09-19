package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.PlayerQualifier;
import org.springframework.stereotype.Component;

@Component
@PlayerQualifier(playerName = "Kirill")
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
