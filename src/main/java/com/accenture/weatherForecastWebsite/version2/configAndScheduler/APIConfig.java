package com.accenture.weatherForecastWebsite.version2.configAndScheduler;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
@EnableCaching
public class APIConfig {

    //Web Client builder
    @Bean
    WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }

    //Swagger documentation configuration
    @Bean
    public Docket swaggerConfiguration(){

        //Returns prepared Docket instance
        return new Docket(DocumentationType.SWAGGER_2)
                .select().paths(PathSelectors.ant("/forecast/*"))
                .apis(RequestHandlerSelectors.basePackage("com.accenture.weatherForecastWebsite"))
                .build()
                .apiInfo(weatherAPIDetails());
    }


    //Customized info about API in swagger file
    private ApiInfo weatherAPIDetails(){
        return new ApiInfo("Weather Forecast API",
                "Simple Weather forecast API, that lets user check the current weather " +
                        "forecast for the city user wants to know about.",
                "2.0",
                "Free to use",
                new springfox.documentation.service.Contact("Weather Forecast Team",
                        "http://weatherAtAwsomePlace.com","OurSuperE@mail.com"),
                "WeatherForecastAPI Licence",
                "http://weatherAtAwesomePlace.com",
                Collections.emptyList());
    }
}
