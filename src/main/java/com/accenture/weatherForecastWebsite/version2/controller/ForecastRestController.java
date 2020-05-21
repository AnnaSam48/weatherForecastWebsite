package com.accenture.weatherForecastWebsite.version2.controller;



import com.accenture.weatherForecastWebsite.version2.ApiService.WeatherAPIService;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;


@RestController
@RequestMapping("forecast")
public class ForecastRestController {
    Logger logger = LoggerFactory.getLogger(ForecastRestController.class);

    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;

    @Autowired
    WeatherAPIService weatherAPIService;


    @GetMapping(value = "/{cityName}", produces = "application/json")
    public City getForecast(@PathVariable String cityName) {
        return findByCityName(cityName);
    }

    private City findByCityName(String cityName) {
        City matchedLocation = forecastsByCityRepository.findByCityName(cityName);

        if (matchedLocation != null) {
            logger.info("Data found in database for this city.");
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));
            logger.info("Checking the timestamp");

            if (lastTimeUpdate.after(currentTimeMinusHour)) {
                logger.info("Data returned from db");
                return matchedLocation;
            } else {
                logger.info("Data returned from Api");
                City forecast = weatherAPIService.getForecastByCity(cityName);
                return forecast;
            }

        } else {

            City forecast = weatherAPIService.getForecastByCity(cityName);
            logger.info("Data returned from Api");
            return forecast;

        }

    }

    @PostMapping(value = "/{cityName}")
    public City setForecast(@PathVariable String cityName) {
        logger.info("POST data");
        City matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (matchedLocation == null) {
            City getNewCity = weatherAPIService.getForecastByCity(cityName);
            City cityToAdd = new City();
            cityToAdd.setId(getNewCity.getId());
            cityToAdd.setCityName(getNewCity.getCityName());
            cityToAdd.setCountry(getNewCity.getCountry());
            cityToAdd.setTemperature(getNewCity.getTemperature());
            cityToAdd.setSunrise(getNewCity.getSunrise());
            cityToAdd.setSunset(getNewCity.getSunset());
            cityToAdd.setTimestamp(currentTime);
            forecastsByCityRepository.save(cityToAdd);
            logger.info("New location added in db");
            return cityToAdd;
        } else {
            String matchedLocationId = matchedLocation.getId();
            logger.info("Data already found in db");
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));

            if (currentTimeMinusHour.after(lastTimeUpdate)) {
                City cityForUpdate = weatherAPIService.getForecastByCityID(matchedLocationId);
                logger.info("Data needs update");
                matchedLocation.setTimestamp(currentTime);
                Date today = new Date(System.currentTimeMillis());



                if (lastTimeUpdate.before(today)) {

                    matchedLocation.setSunrise(cityForUpdate.getSunrise());
                    matchedLocation.setSunset(cityForUpdate.getSunset());
                    logger.info("First update today");

                } else {
                    logger.info("Updated ony temperature part");
                }
                matchedLocation.setTemperature(cityForUpdate.getTemperature());
                forecastsByCityRepository.save(matchedLocation);
            } else {
                logger.info("no need for update");
                return matchedLocation;
            }
            return matchedLocation;
        }
    }
}