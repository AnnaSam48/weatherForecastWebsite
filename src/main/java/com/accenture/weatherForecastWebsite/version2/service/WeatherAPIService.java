package com.accenture.weatherForecastWebsite.version2.service;

import com.accenture.weatherForecastWebsite.version2.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URL;

@CacheConfig(cacheNames = "cityCache")
@Service
public class WeatherAPIService {

    @Value("${weather.api.request}") //beginning of API request link
    private String requestUrlBegin;

    @Value("${weather.api.key}") //You have to have API key at the end of link
    private String apiKey;

    @Value("${weather.api.request.by.name}") //if user input is name, used right after requestUrlBegin
    private String prefixName;

    @Value("${weather.api.request.by.cityId}") //if we search in database, used right after requestUrlBegin
    private String prefixCityId;

    @Autowired
    WeatherApiClient weatherApiClient;




    @Cacheable(cacheNames = "findByCityName", key = "#userInput")
    public City getForecastByCity(String userInput) {


        try {
            URL url = new URL(requestUrlBegin + prefixName + userInput + apiKey);
            City jsonResponse = weatherApiClient.getCityInformation(url);
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Cacheable(value = "city", key = "#cityID")
    public City getForecastByCityID(String cityID) {

        try {
            URL url = new URL(requestUrlBegin + prefixCityId + cityID + apiKey);
            City jsonResponse = weatherApiClient.getCityInformation(url);
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}