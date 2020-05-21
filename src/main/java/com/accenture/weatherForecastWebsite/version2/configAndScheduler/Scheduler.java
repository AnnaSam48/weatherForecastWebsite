/*package com.accenture.weatherForecastWebsite.version2.configAndScheduler;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class Scheduler {

    Logger cacheLogger = (Logger) LoggerFactory.getLogger(Scheduler.class);
    //cron expression telling how often cache update needs to execute
    @Scheduled(cron = "${update.cache.cron.start}")
    @CachePut(cacheNames ="cityCache")
    public void updateCache () //doesn't accept any parameters always returns void type
    {
        cacheLogger.info("Cache data updated...");
    }

    //cron expression telling when cache needs to be cleared
    @Scheduled(cron = "${delete.data.cache.cron.start}")
    @CacheEvict(cacheNames ="cityCache")
    public void clearCache ()
    {
        cacheLogger.info("Cache data cleared...");
    }

}
*/