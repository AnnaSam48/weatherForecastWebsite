package com.accenture.weatherForecastWebsite.newVersion.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Cities implements Serializable {
    @Id
    private String id;
    @Column
    private String cityName;
    @Column
    private String country;
    @Column
    private double temp;
    @Column
    private String sunrise; //milisec
    @Column
    private String sunset; //milisec

    public Cities(String id, String cityName, String country, double temp, String sunrise, String sunset) {
        this.id = id;
        this.cityName = cityName;
        this.country = country;
        this.temp = temp;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
    public Cities(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
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
