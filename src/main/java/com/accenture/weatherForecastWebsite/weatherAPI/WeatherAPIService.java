package com.accenture.weatherForecastWebsite.weatherAPI;

import com.accenture.weatherForecastWebsite.model.Forecast;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.web.JsonPath;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class WeatherAPIService {
    @Value("${weather.api.request}")
    private String requestUrlBegin;

    @Value("${weather.api.request.riga}")
    private String locationRiga;

    @Value("${weather.api.key}")
    private String apiKey;

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

    private String getLocation(String userInput){

        return userInput;
    }

    public Forecast getForecastByID(String idLocation){
        try {
            URL url = new URL(requestUrlBegin+idLocation+apiKey);
            String JsonResponse = getJsonResponse(url);
            Gson gson = new Gson();
            Forecast forecast = gson.fromJson(JsonResponse, Forecast.class);
            return forecast;

        }catch (Exception e){
            throw new RuntimeException();
        }

    }
    public Forecast getForecastForRiga(){
        try {
            URL url = new URL(requestUrlBegin+locationRiga+apiKey);
            String JsonResponse = getJsonResponse(url);
            Gson gson = new Gson();
            Forecast forecast = gson.fromJson(JsonResponse, Forecast.class);
            return forecast;

        }catch (Exception e){
            throw new RuntimeException();
        }

    }


}
