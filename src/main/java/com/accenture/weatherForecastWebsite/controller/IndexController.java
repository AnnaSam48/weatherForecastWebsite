package com.accenture.weatherForecastWebsite.controller;

import com.accenture.weatherForecastWebsite.repository.ForecastRepository;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIService;
import com.accenture.weatherForecastWebsite.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IndexController {

    @Autowired
    private WeatherAPIService weatherAPIService;

    @Autowired
    private ForecastRepository forecastRepository;


        @GetMapping("/")
        public String indexPage(Model model){
            Forecast foundForecast = weatherAPIService.getForecastForRiga();
            model.addAttribute("forecast", foundForecast);

            return "index";
        }
}

