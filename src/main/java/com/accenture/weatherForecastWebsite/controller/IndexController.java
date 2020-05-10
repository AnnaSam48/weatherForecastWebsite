package com.accenture.weatherForecastWebsite.controller;

import com.accenture.weatherForecastWebsite.weatherAPI.WeatherAPIService;
import com.accenture.weatherForecastWebsite.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class IndexController {



    @GetMapping("/")
    public String indexPage(Model model){

        return "index";
    }


}
