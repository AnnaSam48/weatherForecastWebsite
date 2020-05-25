package com.accenture.weatherForecast.version2.service;

import com.accenture.weatherForecastWebsite.version2.service.ForecastRestClient;
import org.springframework.web.reactive.function.client.WebClient;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ForecastRestClientTest {

    private String baseUrl = "http://localhost:8080/forecast";

    private WebClient webClient = WebClient.create(baseUrl);

    ForecastRestClient forecastRestClientImpl = new ForecastRestClient(webClient);

  /*  @Test
    void getCityByName() {
        String cityName = "Toronto";
        City toronto = forecastRestClient.retrieveForecastByCityName(cityName);
        assertEquals("Toronto", city.getName());
    }
*/
}
