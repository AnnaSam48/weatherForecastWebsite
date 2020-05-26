package com.accenture.weatherForecastWebsite.version2.logic;

import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.version2.service.WeatherAPIRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class AddNewCity {
    @Autowired
    WeatherAPIRequests weatherAPIRequests;
    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;

    Logger serviceLogger = LoggerFactory.getLogger(AddNewCity.class);

    public City addNewCity(String cityToAdd) {

        City getNewCity = weatherAPIRequests.getForecastByCity(cityToAdd);
        City newCity = new City();
        newCity.setId(getNewCity.getId());
        newCity.setCityName(getNewCity.getCityName());
        newCity.setCountry(getNewCity.getCountry());
        newCity.setTemp(getNewCity.getTemp());
        newCity.setTimeZone(getNewCity.getTimeZone());
        newCity.setSunrise(getNewCity.getSunrise());
        newCity.setSunset(getNewCity.getSunset());

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        newCity.setTimestamp(currentTime);
        forecastsByCityRepository.save(newCity);

        serviceLogger.trace("New city " + getNewCity.getCityName() + " added in database...");
        return newCity;
    }
}
