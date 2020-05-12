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


    @RequestMapping(value = "/{cityName}", method = RequestMethod.GET, produces = "application/json")
    public Cities getForecast(@PathVariable String cityName) {
        return findByCityName(cityName);
    }

    private Cities findByCityName(String cityName) {
        Cities forecast = weatherAPIService.getForecastByCity(cityName);
        forecast.setId(weatherAPIService.getForecastByCity(cityName).getId());
        forecast.setCityName(cityName);
        forecast.setCountry(weatherAPIService.getForecastByCity(cityName).getCountry());
        forecast.setTemp(weatherAPIService.getForecastByCity(cityName).getTemp());
        forecast.setSunrise(weatherAPIService.getForecastByCity(cityName).getSunrise());
        forecast.setSunset(weatherAPIService.getForecastByCity(cityName).getSunset());
        return forecast;
    }
}