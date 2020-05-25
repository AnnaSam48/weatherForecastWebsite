package com.accenture.weatherForecastWebsite.version2.service;

import com.accenture.weatherForecastWebsite.version2.controller.ForecastRestController;
import com.accenture.weatherForecastWebsite.version2.converters.TimeConverter;
import com.accenture.weatherForecastWebsite.version2.converters.TemperatureConverter;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.model.Forecast;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetForecast {


    @Autowired
    WeatherAPIService weatherAPIService;
    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;
    @Autowired
    Forecast forecastClass;
    @Autowired
    TimeConverter timeConverter;
    @Autowired
    TemperatureConverter temperatureConverter;


    public Forecast getForecast(City cityToReturn) {

        forecastClass.setCityName(cityToReturn.getCityName());
        forecastClass.setCountry(cityToReturn.getCountry());
        forecastClass.setSunrise(timeConverter.getTimeFormUnx
                (cityToReturn.getSunrise() + cityToReturn.getTimeZone()));

        forecastClass.setSunset(timeConverter.getTimeFormUnx
                (cityToReturn.getSunset() + cityToReturn.getTimeZone()));
        forecastClass.setTemperature(String.valueOf(temperatureConverter.getCelsiusFormKelvin(cityToReturn.getTemp())) + " " + "\u00B0" + "C");
        return forecastClass;

    }
}
