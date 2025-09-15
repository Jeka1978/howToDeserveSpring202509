package com.borisov.howtodeservespring.aop;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

    @Bean
    public RessurectionSpidersDetectorPointcut pointcut() {
        return new RessurectionSpidersDetectorPointcut();
    }

    @Bean
    public RessurectionSpidersAdvise advise() {
        return new RessurectionSpidersAdvise();
    }

    @Bean
    public DefaultPointcutAdvisor advisor() {

        return new DefaultPointcutAdvisor(pointcut(), advise());
    }
}
