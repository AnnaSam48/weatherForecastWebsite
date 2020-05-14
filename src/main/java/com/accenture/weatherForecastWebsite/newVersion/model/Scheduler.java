package com.accenture.weatherForecastWebsite.newVersion.model;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
@EnableScheduling
public class Scheduler {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //cron expression telling how often needs to execute
    @Scheduled(cron = "${update.cache.cron.start}")
    @CachePut(value ="city")
    public void updateCache () //doesn't accept any parameters always returns void type
    {
        System.out.println("Cache updated...");
    }

    @Scheduled(cron = "${delete.data.cache.cron.start}")
    @CacheEvict(value ="city")
    public void clearCache ()
    {
        System.out.println("Cache cleared...");
    }
}
