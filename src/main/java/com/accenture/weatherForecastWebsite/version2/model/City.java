package com.accenture.weatherForecastWebsite.version2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@ApiModel(description = "City's weather forecast details")
@Data
@JsonInclude
@Entity
public class City implements Serializable {
    //TODO needed conversion of sunset, rise, temperature. The time zone should be invisible for user.

    @ApiModelProperty(notes = "Unique identifier for location", required = true)
    @Id
    private String id;

    @ApiModelProperty(notes = "Name of the location", required = true)
    @Column
    @JsonProperty("name")
    private String cityName;

    @ApiModelProperty(notes = "Country code")
    @Column
    private String country;


    @ApiModelProperty(notes = "Temperature in location")
    @Column
    private double temp;

    @ApiModelProperty(notes = "Time of sunrise")
    @Column
    private int sunrise; //milisec

    @ApiModelProperty(notes = "Time of sunset")
    @Column
    private int sunset; //milisec


    @CreationTimestamp
    @JsonIgnore
    private Timestamp timestamp;


    @JsonProperty("sys")
    public void unpackNestedCountry(Map<String, Object> sys) {
        this.country = (String) sys.get("country");
        this.sunrise = (int) sys.get("sunrise");
        this.sunset = (int) sys.get("sunset");
    }



    @JsonProperty("main")
    public void unpackNestedMain(Map<String, Object> main) {
        this.temp = (double) main.get("temp");
    }

    @JsonProperty("timezone")
    private int timeZoneOfCountry;


    public City(String id, String cityName, String country, double temp, int sunrise, int sunset) {
        this.id = id;
        this.cityName = cityName;
        this.country = country;
        this.temp = temp;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public City() {
    }


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

    public int getSunrise() {
        int sunriseRaw=sunrise+timeZoneOfCountry;
        return sunrise;
    }


    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        int sunsetRaw=sunset+timeZoneOfCountry;
        return sunsetRaw;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }
}
