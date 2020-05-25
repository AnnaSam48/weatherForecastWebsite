package com.accenture.weatherForecastWebsite.version2.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
@Component
@ApiModel(description = "City's weather forecast details")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @ApiModelProperty(notes= "Time data accessed")
    @CreationTimestamp
    @Column
    private Timestamp timestamp;

    @ApiModelProperty(notes = "Time shift UNIX to UTC")
    @Column
    int timeZone; //UNIX time shift UTC

    @ApiModelProperty(notes = "Time of sunrise")
    @Column
    public int sunriseOfTheLocation; //UNIX UTC

    @ApiModelProperty(notes = "Time of sunset")
    @Column
    public int sunSetOfTheLocation; //UNIX UTC

    @ApiModelProperty(notes = "Temperature in Kelvins")
    @Column
    public double temp; //temperature in K raw

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
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public int getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }
    public int getSunriseOfTheLocation() {
        return sunriseOfTheLocation;
    }
    public void setSunriseOfTheLocation(int sunriseOfTheLocation) {
        this.sunriseOfTheLocation = sunriseOfTheLocation;
    }
    public int getSunSetOfTheLocation() {
        return sunSetOfTheLocation;
    }
    public void setSunSetOfTheLocation(int sunSetOfTheLocation) {
        this.sunSetOfTheLocation = sunSetOfTheLocation;
    }
    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
}
