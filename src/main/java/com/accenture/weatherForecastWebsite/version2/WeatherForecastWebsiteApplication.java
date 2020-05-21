package com.accenture.weatherForecastWebsite.version2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication
public class WeatherForecastWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherForecastWebsiteApplication.class, args);
    }

    @Bean
    WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
}
