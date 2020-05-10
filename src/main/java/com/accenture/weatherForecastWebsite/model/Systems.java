package com.accenture.weatherForecastWebsite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;


@Embeddable
public class Systems {
    @SerializedName("type")
    @Expose
    private long type;
    @SerializedName(value = "id", alternate="sysID")
    @Expose
    private long sysID;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("sunrise")
    @Expose
    private long sunrise;
    @SerializedName("sunset")
    @Expose
    private long sunset;

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getSysID() {
        return sysID;
    }

    public void setSysID(long sysID) {
        this.sysID = sysID;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

}
