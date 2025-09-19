package com.borisov.howtodeservespring.aop;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ConditionalOnProperty(value = "spider.immortal", havingValue = "true", matchIfMissing = true)
public class AspectConfig {

    @Bean
    public DynamicMethodMatcherPointcut pointcut() {
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
