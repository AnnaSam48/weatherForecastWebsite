package com.accenture.weatherForecastWebsite.controller;

import com.accenture.weatherForecastWebsite.model.Forecast;
import com.accenture.weatherForecastWebsite.repository.ForecastRepository;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

public class RigaForecastController {


    @Autowired
    private WeatherAPIService weatherAPIService;

    @Autowired
    private ForecastRepository forecastRepository;
    
        @GetMapping("/riga")
        public String indexPage(Model model){
            String locationName = "Riga";
            Forecast foundForecast = weatherAPIService.getForecastByCity(locationName);
            model.addAttribute("forecast", foundForecast);

            return "index/riga";
        }

    }
