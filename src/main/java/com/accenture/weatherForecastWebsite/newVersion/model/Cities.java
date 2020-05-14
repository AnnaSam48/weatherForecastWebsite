package com.accenture.weatherForecastWebsite.newVersion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@ApiModel(description = "City's weather forecast details")
@Entity
public class Cities implements Serializable {

    @ApiModelProperty(notes = "Unique identifier for location", required = true)
    @Id
    private String id;
    @ApiModelProperty(notes = "Name of the location",required = true)
    @Column
    private String cityName;
    @ApiModelProperty(notes = "Country code")
    @Column
    private String country;
    @ApiModelProperty(notes = "Temperature in location")
    @Column
    private double temp;
    @ApiModelProperty(notes = "Time of sunrise")
    @Column
    private String sunrise; //milisec
    @ApiModelProperty(notes = "Time of sunset")
    @Column
    private String sunset; //milisec

    @CreationTimestamp
    @JsonIgnore
    private Timestamp timestamp;

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
