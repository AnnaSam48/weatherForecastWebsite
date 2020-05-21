package com.accenture.weatherForecastWebsite.version2.service;

import com.accenture.weatherForecastWebsite.version2.model.City;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;

@Component
public class GetJsonResponseService {


    @Autowired
    WebClient.Builder webClientBuilder;

    public City getJsonResponse(URL url) {
        try {

           City newCity= webClientBuilder.build()
                    .get()
                    .uri(""+url)
                    .retrieve()
                    .bodyToMono(City.class)
                    .block();

            return newCity;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}


