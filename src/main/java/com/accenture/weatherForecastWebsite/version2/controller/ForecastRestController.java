package com.accenture.weatherForecastWebsite.version2.controller;

import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.model.Forecast;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.version2.service.Logic.ForecastService;
import com.accenture.weatherForecastWebsite.version2.service.Logic.UpdateCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.accenture.weatherForecastWebsite.version2.constants.ForecastConstants.API_MEDIA_TYPE;


@RestController
@RequestMapping("/forecast")
public class ForecastRestController {


    @Autowired
    ForecastService forecastService;

    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;

    @Autowired
    UpdateCity updateCity;


    @GetMapping(value = "/{cityName}", produces = API_MEDIA_TYPE)
    public Forecast getForecast(@PathVariable String cityName) {
        return forecastService.findForecast(cityName);
    }


    @PostMapping(value = "/{cityName}")
    public City setForecast(@PathVariable String cityName) {
        return forecastService.setForecast(cityName);
    }

    @PutMapping(value = "/{cityId}")
    public City updatedForecast(@PathVariable String cityId) {

        return updateCity.updateCity(forecastsByCityRepository.findById(cityId).get());
    }
}
