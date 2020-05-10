package com.accenture.weatherForecastWebsite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Forecast implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @SerializedName(value = "coord", alternate = "coordinates")
    @Expose
    private Coordinates coordinates;
    @SerializedName("weather")
    @Expose
    @Column(columnDefinition = "text", nullable = false)
    @Convert(converter = WeatherConverter.class)
    private List<Weather> weather = new ArrayList<>();
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName(value="main", alternate = "mainWeather")
    @Expose
    private MainWeather mainWeather;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName(value = "dt", alternate = "dateOnMachine")
    @Expose
    private long dateOnMachine;
    @SerializedName(value = "sys", alternate = "systems")
    @Expose
    private Systems systems;
    @SerializedName("timezone")
    @Expose
    private long timezone;
    @SerializedName(value = "id", alternate = "cityId")
    @Expose
    private long cityId;
    @SerializedName(value = "name", alternate = "cityName")
    @Expose
    private String cityName;
    @SerializedName("cod")
    @Expose
    private long cod;

    public Forecast() {
    }

    public Forecast(long id, Coordinates coordinates, List<Weather> weather, String base,
                    MainWeather mainWeather, Wind wind, Clouds clouds, long dateOnMachine,
                    Systems systems, long timezone, long cityId, String cityName, long cod) {
        this.id = id;
        this.coordinates = coordinates;
        this.weather = weather;
        this.base = base;
        this.mainWeather = mainWeather;
        this.wind = wind;
        this.clouds = clouds;
        this.dateOnMachine = dateOnMachine;
        this.systems = systems;
        this.timezone = timezone;
        this.cityId = cityId;
        this.cityName = cityName;
        this.cod = cod;
    }

    public long getfID() {
        return id;
    }

    public void setfID(long fID) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainWeather getMainWeather() {
        return mainWeather;
    }

    public void setMainWeather(MainWeather mainWeather) {
        this.mainWeather = mainWeather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDateOnMachine() {
        return dateOnMachine;
    }

    public void setDateOnMachine(long dateOnMachine) {
        this.dateOnMachine = dateOnMachine;
    }

    public Systems getSystems() {
        return systems;
    }

    public void setSystems(Systems systems) {
        this.systems = systems;
    }

    public long getTimezone() {
        return timezone;
    }

    public void setTimezone(long timezone) {
        this.timezone = timezone;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }
}
