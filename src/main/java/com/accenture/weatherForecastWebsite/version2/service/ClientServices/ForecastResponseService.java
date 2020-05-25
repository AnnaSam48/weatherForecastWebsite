package com.accenture.weatherForecastWebsite.version2.service.ClientServices;

import com.accenture.weatherForecastWebsite.version2.model.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import static com.accenture.weatherForecastWebsite.version2.constants.ForecastConstants.*;

public class ForecastResponseService implements ForecastClientService {

    @Autowired
    ForecastRestClient forecastRestClient;

    private final WebClient webClient;

    public ForecastResponseService() {
        this.webClient = WebClient.builder()
                .baseUrl(WEATHER_FORECAST_API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE,API_MEDIA_TYPE)
                .defaultHeader(HttpHeaders.USER_AGENT,WEATHER_FORECAST_TEAM)
                .build();
    }

    public Forecast getForecast(String cityName) {

        return forecastRestClient.retrieveForecastByCityName(cityName) ;
    }
}
