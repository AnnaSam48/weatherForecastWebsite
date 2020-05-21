package com.accenture.weatherForecastWebsite.version2.controller;


import com.accenture.weatherForecastWebsite.version2.ApiService.WeatherAPIService;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
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
    public City getForecast(@PathVariable String cityName) {
        return findByCityName(cityName);
    }

    private City findByCityName(String cityName) {

      City forecast = weatherAPIService.getForecastByCity(cityName);
        return forecast;
    }

    @PostMapping(value = "/{cityName}")
    public City setForecast(@PathVariable String cityName) {
        City matchedLocation = forecastsByCityRepository.findByCityName(cityName);
        if (matchedLocation == null) {
            City getNewCity = weatherAPIService.getForecastByCity(cityName);
            City cityToAdd = new City();
            cityToAdd.setId(getNewCity.getId());
            cityToAdd.setCityName(getNewCity.getCityName());
          cityToAdd.setCountry(getNewCity.getCountry());
            cityToAdd.setTemp(getNewCity.getTemp());
            cityToAdd.setSunrise(getNewCity.getSunrise());
            cityToAdd.setSunset(getNewCity.getSunset());
            forecastsByCityRepository.save(cityToAdd);
            System.out.println("new city added");
            return cityToAdd;
        }else {
            System.out.println("city already exists");
            return matchedLocation;
        }
    }
}