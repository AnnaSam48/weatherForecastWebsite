package com.accenture.weatherForecastWebsite.controller;

import com.accenture.weatherForecastWebsite.model.Forecast;
import com.accenture.weatherForecastWebsite.repository.ForecastRepository;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {
    @Autowired
    WeatherAPIService weatherAPIService;
    @Autowired
    ForecastRepository forecastRepository;


    @GetMapping("/")
    public ModelAndView indexPage(Forecast forecast) {
        ModelAndView modelAndView = new ModelAndView();
        String location = "Riga";
        forecast.setId(weatherAPIService.getForecastByCity(location).getId());
        forecast.setCityName(location);
        forecast.setMainWeather(weatherAPIService.getForecastByCity(location).getMainWeather());
        forecast.setClouds(weatherAPIService.getForecastByCity(location).getClouds());
        modelAndView.addObject("forecast", forecast);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/")
    public ModelAndView showByLocation() {
        ModelAndView modelAndView = new ModelAndView();
        String location = "Riga";
        String cityID = weatherAPIService.getForecastByCity(location).getId();
        Forecast matchedLocation = forecastRepository.findAllById(cityID);


        Forecast forecastToAdd = weatherAPIService.getForecastByCity(location);
        if (matchedLocation == null) {
            forecastToAdd.setCityName(location);
            forecastToAdd.setId(cityID);
            forecastToAdd.setMainWeather(weatherAPIService.getForecastByCityID(cityID).getMainWeather());
            forecastToAdd.setClouds(weatherAPIService.getForecastByCity(location).getClouds());
            forecastRepository.save(forecastToAdd);
            modelAndView.setViewName("/");
        } else {
            forecastToAdd.setMainWeather(weatherAPIService.getForecastByCityID(cityID).getMainWeather());
            forecastToAdd.setClouds(weatherAPIService.getForecastByCity(cityID).getClouds());

        }
        modelAndView.addObject("forecast", forecastToAdd);
        modelAndView.setViewName("index");


        return modelAndView;
    }


}
