package com.borisov.howtodeservespring.infra.scope;

import com.borisov.howtodeservespring.Spider;
import org.springframework.context.ApplicationEvent;

import java.util.List;

import lombok.Getter;

@Getter
public class FightFinishedEvent extends ApplicationEvent {
    private final List<Spider> winners;

    public FightFinishedEvent(List<Spider> winners) {
        super(winners);
        this.winners = winners;
    }
}
