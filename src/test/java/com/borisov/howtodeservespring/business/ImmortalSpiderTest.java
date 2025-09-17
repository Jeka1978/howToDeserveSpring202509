package com.borisov.howtodeservespring.business;

import com.borisov.howtodeservespring.PaperSpider;
import com.borisov.howtodeservespring.Spider;
import com.borisov.howtodeservespring.StoneSpider;
import com.borisov.howtodeservespring.aop.AspectConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmortalSpiderTest {
    @Test
    void should_revive_spider_when_lives_is_1() {
        new ApplicationContextRunner()
                .withUserConfiguration(AspectConfig.class)
                .withPropertyValues("spider.special.names=tone")
                .withBean("paper", PaperSpider.class)
                .withBean("stone", StoneSpider.class)

                .run(ctx -> {
                    //given
                    Spider paper = (Spider) ctx.getBean("paper");
                    Spider stone = (Spider) ctx.getBean("stone");

                    paper.setLives(5);
                    stone.setLives(5);

                    //when
                    stone.fight(paper, 1);
                    stone.loseLife();
                    stone.fight(paper, 1);
                    stone.loseLife();
                    stone.fight(paper, 1);
                    stone.loseLife();
                    stone.fight(paper, 1);
                    stone.loseLife();
                    stone.fight(paper, 1);
                    stone.loseLife();

                    //then
                    assertThat(stone.getLives())
                            .as("Should revive spider when lives is 1")
                            .isEqualTo(99);

                });
    }
}
