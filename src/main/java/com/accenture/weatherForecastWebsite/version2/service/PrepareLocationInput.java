package com.accenture.weatherForecastWebsite.version2.service;

import org.springframework.stereotype.Component;

@Component
public class PrepareLocationInput {

    public String prepareLocationName(String locationName) {
        locationName = locationName.trim();
        String modifiedLocation = locationName.replaceAll(" ", "%20");
        return modifiedLocation;
    }
}
