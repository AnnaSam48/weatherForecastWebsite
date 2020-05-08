package com.accenture.weatherForecastWebsite.controller;

import com.accenture.weatherForecastWebsite.model.Forecast;
import com.accenture.weatherForecastWebsite.repository.ForecastRepository;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIResponse;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


public class SearchController {
    //We need to change search by id, if db is used!

    @Autowired
    private WeatherAPIResponse weatherAPIResponse;

    @Autowired
    private WeatherAPIService weatherAPIService;

    @Autowired
    private ForecastRepository forecastRepository;

    @GetMapping("/searchLocation")
    public String searchByLocation(@PathVariable String location, Model model) {
        return "index";
    }

    @PostMapping("/searchLocation")
    public ModelAndView showByLocation(@PathVariable String location, Model model, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        Forecast matchedLocation = forecastRepository.findAllByName(location);
        if (bindingResult.hasErrors()) {

            //probably error info needed here

        } else {

            // do we need try catch cycle here?
            Forecast forecastToAdd = weatherAPIService.getForecastByCity(location);
            if (matchedLocation == null) {
                forecastRepository.save(forecastToAdd);
                modelAndView.setViewName("/");
            } else {
                forecastToAdd.setTemperature(weatherAPIService.getForecastByCity(location).getTemperature());

            }
            modelAndView.addObject("forecast", forecastToAdd);
        }
        modelAndView.setViewName("/");
        return modelAndView;
    }
}
