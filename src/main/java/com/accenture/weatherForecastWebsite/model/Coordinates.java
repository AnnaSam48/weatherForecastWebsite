package com.accenture.weatherForecastWebsite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;


@Embeddable
public class Coordinates {
    @SerializedName("lon")
    @Expose
    private long lon;
    @SerializedName("lat")
    @Expose
    private long lat;

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

}
