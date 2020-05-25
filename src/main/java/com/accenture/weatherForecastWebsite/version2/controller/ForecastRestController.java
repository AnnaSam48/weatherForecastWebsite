package com.accenture.weatherForecastWebsite.version2.controller;

import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.model.Forecast;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.version2.service.ServiceLogic.ForecastService;
import com.accenture.weatherForecastWebsite.version2.service.ServiceLogic.UpdateCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/forecast")
public class ForecastRestController {


    @Autowired
    ForecastService forecastService;

    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;

    @Autowired
    UpdateCity updateCity;


    @GetMapping(value = "/{cityName}", produces = "application/json")
    public Forecast getForecast(@PathVariable String cityName) {
        return forecastService.findForecast(cityName);
    }


    @PostMapping(value = "/{cityName}")
    public City setForecast(@PathVariable String cityName) {
        return forecastService.setForecast(cityName);
    }

    @PutMapping(value = "/{cityId}")
    public City updatedForecast(@PathVariable String cityId) {
        return forecastService.setForecast(cityId);
    }
}
