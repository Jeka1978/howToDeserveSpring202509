package com.borisov.howtodeservespring;

public class StoneSpider extends AbstractSpider {




    @Override
    public RPSEnum fight(Spider opponent, int battleId) {
        return RPSEnum.ROCK;
    }
}
