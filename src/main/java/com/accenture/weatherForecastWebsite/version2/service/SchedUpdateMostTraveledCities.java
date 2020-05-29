package com.accenture.weatherForecastWebsite.version2.service;

import ch.qos.logback.classic.Logger;
import com.accenture.weatherForecastWebsite.version2.logic.CityService;
import com.accenture.weatherForecastWebsite.version2.logic.ForecastService;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchedUpdateMostTraveledCities {

    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;
    @Autowired
    CityService cityService;


    Logger cronJobLogger = (Logger) LoggerFactory.getLogger(SchedUpdateMostTraveledCities.class);

    //cron expression telling how often cache update needs to execute
    @Scheduled(cron = "${update.data.cron.start}")
    public void updateData() //doesn't accept any parameters always returns void type
    {
        try {
            cronJobLogger.info("Starting scheduled Cron job...");
            List<String> mostPopularDestinations = new ArrayList<>();
            mostPopularDestinations.add("Dubai");
            mostPopularDestinations.add("London, GB");
            mostPopularDestinations.add("Paris, FR");
            mostPopularDestinations.add("Bangkok");
            mostPopularDestinations.add("Singapore");
            mostPopularDestinations.add("Kuala Lumpur");
            mostPopularDestinations.add("New York");
            mostPopularDestinations.add("Istanbul");
            mostPopularDestinations.add("Tokyo");
            mostPopularDestinations.add("Antalya");


            mostPopularDestinations.forEach(mostPopularDestination -> {

                //Check if there is information about country as well
                if (mostPopularDestination.contains(",")) {
                    String[] placeParts = mostPopularDestination.split("\\s*,\\s*");
                    String city = placeParts[0];
                    String country = placeParts[1];

                    //Check if both city and country are not already in database
                    if (forecastsByCityRepository
                            .findByCityNameAndCountryIgnoreCaseContaining(city, country) == null) {
                        cronJobLogger.info("Adding new city " + mostPopularDestination +"...");
                        cityService.addNewCity(city);

                    } else {

                        String cityId = forecastsByCityRepository
                                .findByCityNameAndCountryIgnoreCaseContaining(city, country).getId();
                        cronJobLogger.info("Updating " + mostPopularDestination + " forecast in database...");
                        cityService.updateCity(forecastsByCityRepository.findById(cityId).get());
                    }
                    //if location doesn't contain country
                } else {
                    if (forecastsByCityRepository
                            .findByCityNameIgnoreCaseContaining(mostPopularDestination) == null) {
                        cronJobLogger.info("Adding new city " + mostPopularDestination +"...");
                        cityService.addNewCity(mostPopularDestination);

                    } else {
                        String cityId = forecastsByCityRepository
                                .findByCityNameIgnoreCaseContaining(mostPopularDestination).getId();
                        cronJobLogger.info("Updating " + mostPopularDestination + " forecast in database...");
                        cityService.updateCity(forecastsByCityRepository.findById(cityId).get());
                    }
                }
            });
            cronJobLogger.info("Cron job finished...");
        } catch (RuntimeException ex) {
            cronJobLogger.error(ex.toString());
            throw new RuntimeException(ex);
        }
    }
}
