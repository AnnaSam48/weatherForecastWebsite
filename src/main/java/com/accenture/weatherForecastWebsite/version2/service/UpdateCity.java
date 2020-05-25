package com.accenture.weatherForecastWebsite.version2.service;

import com.accenture.weatherForecastWebsite.version2.ApiService.WeatherAPIService;
import com.accenture.weatherForecastWebsite.version2.controller.ForecastRestController;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;


@Component
public class UpdateCity {
    @Autowired
    WeatherAPIService weatherAPIService;
    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;
    Logger serviceLogger = LoggerFactory.getLogger(UpdateCity.class);

    public City updateCity(City cityToUpdate) {

        String matchedLocationId = cityToUpdate.getId();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp lastTimeUpdate = cityToUpdate.getTimestamp();
        Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));


        if (currentTimeMinusHour.after(lastTimeUpdate)) {
            City cityForUpdate = weatherAPIService.getForecastByCityID(matchedLocationId);
            serviceLogger.trace("Data needs update...");
            cityToUpdate.setTimestamp(currentTime);


            Date today = new Date(System.currentTimeMillis());
            if (lastTimeUpdate.before(today)) {
                cityToUpdate.setSunrise(cityForUpdate.getSunrise());
                cityToUpdate.setSunset(cityForUpdate.getSunset());
                serviceLogger.trace("Updating sunset and sunrise data...");

            }
            serviceLogger.trace("Updating temperature data...");
            cityToUpdate.setTemperature(cityForUpdate.getTemperature());
            forecastsByCityRepository.save(cityToUpdate);
            serviceLogger.trace("Update saved...");


        } else {
            serviceLogger.trace("No need for update, data up to date ");
            return cityToUpdate;
        }
        return cityToUpdate;
    }
}