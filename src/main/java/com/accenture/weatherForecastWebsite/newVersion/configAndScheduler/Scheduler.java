package com.accenture.weatherForecastWebsite.newVersion.configAndScheduler;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class Scheduler {
/*
    //cron expression telling how often cache update needs to execute
    @Scheduled(cron = "${update.cache.cron.start}")
    @CachePut(value ="city")
    public void updateCache () //doesn't accept any parameters always returns void type
    {
        System.out.println("Cache updated...");
    }

    //cron expression telling when cache needs to be cleared
    @Scheduled(cron = "${delete.data.cache.cron.start}")
    @CacheEvict(value ="city")
    public void clearCache ()
    {
        System.out.println("Cache cleared...");
    }

 */
}
