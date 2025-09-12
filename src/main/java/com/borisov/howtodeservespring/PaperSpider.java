package com.borisov.howtodeservespring;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class PaperSpider extends AbstractSpider {




    @Override
    public RPSEnum fight(Spider opponent, int battleId) {
        return RPSEnum.PAPER;
    }
}
