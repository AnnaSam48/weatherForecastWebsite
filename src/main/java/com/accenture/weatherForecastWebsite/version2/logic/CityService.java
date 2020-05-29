package com.accenture.weatherForecastWebsite.version2.logic;

import com.accenture.weatherForecastWebsite.version2.converters.TimeConverter;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.version2.service.WeatherAPIRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

@Component
@Service
public class CityService {

    @Autowired
    WeatherAPIRequests weatherAPIRequests;
    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;
    @Autowired
    TimeConverter timeConverter;

    Logger serviceLogger = LoggerFactory.getLogger(CityService.class);

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

    public City updateCity(City cityToUpdate) {

        String matchedLocationId = cityToUpdate.getId();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp lastTimeUpdate = cityToUpdate.getTimestamp();

        if (!lastTimeUpdate.after(timeConverter.timeHourAgo())) {
            serviceLogger.trace("Data expired... Will update data...");
            City cityForUpdate = weatherAPIRequests.getForecastByCityID(matchedLocationId);
            cityToUpdate.setTimestamp(currentTime);

            serviceLogger.trace("Updating temperature data...");
            cityToUpdate.setTemp(cityForUpdate.getTemp());

            Date today = new Date(System.currentTimeMillis());
            if (lastTimeUpdate.before(today)) {
                serviceLogger.trace("Updating sunset and sunrise data...");
                cityToUpdate.setSunset(cityForUpdate.getSunset());
                cityToUpdate.setSunrise(cityForUpdate.getSunrise());

            }
            serviceLogger.trace("Saving update...");
            forecastsByCityRepository.save(cityToUpdate);

        } else {
            serviceLogger.trace("No need for update, data up to date ");
            return cityToUpdate;
        }
        return cityToUpdate;
    }
}
