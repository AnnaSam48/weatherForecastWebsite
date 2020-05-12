package com.accenture.weatherForecastWebsite.weatherAPI;

import com.accenture.weatherForecastWebsite.model.Forecast;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class WeatherAPIServiceOld {

    @Value("${weather.api.request}") //beginning of API request link
    private String requestUrlBegin;

    @Value("${weather.api.key}") //You have to have API key at the end of link
    private String apiKey;

    @Value("${weather.api.request.by.name}") //if user input is name, used right after requestUrlBegin
    private String prefixName;

    @Value("${weather.api.request.by.cityId}") //if we search in database, used right after requestUrlBegin
    private String prefixCityId;

    @Value("${weather.api.request.metric}") //example  api.openweathermap.org/data/2.5/find?q=London&units=metric
    private String useMetric;

/* for user location class
    private String latitude = "lat="; //prefix before latitude
    private double latNumbers = 0.0; //need to be updated for actual latitude
    private String longitude = "&lon="; //prefix before longitude;
    private double lonNumbers = 0.0;//need to be updated for actual latitude


    private String userLocation = latitude + String.format("%12.3f", latNumbers) + longitude + String.format("%12.3f", lonNumbers);    //lat={lat}&lon={lon}
    to string probably in place of this^^^
*/

    private String prepareLocationName(String locationName) {
        locationName = locationName.trim();
        String modifiedLocation = locationName.replaceAll(" ", "%20");
        return modifiedLocation;
    }

    private String getJsonResponse(URL url) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(3000);
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        String JsonResponse = stringBuilder.toString();
        bufferedReader.close();

        return JsonResponse;
    }

    public Forecast getForecastByCityID(String cityID) {
        try {
            URL url = new URL(requestUrlBegin +prefixCityId+ cityID + apiKey); //request link here needs to be different
            String JsonResponse = getJsonResponse(url);
            Gson gson = new Gson();
            Forecast forecast = gson.fromJson(JsonResponse, Forecast.class);
            return forecast;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public Forecast getForecastByCity(String userInput) {

        String requestedLocation = prepareLocationName(userInput);
        try {
            URL url = new URL(requestUrlBegin + prefixName+ requestedLocation + apiKey);
            String JsonResponse = getJsonResponse(url);
            Gson gson = new Gson();
            Forecast forecast = gson.fromJson(JsonResponse, Forecast.class);
            return forecast;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();

        }

    }

    //Constant till we get answer what exactly we need to be in CRON job
    public Forecast getForecastForRiga() {
        try {
            URL url = new URL(requestUrlBegin + prefixName+ "Riga" + apiKey);
            String JsonResponse = getJsonResponse(url);
            Gson gson = new Gson();
            Forecast forecast = gson.fromJson(JsonResponse, Forecast.class);
            return forecast;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


}
