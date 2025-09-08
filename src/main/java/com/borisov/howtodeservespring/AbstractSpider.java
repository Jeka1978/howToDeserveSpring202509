package com.borisov.howtodeservespring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AbstractSpider implements Spider {

    @InjectProperty("spring.default.life")
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
