package com.accenture.weatherForecastWebsite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;


@Embeddable
public class Clouds {
    @SerializedName(value="all", alternate="allClouds")
    @Expose
    private long allClouds;

    public long getAll() {
        return allClouds;
    }

    public void setAll(long all) {
        this.allClouds = allClouds;
    }
}
