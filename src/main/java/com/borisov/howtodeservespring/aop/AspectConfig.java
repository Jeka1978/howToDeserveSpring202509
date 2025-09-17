package com.borisov.howtodeservespring.aop;

import com.borisov.howtodeservespring.infra.PreDestroyBFPP;
import jakarta.annotation.PostConstruct;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {


    @PostConstruct
    public void postConstruct() {
        System.out.println(123);
    }


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
