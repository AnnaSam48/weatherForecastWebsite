package com.accenture.weatherForecastWebsite.controller;

import com.accenture.weatherForecastWebsite.model.Forecast;
import com.accenture.weatherForecastWebsite.repository.ForecastRepository;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIResponseOld;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIServiceOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


public class SearchController {
    //We need to change search by id, if db is used!

    @Autowired
    private WeatherAPIResponseOld weatherAPIResponseOld;

    @Autowired
    private WeatherAPIServiceOld weatherAPIServiceOld;

    @Autowired
    private ForecastRepository forecastRepository;

    @GetMapping("/searchLocation")
    public ModelAndView searchByLocation(@PathVariable String location, Forecast forecast) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchLocation");
        return modelAndView;
    }


    @PostMapping("/searchLocation")
    public ModelAndView showByLocation(@PathVariable String location) {
        ModelAndView modelAndView = new ModelAndView();
        String cityID = weatherAPIServiceOld.getForecastByCity(location).getId();
        Forecast matchedLocation = forecastRepository.findAllById(cityID);


        Forecast forecastToAdd = weatherAPIServiceOld.getForecastByCity(location);
        if (matchedLocation == null) {
            forecastToAdd.setCityName(location);
            forecastToAdd.setId(cityID);
            forecastToAdd.setMainWeather(weatherAPIServiceOld.getForecastByCityID(cityID).getMainWeather());
            forecastToAdd.setClouds(weatherAPIServiceOld.getForecastByCity(location).getClouds());
            modelAndView.setViewName("/");
        } else {
            forecastToAdd.setMainWeather(weatherAPIServiceOld.getForecastByCityID(cityID).getMainWeather());
            forecastToAdd.setClouds(weatherAPIServiceOld.getForecastByCity(cityID).getClouds());

        }
        forecastRepository.save(forecastToAdd);
        modelAndView.addObject("forecast", forecastToAdd);
        modelAndView.setViewName("/searchLocation");


        return modelAndView;
    }


}
