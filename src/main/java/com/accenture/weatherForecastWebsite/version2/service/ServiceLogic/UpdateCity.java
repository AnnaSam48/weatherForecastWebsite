package com.accenture.weatherForecastWebsite.version2.service.ServiceLogic;

import com.accenture.weatherForecastWebsite.version2.converters.TimeConverter;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.version2.service.RequestServices.WeatherAPIRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;


@Component
public class UpdateCity {
    @Autowired
    WeatherAPIRequestService weatherAPIRequestService;
    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;
    @Autowired
    TimeConverter timeConverter;

    Logger serviceLogger = LoggerFactory.getLogger(UpdateCity.class);

    public City updateCity(City cityToUpdate) {

        String matchedLocationId = cityToUpdate.getId();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp lastTimeUpdate = cityToUpdate.getTimestamp();
        Timestamp currentTimeMinusHour = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));

        if (!lastTimeUpdate.after(timeConverter.timeHourAgo())) {
            City cityForUpdate = weatherAPIRequestService.getForecastByCityID(matchedLocationId);
            serviceLogger.trace("Data expired... Will update data...");
            cityToUpdate.setTimestamp(currentTime);

            serviceLogger.trace("Updating temperature data...");
            cityToUpdate.setTemp(cityForUpdate.getTemp());

            Date today = new Date(System.currentTimeMillis());
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