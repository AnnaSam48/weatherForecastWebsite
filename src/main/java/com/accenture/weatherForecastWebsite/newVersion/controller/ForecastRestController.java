package com.accenture.weatherForecastWebsite.newVersion.controller;

import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import com.accenture.weatherForecastWebsite.newVersion.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.newVersion.weatherAPI.WeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@RestController
@RequestMapping("forecast")
public class ForecastRestController {
    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;

    @Autowired
    WeatherAPIService weatherAPIService;


    @GetMapping(value = "/{cityName}", produces = "application/json")
    public Cities getForecast(@PathVariable String cityName) {
        return findByCityName(cityName);
    }

    private Cities findByCityName(String cityName) {
        Cities forecast = weatherAPIService.getForecastByCity(cityName);
        return forecast;
    }

    @PostMapping(value = "/{cityName}")
    public Cities setForecast(@PathVariable String cityName) {
        Cities matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        long currentTimeRaw = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(currentTimeRaw);

        if (matchedLocation == null) {
            Cities getNewCity = weatherAPIService.getForecastByCity(cityName);
            Cities cityToAdd = new Cities();
            cityToAdd.setId(getNewCity.getId());
            cityToAdd.setCityName(cityName);
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
            Timestamp lastUpdateRaw = matchedLocation.getTimestamp();
            long lastUpdateTime = Long.parseLong(lastUpdateRaw.toString());
            long currentTimeMinusH = System.currentTimeMillis() - (60 * 60 * 1000);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String currentDate=simpleDateFormat.format(currentTime);
            String lastUpdateDate=simpleDateFormat.format(lastUpdateTime);

            if (lastUpdateTime < currentTimeMinusH || lastUpdateTime == currentTimeMinusH) {

                Cities cityForUpdate = weatherAPIService.getForecastByCityID(matchedLocationId);
                matchedLocation.setTimestamp(currentTime);
                //API updates information in their db in every 2h, as we don't know at what time, we update every 1h, to get more precise data

                if (currentDate!=lastUpdateDate) {
                    //last update didn't happen today
                    matchedLocation.setSunrise(cityForUpdate.getSunrise());
                    matchedLocation.setSunset(cityForUpdate.getSunset());
                    matchedLocation.setTemp(cityForUpdate.getTemp());
                }else {//last update is older than 1 h
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

              */
 /*
            //TODO
            // Make classes for conversions date/Kelvin and all the rest ones
              */

 /*
            //TODO
            // Make test classes
              */



