package com.accenture.weatherForecastWebsite.version2.service;

import ch.qos.logback.classic.Logger;
import com.accenture.weatherForecastWebsite.version2.logic.AddNewCity;
import com.accenture.weatherForecastWebsite.version2.logic.UpdateCity;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ScheduledDataUpdate {

    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;

    @Autowired
    AddNewCity addNewCity;

    @Autowired
    UpdateCity updateCity;

    Logger cronJobLogger = (Logger) LoggerFactory.getLogger(ScheduledDataUpdate.class);

    //cron expression telling how often cache update needs to execute
    @Scheduled(cron = "${update.data.cron.start}")
    public void updateData() //doesn't accept any parameters always returns void type
    {
        try {
            cronJobLogger.info("Starting scheduled cron job...");
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
                            .findByCityNameIgnoreCaseContaining(city) != null
                            && forecastsByCityRepository.findByCountry(country) == null
                    ) {

                        cronJobLogger.info(mostPopularDestination + " not found. Adding " + mostPopularDestination + "...");
                        addNewCity.addNewCity(city);

                        //Check if there is a city  in db with matching name, but in different country
                    } else if (forecastsByCityRepository
                            .findByCityNameAndCountryIgnoreCaseContaining(city, country) == null) {

                        cronJobLogger.info(mostPopularDestination + " not found. Adding " + mostPopularDestination + "...");
                        addNewCity.addNewCity(city);

                    } else {

                        String cityId = forecastsByCityRepository
                                .findByCityNameAndCountryIgnoreCaseContaining(city, country).getId();
                        cronJobLogger.info("Updating " + mostPopularDestination + " forecast in database...");
                        updateCity.updateCity(forecastsByCityRepository.findById(cityId).get());
                    }
                    //if location doesn't contain country
                } else {
                    if (forecastsByCityRepository
                            .findByCityNameIgnoreCaseContaining(mostPopularDestination) == null) {

                        cronJobLogger.info(mostPopularDestination + " not found. Adding " + mostPopularDestination + "...");
                        addNewCity.addNewCity(mostPopularDestination);

                    } else {
                        String cityId = forecastsByCityRepository
                                .findByCityNameIgnoreCaseContaining(mostPopularDestination).getId();
                        cronJobLogger.info("Updating " + mostPopularDestination + " forecast in database...");
                        updateCity.updateCity(forecastsByCityRepository.findById(cityId).get());
                    }
                }
            });
        cronJobLogger.info("Cron job finished");
        } catch (RuntimeException ex) {
            cronJobLogger.error(ex.toString());
            throw new RuntimeException(ex);
        }
    }
}
