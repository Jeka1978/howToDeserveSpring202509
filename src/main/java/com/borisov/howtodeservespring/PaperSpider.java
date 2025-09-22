package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.PlayerQualifier;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@PlayerQualifier(playerName = "Kirill")
public class PaperSpider extends AbstractSpider {




    @Override
    public RPSEnum fight(Spider opponent, int battleId) {
        return RPSEnum.PAPER;
    }

}
