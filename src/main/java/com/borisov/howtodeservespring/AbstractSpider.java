package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.InjectProperty;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class AbstractSpider implements Spider {

    @PostConstruct
    public void init() {
        System.out.println("spider type: " +getClass().getSimpleName()+" lives = "+lives);
    }

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
