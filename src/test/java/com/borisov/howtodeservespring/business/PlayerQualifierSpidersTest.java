package com.borisov.howtodeservespring.business;

import com.borisov.howtodeservespring.*;
import com.borisov.howtodeservespring.aop.AspectConfig;
import com.borisov.howtodeservespring.aop.LogAspect;
import com.borisov.howtodeservespring.infra.Log;
import com.borisov.howtodeservespring.infra.PlayerQualifier;
import com.borisov.howtodeservespring.infra.PlayerQualifierBPP;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.lang.reflect.Proxy;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig({
        PlayerQualifierSpidersTest.Config.class,
        AspectConfig.class,
        LogAspect.class,
        PlayerQualifierBPP.class
})
public class PlayerQualifierSpidersTest {
    @Autowired ApplicationContext applicationContext;

    /*********************************
     *** Test configuration **********
     ********************************/
    @TestConfiguration
    public static class Config {
        @Bean
        public PaperSpider paperSpider() {
            return new PaperSpider();
        }

        @Bean
        @PlayerQualifier(playerName = "Override")
        public StoneSpider stoneSpider() {
            return new StoneSpider();
        }

        @Bean
        @PlayerQualifier(playerName = "SuperDuperOwner")
        public Spider superDuperSpider() {
            return new AbstractSpider() {
                @Getter
                String test = "testfield";

                @Log("test")
                @Override
                public RPSEnum fight(Spider opponent, int battleId) {
                    return RPSEnum.ROCK;
                }
            };
        }
    }

    /*************************************
     *** End test configuration **********
     ************************************/

    @Test
    void should_contains_proxy_spiders_bean_in_context() {
        Map<String, Spider> spiders = applicationContext.getBeansOfType(Spider.class);

        assertThat(
                spiders.values()
                       .stream()
                       .anyMatch(
                               spider -> Enhancer.isEnhanced(spider.getClass())
                                       || Proxy.isProxyClass(spider.getClass())
                       )
        ).isTrue();
    }

    @Test
    void should_set_owner_to_all_beans() {
        Map<String, Spider> spiders = applicationContext.getBeansOfType(Spider.class);

        //expect
        assertThat(spiders.values())
                .allMatch(s -> s.getOwner() != null && !s.getOwner().equals(AbstractSpider.DEFAULT_SPIDER_OWNER))
                .hasSize(3);
    }

    @Test
    void should_set_owner_from_qualifier() {
        Spider bean = applicationContext.getBean("superDuperSpider", Spider.class);

        assertThat(bean.getOwner()).isEqualTo("SuperDuperOwner");
    }

    //TODO почините код чтобы тест работал
    @Test
    void should_override_playerqualifier_on_bean_is_priority() {
        Spider stoneSpider = applicationContext.getBean("stoneSpider", Spider.class);

        assertThat(stoneSpider.getOwner()).isEqualTo("Override");

    }
}
