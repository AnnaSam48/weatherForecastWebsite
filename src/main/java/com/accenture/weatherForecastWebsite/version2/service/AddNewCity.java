package com.accenture.weatherForecastWebsite.version2.service;


import com.accenture.weatherForecastWebsite.version2.controller.ForecastRestController;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class AddNewCity {
    @Autowired
    WeatherAPIService weatherAPIService;
    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;

    Logger serviceLogger = LoggerFactory.getLogger(AddNewCity.class);

    public City addNewCity(String cityToAdd) {

        City getNewCity = weatherAPIService.getForecastByCity(cityToAdd);
        City newCity = new City();
        newCity.setId(getNewCity.getId());
        newCity.setCityName(getNewCity.getCityName());
        newCity.setCountry(getNewCity.getCountry());
        newCity.setTemp(getNewCity.getTemp());
        newCity.setTimeZone(getNewCity.getTimeZone());
        newCity.setSunriseOfTheLocation(getNewCity.sunriseOfTheLocation);
        newCity.setSunSetOfTheLocation(getNewCity.sunSetOfTheLocation);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        newCity.setTimestamp(currentTime);
        forecastsByCityRepository.save(newCity);

        serviceLogger.trace("New city " + getNewCity.getCityName() + " added in database...");
        return newCity;
    }
}
