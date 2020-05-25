package com.accenture.weatherForecastWebsite.version2.service.ServiceLogic;

import com.accenture.weatherForecastWebsite.version2.converters.TimeConverter;
import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.model.Forecast;
import com.accenture.weatherForecastWebsite.version2.repository.ForecastsByCityRepository;
import com.accenture.weatherForecastWebsite.version2.service.RequestServices.WeatherAPIRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Component
@Service
public class ForecastService {


    @Autowired
    City city;
    @Autowired
    ForecastsByCityRepository forecastsByCityRepository;
    @Autowired
    WeatherAPIRequestService weatherAPIService;
    @Autowired
    AddNewCity addNewCity;
    @Autowired
    UpdateCity updateCity;
    @Autowired
    TimeConverter timeConverter;


    @Autowired
    GetForecast getForecast;

    Logger serviceLogger = LoggerFactory.getLogger(ForecastService.class);

    public City getCity() {
        return city;
    }

    public Forecast findForecast(String cityName) {
        City matchedLocation = forecastsByCityRepository.findByCityNameIgnoreCaseContaining(cityName);


        if (matchedLocation != null) {
            serviceLogger.trace("Forecast for " + cityName + " found in database...");
            Timestamp lastTimeUpdate = matchedLocation.getTimestamp();
            serviceLogger.trace("Checking the timestamp...");

            if (lastTimeUpdate.after(timeConverter.timeHourAgo())) {
                serviceLogger.trace("Data returned from database...");
                return getForecast.getForecast(matchedLocation);
            } else {
                serviceLogger.trace("Data retrieved from external API...");
                City forecast = weatherAPIService.getForecastByCity(cityName);

                return getForecast.getForecast(forecast);
            }

        } else {
            City forecast = weatherAPIService.getForecastByCity(cityName);
            serviceLogger.trace("Data retrieved from external API...");
            return getForecast.getForecast(forecast);
        }


    }

    public City setForecast(String cityName) {
        serviceLogger.trace("Checking data before saving...");
        City matchedLocation = forecastsByCityRepository.findByCityNameIgnoreCaseContaining(cityName);

        if (matchedLocation == null) {
            serviceLogger.trace("No city found in database...");
            return addNewCity.addNewCity(cityName);

        } else {
            serviceLogger.trace("Data about " + cityName + " already in database");
            return updateCity.updateCity(matchedLocation);
        }

    }
}
