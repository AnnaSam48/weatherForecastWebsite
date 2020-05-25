package com.accenture.weatherForecastWebsite.version2.service;

import com.accenture.weatherForecastWebsite.version2.controller.ForecastRestController;
import com.accenture.weatherForecastWebsite.version2.converters.TimeConverter;
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
    @Autowired
    TimeConverter timeConverter;
    Logger serviceLogger = LoggerFactory.getLogger(ForecastRestController.class);

    public City updateCity(City cityToUpdate) {

        String matchedLocationId = cityToUpdate.getId();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp lastTimeUpdate = cityToUpdate.getTimestamp();
        System.out.println(String.valueOf(lastTimeUpdate));

        if (!lastTimeUpdate.after(timeConverter.timeHourAgo())) {
            City cityForUpdate = weatherAPIService.getForecastByCityID(matchedLocationId);
            serviceLogger.trace("Data needs update...");
            cityToUpdate.setTimestamp(currentTime);

            serviceLogger.trace("Updating temperature data...");
            cityToUpdate.setTemp(cityForUpdate.getTemp());

            Date today = new Date(System.currentTimeMillis());


//TODO Help!!!
            if (lastTimeUpdate.before(today)) {
                cityToUpdate.setSunset(cityForUpdate.getSunset());

                cityToUpdate.setSunrise(cityForUpdate.getSunrise());
                 serviceLogger.trace("Updating sunset and sunrise data...");
            } else {
                System.out.println("after");
            }

            forecastsByCityRepository.save(cityToUpdate);
            serviceLogger.trace("Update saved...");


        } else {
            serviceLogger.trace("No need for update, data up to date ");
            return cityToUpdate;
        }
        return cityToUpdate;
    }
}