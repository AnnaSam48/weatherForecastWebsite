package com.accenture.weatherForecastWebsite.version2.ApiService;


import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.service.GetJsonResponseService;
import com.accenture.weatherForecastWebsite.version2.service.PrepareLocationInput;
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

    @Autowired
    GetJsonResponseService getJsonResponseService;

    @Autowired
    PrepareLocationInput prepareLocationInput;


    @Cacheable(value = "city", key = "#userInput")
    public City getForecastByCity(String userInput) {

        String requestedLocation = prepareLocationInput.prepareLocationName(userInput);
        try {
            URL url = new URL(requestUrlBegin + prefixName + requestedLocation + apiKey);
            City jsonResponse = getJsonResponseService.getJsonResponse(url);
            return jsonResponse;
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}