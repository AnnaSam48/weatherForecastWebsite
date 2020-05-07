package com.accenture.weatherForecastWebsite.weatherAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherAPIService {
    @Value("${weather.api.request}")
    private String requestUrlBegin;

    @Value("${weather.api.request.riga}")
    private String locationRiga;

    @Value("${weather.api.key}")
    private String apiKey;

}
