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



    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;


    @Autowired
    WeatherAPIService weatherAPIService;

    Logger serviceLogger = LoggerFactory.getLogger(ForecastRestController.class);


    @GetMapping(value = "/{cityName}", produces = "application/json")
    public City getForecast(@PathVariable String cityName) {
        return findByCityName(cityName);
    }

    private City findByCityName(String cityName) {
        City matchedLocation = (City) forecastsByCityRepository.findByCityName(cityName);

        serviceLogger.trace("Requesting forecast for " + cityName + "...");
        if (matchedLocation != null) {
            serviceLogger.info("Forecast for " + cityName + " found in database...");
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));
            serviceLogger.info("Checking the timestamp...");

            if (lastTimeUpdate.after(currentTimeMinusHour)) {
                serviceLogger.info("Data returned from database...");
                return matchedLocation;
            } else {
                serviceLogger.info("Data retrieved from Api...");
                City forecast = weatherAPIService.getForecastByCity(cityName);
                return forecast;
            }

        } else {

            City forecast = weatherAPIService.getForecastByCity(cityName);
            serviceLogger.info("Data retrieved from Api...");
            return forecast;

        }

    }

    @PostMapping(value = "/{cityName}")
    public City setForecast(@PathVariable String cityName) {
        serviceLogger.info("Posting data...");
        City matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (matchedLocation == null) {
            serviceLogger.info("New location...");
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
            serviceLogger.info(cityName+" added in database...");
            return cityToAdd;
        } else {
            String matchedLocationId = matchedLocation.getId();
            serviceLogger.info("Data about " +cityName+" already found in database");
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));

            if (currentTimeMinusHour.after(lastTimeUpdate)) {
                City cityForUpdate = weatherAPIService.getForecastByCityID(matchedLocationId);
                serviceLogger.info("Data needs update...");
                matchedLocation.setTimestamp(currentTime);
                Date today = new Date(System.currentTimeMillis());



                if (lastTimeUpdate.before(today)) {

                    matchedLocation.setSunrise(cityForUpdate.getSunrise());
                    matchedLocation.setSunset(cityForUpdate.getSunset());
                    serviceLogger.info("Updating sunset, sunrise, temperature data...");

                } else {
                    serviceLogger.info("Updating temperature data...");
                }
                matchedLocation.setTemperature(cityForUpdate.getTemperature());
                serviceLogger.info("Update saved...");
                forecastsByCityRepository.save(matchedLocation);
            } else {
                serviceLogger.info("No need for update, data up to date ");
                return matchedLocation;
            }
            return matchedLocation;
        }
    }
}