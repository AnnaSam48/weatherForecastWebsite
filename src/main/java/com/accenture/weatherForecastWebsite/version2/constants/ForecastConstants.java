package com.accenture.weatherForecastWebsite.version2.constants;

public class ForecastConstants {

    //Media type expected  from API
    public static final String API_MEDIA_TYPE = "application/json";

    //Base url for external API
    public static final String OPENWEATHER_API_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";

    //Base url for our API
    public static final String WEATHER_FORECAST_API_BASE_URL = "http://localhost:8080/forecast";

    //Request prefixes
    public static final String REQUEST_BY_NAME = "q=";
    public static final String REQUEST_BY_ID = "id=";

    //This API name
    public static final String WEATHER_FORECAST_TEAM = "Weather Forecast Team";

    //Links for GET, POST, UPDATE requests
    public static final String CITY_FORECAST_BY_NAME = "/{cityName}";
    public static final String POST_CITY_FORECAST = "/{cityName}";
    public static final String UPDATE_FORECAST_DATA = "/{cityId}";
}
