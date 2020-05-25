package com.accenture.weatherForecastWebsite.version2.service.ClientServices;

import com.accenture.weatherForecastWebsite.version2.model.City;

public interface CityClientService {

     City setForecast(String cityName);
     City updateForecast(String cityId);
}
