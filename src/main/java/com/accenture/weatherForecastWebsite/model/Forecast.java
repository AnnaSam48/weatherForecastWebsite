package com.accenture.weatherForecastWebsite.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Forecast implements Serializable {
    @Id
    @Column (length = 12)
    private String id;
    private String name;

}
