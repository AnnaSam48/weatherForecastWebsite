package com.accenture.weatherForecastWebsite.controller;

import com.accenture.weatherForecastWebsite.model.Forecast;
import com.accenture.weatherForecastWebsite.repository.ForecastRepository;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIResponse;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIService;
import com.google.gson.internal.$Gson$Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


public class SearchController {
    //We need to change search by id, if db is used!

    @Autowired
    private WeatherAPIResponse weatherAPIResponse;

    @Autowired
    private WeatherAPIService weatherAPIService;

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
        String cityID = weatherAPIService.getForecastByCity(location).getId();
        Forecast matchedLocation = forecastRepository.findAllById(cityID);


        Forecast forecastToAdd = weatherAPIService.getForecastByCity(location);
        if (matchedLocation == null) {
            forecastToAdd.setCityName(location);
            forecastToAdd.setId(cityID);
            forecastToAdd.setMainWeather(weatherAPIService.getForecastByCityID(cityID).getMainWeather());
            forecastToAdd.setClouds(weatherAPIService.getForecastByCity(location).getClouds());
            modelAndView.setViewName("/");
        } else {
            forecastToAdd.setMainWeather(weatherAPIService.getForecastByCityID(cityID).getMainWeather());
            forecastToAdd.setClouds(weatherAPIService.getForecastByCity(cityID).getClouds());

        }
        forecastRepository.save(forecastToAdd);
        modelAndView.addObject("forecast", forecastToAdd);
        modelAndView.setViewName("/searchLocation");


        return modelAndView;
    }


}
