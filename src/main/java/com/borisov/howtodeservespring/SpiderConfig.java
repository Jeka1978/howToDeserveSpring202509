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
    public List<Spider> spiders() {
        return allSpiders.stream()
                         .filter(spider -> !spider.getOwner().equals(AbstractSpider.DEFAULT_SPIDER_OWNER))
                         .collect(Collectors.toList());
    }


    @Bean
    public Map<String, Integer> playerTrophiesMap() {
        //TODO соберите по метаинформации из @PlayerQualifier пауков в мапу трофеев
        return spiders().stream()
                        .collect(Collectors.toMap(
                                Spider::getOwner,
                                _ -> 0,
                                (existing, _) -> existing
                        ));
    }
}
