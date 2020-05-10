package com.accenture.weatherForecastWebsite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;


@Embeddable
public class Clouds {
    @SerializedName("all")
    @Expose
    private long all;

    public long getAll() {
        return all;
    }

    public void setAll(long all) {
        this.all = all;
    }
}
