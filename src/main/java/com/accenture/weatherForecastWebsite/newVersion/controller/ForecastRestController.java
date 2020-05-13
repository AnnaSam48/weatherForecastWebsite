package com.accenture.weatherForecastWebsite.newVersion.controller;
import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import com.accenture.weatherForecastWebsite.newVersion.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.newVersion.weatherAPI.WeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if (matchedLocation == null) {
            Cities getNewCity = weatherAPIService.getForecastByCity(cityName);
            Cities cityToAdd = new Cities();
            cityToAdd.setId(getNewCity.getId());
            cityToAdd.setCityName(cityName);
            cityToAdd.setCountry(getNewCity.getCountry());
            cityToAdd.setTemp(getNewCity.getTemp());
            cityToAdd.setSunrise(getNewCity.getSunrise());
            cityToAdd.setSunset(getNewCity.getSunset());
            forecastsByCityRepository.save(cityToAdd);
            System.out.println("new city added");
            return cityToAdd;
        } else {
            System.out.println("city is already in DB");
            return matchedLocation;
        }
    }
}





      /*
            //TODO
            // make timestamp, to each table update for field
            // if(time.stamp is not older than 2h (API Documentation) then return DB info
            // else (ask for api for new information in needed parts)
            // latter separate dB table in normalisation levels
            // ID + name + country + lon/lat +timezone(probably needed for sunset)
            // sunrise + sunset table updates not more than once in day \
            // temp and timestamp should be updated every two h + could add wind/clouds

            TODO if not updated since 00:00 at the place
            Cities cityUpdate = weatherAPIService.getForecastByCity(cityName);
            matchedLocation.setSunset(cityUpdate.getSunset());
            matchedLocation.setSunrise(cityUpdate.getSunrise());
            //++ all the rest fields that are updatable
            matchedLocation.setTemp(cityUpdate.getTemp());
            + Update time stamp with new

            TODO if update has happened today, but haven't been updated last two h
            Cities cityUpdate = weatherAPIService.getForecastByCity(cityName);
            matchedLocation.setTemp(cityUpdate.getTemp());
            + Update time stamp with new

              */



