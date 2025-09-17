package com.borisov.howtodeservespring;

import com.borisov.howtodeservespring.infra.PlayerQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SpiderConfig {

    @Autowired
    private List<Spider> allSpiders;


    @Bean
    public Map<String,Integer> playersTrophyMap(){
        //TODO соберите по метаинформации из @PlayerQualifier пауков в мапу трофеев
        return null;
    }
}
