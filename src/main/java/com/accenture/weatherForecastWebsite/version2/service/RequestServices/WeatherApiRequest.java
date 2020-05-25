
package com.accenture.weatherForecastWebsite.version2.service.RequestServices;

import com.accenture.weatherForecastWebsite.version2.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;

@Component
public class WeatherApiRequest {


    @Autowired
    WebClient.Builder webClientBuilder;

    public City getCityInformation(URL url) {
        try {

             return webClientBuilder.build()
                    .get()
                    .uri(""+url)
                    .retrieve()
                    .bodyToMono(City.class)
                    .block();


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}



