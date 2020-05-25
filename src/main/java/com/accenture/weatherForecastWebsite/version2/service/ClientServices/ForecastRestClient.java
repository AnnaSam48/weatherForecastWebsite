package com.accenture.weatherForecastWebsite.version2.service.ClientServices;

import com.accenture.weatherForecastWebsite.version2.model.City;
import com.accenture.weatherForecastWebsite.version2.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.accenture.weatherForecastWebsite.version2.constants.ForecastConstants.*;

@Component
public class ForecastRestClient {

    @Autowired
    WebClient.Builder webClientBuilder;

    public ForecastRestClient() {

    }

    public Forecast retrieveForecastByCityName(String cityName) {
          return  webClientBuilder.build()
                .get()
                .uri(CITY_FORECAST_BY_NAME, cityName)
                .retrieve()
                .bodyToMono(Forecast.class)
                .block();

    }

    public City updateForecastData(String cityId) {
        return webClientBuilder.build()
                .put()
                .uri(UPDATE_FORECAST_DATA, cityId)
                .retrieve()
                .bodyToMono(City.class)
                .block();
    }

    public City saveCityForecast(String cityName) {
        return webClientBuilder.build()
                .post()
                .uri(POST_CITY_FORECAST, cityName)
                .retrieve()
                .bodyToMono(City.class)
                .block();
    }

}
