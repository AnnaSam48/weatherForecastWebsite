package com.accenture.weatherForecastWebsite.version2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

@Component
public class Forecast {

    @ApiModelProperty(notes = "Name of city")
    @JsonProperty("City")
    private String cityName;
    @ApiModelProperty(notes = "Country ")
    @JsonProperty("Country code")
    private String country;
    @ApiModelProperty(notes = "Temperature")
    @JsonProperty("Temperature")
    private String temperature;
    @ApiModelProperty(notes = "Time of sunrise")
    @JsonProperty("Sunrise")
    private String sunrise;
    @ApiModelProperty(notes = "Time of sunset")
    @JsonProperty("Sunset")
    private String sunset;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}