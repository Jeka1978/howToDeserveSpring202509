package com.borisov.howtodeservespring.business;


import com.borisov.howtodeservespring.PaperSpider;
import com.borisov.howtodeservespring.Spider;
import com.borisov.howtodeservespring.infra.DefaultSpider;
import com.borisov.howtodeservespring.infra.MyPlayerQualifier;
import com.borisov.howtodeservespring.infra.PlayerQualifierBPP;
import lombok.experimental.Delegate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

//TODO сделайте так, чтобы тест заработал
// Внимательно прочитайте какие есть варианты Condition
public class DefaultSpiderTest {
    @DefaultSpider("1")
//    @PlayerQualifier(playerName = "1")
    public static class Spider1 {

    }

    //    @PlayerQualifier(playerName = "1")
    @MyPlayerQualifier(test = "1")
    public static class Spider2 {

    }

    @DefaultSpider("2")
//    @PlayerQualifier(playerName = "2")
    public static class Spider3 {

    }

    public static class SuperSpider implements Spider {
        @Delegate PaperSpider spider = new PaperSpider();
    }

    @TestConfiguration
    public static class Config {
        @Bean
        @DefaultSpider("22")
        public Spider superDuperSpider() {
            return new SuperSpider();
        }
    }

    @Test
    void should_not_create_default_spider_when_others_spiders_has_been_presented() {
        new ApplicationContextRunner()
                .withBean(Spider1.class)
                .withBean(Spider2.class)
                .withBean(Spider3.class)
                .run(context -> {
                    assertThat(context)
                            .doesNotHaveBean(Spider1.class)
                            .hasSingleBean(Spider2.class);
                });
    }

    @Test
    void should_create_default_spider_when_others_spiders_has_been_presented() {
        new ApplicationContextRunner()
                .withBean(Spider1.class)
                .withBean(Spider3.class)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(Spider1.class)
                            .hasSingleBean(Spider3.class);
                });
    }

    @Test
    void should_work_with_java_configuration() {
        new ApplicationContextRunner()
                .withUserConfiguration(Config.class)
                .withUserConfiguration(PlayerQualifierBPP.class)
                .withBean(Spider1.class)
                .run(context -> {
                    assertThat(context)
                            .hasSingleBean(Spider1.class)
                            .hasSingleBean(SuperSpider.class);
                });
    }
}
