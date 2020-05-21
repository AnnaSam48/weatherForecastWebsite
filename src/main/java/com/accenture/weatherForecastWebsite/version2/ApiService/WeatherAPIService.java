package com.accenture.weatherForecastWebsite.version2.ApiService;


import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.service.GetJsonResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


import java.net.URL;


@Component
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
    GetJsonResponseService getJsonResponseService;




    @Cacheable(value = "city", key = "#userInput")
    public City getForecastByCity(String userInput) {


        try {
            URL url = new URL(requestUrlBegin + prefixName + userInput + apiKey);
            City jsonResponse = getJsonResponseService.getJsonResponse(url);
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
            City jsonResponse = getJsonResponseService.getJsonResponse(url);
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}