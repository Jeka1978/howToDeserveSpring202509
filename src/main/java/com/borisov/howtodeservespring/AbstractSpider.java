package com.borisov.howtodeservespring;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractSpider implements Spider {

    @InjectProperty
    private int lives;

    @Override
    public boolean isAlive() {
        return lives > 0;
    }

    @Override
    public void loseLife() {
        if (lives > 0) lives--;
    }
}
