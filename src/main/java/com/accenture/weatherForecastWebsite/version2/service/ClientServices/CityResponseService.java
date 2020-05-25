package com.accenture.weatherForecastWebsite.version2.service.ClientServices;

import com.accenture.weatherForecastWebsite.version2.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import static com.accenture.weatherForecastWebsite.version2.constants.ForecastConstants.*;

@Service
public class CityResponseService implements CityClientService {

    @Autowired
    ForecastRestClient forecastRestClient;

    private WebClient webClient;

    public CityResponseService() {
        this.webClient = WebClient.builder()
                .baseUrl(WEATHER_FORECAST_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, API_MEDIA_TYPE)
                .defaultHeader(HttpHeaders.USER_AGENT,WEATHER_FORECAST_TEAM)
                .build();
    }


    @Override
    public City setForecast(String cityName) {
        return forecastRestClient.saveCityForecast(cityName);
    }

    @Override
    public City updateForecast(String cityId) {
        return forecastRestClient.updateForecastData(cityId);
    }


}
