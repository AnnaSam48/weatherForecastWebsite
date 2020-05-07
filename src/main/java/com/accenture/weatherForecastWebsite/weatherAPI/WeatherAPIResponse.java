package com.accenture.weatherForecastWebsite.weatherAPI;

import com.accenture.weatherForecastWebsite.model.Forecast;

import java.util.List;

public class WeatherAPIResponse {

    private List<Forecast> Search;

    private String Response;

    public List<Forecast> getSearch() {
        return Search;
    }
}
