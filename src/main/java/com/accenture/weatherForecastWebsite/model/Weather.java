package com.accenture.weatherForecastWebsite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Embeddable;


@Embeddable
public class Weather {

    @SerializedName(value ="id", alternate ="descriptionID")
    @Expose
    private long descriptionID;
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;

    public long getDescriptionID() {
        return descriptionID;
    }

    public void setDescriptionID(long descriptionID) {
        this.descriptionID = descriptionID;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
