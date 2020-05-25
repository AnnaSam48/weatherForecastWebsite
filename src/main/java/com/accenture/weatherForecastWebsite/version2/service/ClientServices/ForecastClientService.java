package com.accenture.weatherForecastWebsite.version2.service.ClientServices;

import com.accenture.weatherForecastWebsite.version2.model.Forecast;

public interface ForecastClientService {

     Forecast getForecast (String cityName);

}
