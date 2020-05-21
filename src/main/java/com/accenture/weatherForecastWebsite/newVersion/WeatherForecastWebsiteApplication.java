package com.accenture.weatherForecastWebsite.newVersion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
public class WeatherForecastWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherForecastWebsiteApplication.class, args);
    }
}
