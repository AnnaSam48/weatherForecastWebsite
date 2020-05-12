package com.accenture.weatherForecastWebsite.controller;

import com.accenture.weatherForecastWebsite.model.Forecast;
import com.accenture.weatherForecastWebsite.repository.ForecastRepository;
import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIServiceOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class RigaForecastController {


    @Autowired
    private WeatherAPIServiceOld weatherAPIServiceOld;

    @Autowired
    private ForecastRepository forecastRepository;


        @GetMapping("/riga")
        public String indexPage(Model model){
            String locationName = "Riga";
            Forecast foundForecast = weatherAPIServiceOld.getForecastByCity(locationName);
            model.addAttribute("forecast", foundForecast);

            return "index/riga";
        }


    }
