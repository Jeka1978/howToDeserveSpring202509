package com.borisov.howtodeservespring;

import org.springframework.stereotype.Component;

@Component
public class StoneSpider extends AbstractSpider {




    @Override
    public RPSEnum fight(Spider opponent, int battleId) {
        return RPSEnum.ROCK;
    }
}
