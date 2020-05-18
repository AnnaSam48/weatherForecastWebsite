package com.accenture.weatherForecastWebsite.newVersion.controller;

import com.accenture.weatherForecastWebsite.newVersion.exceptions.ForecastNotFoundException;
import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import com.accenture.weatherForecastWebsite.newVersion.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.newVersion.weatherAPI.WeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;

@RestController
@RequestMapping("forecast")

public class ForecastRestController {

   @Autowired
   ForecastsByCityRepository forecastsByCityRepository;

    @Autowired
    WeatherAPIService weatherAPIService;

    @GetMapping(value = "/{cityName}", produces = "application/json")
    public Cities getForecast(@PathVariable String cityName, HttpServletResponse response) {
        try {
            return findByCityName(cityName);
        }
        catch (ForecastNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "City Not Found");
        }
    }


    private Cities findByCityName(String cityName) {
        Cities matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        if (matchedLocation != null) {
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));

            if (lastTimeUpdate.after(currentTimeMinusHour)) {
                return matchedLocation;
            } else {
                Cities forecast = weatherAPIService.getForecastByCity(cityName);
                return forecast;
            }

        } else {
            Cities forecast = weatherAPIService.getForecastByCity(cityName);
            return forecast;
       }

    }

    @PostMapping(value = "/{cityName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = "application/json" )
    public Cities setForecast(@PathVariable String cityName) {
       Cities matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (matchedLocation == null) {
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

            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));
            if (currentTimeMinusHour.after(lastTimeUpdate)) {
                //if last update is older than 1h
                Cities cityForUpdate = weatherAPIService.getForecastByCityID(matchedLocationId);
                matchedLocation.setTimestamp(currentTime);
                //API updates information in their db in every 2h, as we don't know at what time, we update every 1h, to get more precise data
                Date today=new Date(System.currentTimeMillis());
                if(lastTimeUpdate.before(today)){
                    //last update didn't happen today
                    matchedLocation.setSunrise(cityForUpdate.getSunrise());
                    matchedLocation.setSunset(cityForUpdate.getSunset());
                    matchedLocation.setTemp(cityForUpdate.getTemp());
                } else {//last update is older than 1 h
                    matchedLocation.setTemp(cityForUpdate.getTemp());
                    System.out.println("city was updated today, but not in last hour");
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



