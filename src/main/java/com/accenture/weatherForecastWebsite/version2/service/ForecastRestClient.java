package com.accenture.weatherForecastWebsite.version2.service;

import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.model.Forecast;
import org.springframework.web.reactive.function.client.WebClient;

import static com.accenture.weatherForecastWebsite.version2.constants.ForecastConstants.*;

public class ForecastRestClient {
    private WebClient webClient;

    public ForecastRestClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Forecast retrieveForecastByCityName(String cityName) {
        return webClient.get()
                .uri(CITY_FORECAST_BY_NAME, cityName)
                .retrieve()
                .bodyToMono(Forecast.class)
                .block();
    }

    public City updateForecastData(String cityId) {
        return webClient.put()
                .uri(UPDATE_FORECAST_DATA, cityId)
                .retrieve()
                .bodyToMono(City.class)
                .block();
    }

    public City saveCityForecast(String cityName) {
        return webClient.post()
                .uri(POST_CITY_FORECAST, cityName)
                .retrieve()
                .bodyToMono(City.class)
                .block();
    }

}
