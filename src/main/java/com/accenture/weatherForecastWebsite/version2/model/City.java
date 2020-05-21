package com.accenture.weatherForecastWebsite.version2.model;

import com.accenture.weatherForecastWebsite.version2.converters.DateConverter;
import com.accenture.weatherForecastWebsite.version2.converters.TemperatureConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

@ApiModel(description = "City's weather forecast details")
@Data
@JsonInclude
@Entity
public class City implements Serializable {

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
    private double temperature;

    @ApiModelProperty(notes = "Time of sunrise")
    @Column
    private String sunrise;
    @ApiModelProperty(notes = "Time of sunset")
    @Column
    private String sunset;


    //fields not showed by json

    @CreationTimestamp
    @JsonIgnore
    private Timestamp timestamp;


    //fields not stored in database
    @Transient
    @JsonIgnore
    private int timeZone; //UNIX time shift

    @Transient
    @JsonIgnore
    private int sunriseOfTheLocation; //UNIX

    @Transient
    @JsonIgnore
    private int sunSetOfTheLocation; //UNIX

    @Transient
    @JsonIgnore
    private double temp; //temperature in K raw


    //Unpacking from json, for DB
    @JsonProperty("sys")
    public void unpackNestedCountry(Map<String, Object> sys) {
        this.country = (String) sys.get("country");
        this.sunriseOfTheLocation = (int) sys.get("sunrise");
        this.sunSetOfTheLocation = (int) sys.get("sunset");
    }


    @JsonProperty("timezone")
    public void getTimeZoneOfCountry(Integer timezone) {
        this.timeZone = timezone;
    }


    @JsonProperty("main")
    public void unpackNestedMain(Map<String, Object> main) {
        this.temp = (double) main.get("temp");
    }


    //  Get, set constructor

    public City(String id, String cityName, String country, double temperature, String sunrise, String sunset) {
        this.id = id;
        this.cityName = cityName;
        this.country = country;
        this.temperature= temperature;
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

    public double getTemperature() {
        TemperatureConverter temperatureConverter=new TemperatureConverter();

        double temperature= temperatureConverter.getCelsiusFormKelvin(temp);
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temp;
    }

    public String getSunrise() {
        DateConverter dateConverter = new DateConverter();
        sunrise = dateConverter.getDateFormUnx(sunriseOfTheLocation+timeZone-10800);
        return sunrise;
    }


    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        DateConverter dateConverter = new DateConverter();
        sunset = dateConverter.getDateFormUnx(sunSetOfTheLocation+timeZone-10800);
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
