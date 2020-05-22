package com.accenture.weatherForecastWebsite.version2.controller;


import ch.qos.logback.classic.Logger;
import com.accenture.weatherForecastWebsite.version2.ApiService.WeatherAPIService;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;



@RestController
@RequestMapping("forecast")
public class ForecastRestController {


    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;

    @Autowired
    WeatherAPIService weatherAPIService;

    Logger serviceLogger = (Logger) LoggerFactory.getLogger(ForecastRestController.class);

    @GetMapping(value = "/{cityName}", produces = "application/json")
    public City getForecast(@PathVariable String cityName) {
        return findByCityName(cityName);
    }
    private City findByCityName(String cityName) {
        serviceLogger.info("Requesting forecast for " +cityName+"...");
        City matchedLocation = forecastsByCityRepository.findByCityName(cityName);

        if (matchedLocation != null) {
            //add checking by country if name found
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));
            serviceLogger.info("Forecast for " +cityName+ " found in database...Checking the timestamp...");

            if (lastTimeUpdate.after(currentTimeMinusHour)) {
                serviceLogger.info("Timestamp valid... Data returned from database...");
                return matchedLocation;
            } else {
                serviceLogger.info("Timestamp has expired... New forecast given from external API...");
                return  weatherAPIService.getForecastByCity(cityName);
            }

        } else {
        serviceLogger.info("Accessing external API getting data...");
        City forecast = weatherAPIService.getForecastByCity(cityName);
        serviceLogger.info("Data retrieved from API...");
        return forecast;
        }
    }

    @PostMapping(value = "/{cityName}")
    public City setForecast(@PathVariable String cityName) {
        serviceLogger.info("Posting data... Checking database for existing data on " +cityName);
        Timestamp currentTime = new Timestamp (System.currentTimeMillis());
        City matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        if(matchedLocation != null) {
            serviceLogger.info("Data about " +cityName+" found in database...");

            String matchedLocationId = matchedLocation.getId();
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));
           if (currentTimeMinusHour.after(lastTimeUpdate)) {
                //if last update is older than 1h
               City cityForUpdate = weatherAPIService.getForecastByCityID(matchedLocationId);
               matchedLocation.setTimestamp(currentTime);
                //API updates information in their db in every 2h, as we don't know at what time, we update every 1h, to get more precise data
                Date today=new Date(System.currentTimeMillis());
                if(lastTimeUpdate.before(today)){
                    //last update didn't happen today
                    matchedLocation.setSunrise(cityForUpdate.getSunrise());
                    matchedLocation.setSunset(cityForUpdate.getSunset());
                    matchedLocation.setTemperature(cityForUpdate.getTemperature());
                } else {//last update is older than 1 h
                    matchedLocation.setTemperature(cityForUpdate.getTemperature());
                    serviceLogger.info("Update not recent... Updating temperature");
                }
                forecastsByCityRepository.save(matchedLocation);
            } else {
               System.out.println("city is already updated in time of last h");
                return matchedLocation;
            }

            return matchedLocation;
        } else {
            City getNewCity = weatherAPIService.getForecastByCity(cityName);
            serviceLogger.info("New city..."+cityName+ "... Saving all data from API");
            City cityToAdd = new City();
            cityToAdd.setId(getNewCity.getId());
            cityToAdd.setCityName(getNewCity.getCityName());
            cityToAdd.setCountry(getNewCity.getCountry());
            cityToAdd.setTemperature(getNewCity.getTemperature());
            cityToAdd.setSunrise(getNewCity.getSunrise());
            cityToAdd.setSunset(getNewCity.getSunset());
            forecastsByCityRepository.save(cityToAdd);
            serviceLogger.info("Data saved in database");
            return cityToAdd;
        }

    }
}