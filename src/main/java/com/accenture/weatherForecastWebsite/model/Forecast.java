package com.accenture.weatherForecastWebsite.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Forecast implements Serializable {
    @Id
    @Column (length = 12)
    private Long id;
    private String name;
    private int temperature; //delete afterwards, for test purposes while we have no data

    public Forecast(Long id, String name, int temperature) {
        this.id = id;
        this.name = name;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
