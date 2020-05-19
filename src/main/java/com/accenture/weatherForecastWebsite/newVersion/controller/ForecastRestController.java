package com.accenture.weatherForecastWebsite.newVersion.controller;

import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import com.accenture.weatherForecastWebsite.newVersion.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.newVersion.weatherAPI.WeatherAPIService;
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
    public Cities getForecast(@PathVariable String cityName) {
            return findByCityName(cityName);
    }

    private Cities findByCityName(String cityName) {
        Cities matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        if (matchedLocation != null) {
            logger.info("Data found in database for this city.");
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));
            logger.info("Checking the timestamp");

            if (lastTimeUpdate.after(currentTimeMinusHour)) {
                return matchedLocation;
            } else {
                logger.info("Data given from database");
                Cities forecast = weatherAPIService.getForecastByCity(cityName);
                return forecast;
            }

        } else {
            logger.info("Data given from API");
            Cities forecast = weatherAPIService.getForecastByCity(cityName);
            return forecast;
        }

    }

    @PostMapping(value = "/{cityName}")
    public Cities setForecast(@PathVariable String cityName) {
        logger.info("Adding data to database");
        Cities matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (matchedLocation == null) {
            logger.info("Adding data about new location");
            Cities getNewCity = weatherAPIService.getForecastByCity(cityName);
            Cities cityToAdd = new Cities();
            cityToAdd.setId(getNewCity.getId());
            cityToAdd.setCityName(getNewCity.getCityName());
            cityToAdd.setCountry(getNewCity.getCountry());
            cityToAdd.setTemp(getNewCity.getTemp());
            cityToAdd.setSunrise(getNewCity.getSunrise());
            cityToAdd.setSunset(getNewCity.getSunset());
            cityToAdd.setTimestamp(currentTime);
            forecastsByCityRepository.save(cityToAdd);
            System.out.println("new city added");
            return cityToAdd;
        } else {
            String matchedLocationId = matchedLocation.getId();
            logger.info("Data found in database updating");

            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));
            if (currentTimeMinusHour.after(lastTimeUpdate)) {
                //if last update is older than 1h
                Cities cityForUpdate = weatherAPIService.getForecastByCityID(matchedLocationId);
                matchedLocation.setTimestamp(currentTime);
                //API updates information in their db in every 2h, as we don't know at what time, we update every 1h, to get more precise data
                Date today = new Date(System.currentTimeMillis());
                if (lastTimeUpdate.before(today)) {
                    //last update didn't happen today
                    logger.info("Updating info... not updated today");
                    matchedLocation.setSunrise(cityForUpdate.getSunrise());
                    matchedLocation.setSunset(cityForUpdate.getSunset());
                    matchedLocation.setTemp(cityForUpdate.getTemp());
                } else {//last update is older than 1 h
                    logger.info("Updating info... not updated in last hour");
                    matchedLocation.setTemp(cityForUpdate.getTemp());
                }
                forecastsByCityRepository.save(matchedLocation);
            } else {
                System.out.println("city is already updated in time of last h");
                return matchedLocation;
            }

            System.out.println("city is already in DB");
            return matchedLocation;
        }
    }
}

      /*
            //TODO
            // separate dB table in normalisation levels
            // ID + name + country + lon/lat
            // sunrise + sunset table updates not more than once in day
            // temp and timestamp should be updated every two h + could add wind/clouds
            //add time zone in sun set sun rise + UTC= then convert
            //
              */
 /*
            //TODO
            // Make classes for conversions date/Kelvin and all the rest ones
              */

 /*
            //TODO
            // Make test classes
              */



