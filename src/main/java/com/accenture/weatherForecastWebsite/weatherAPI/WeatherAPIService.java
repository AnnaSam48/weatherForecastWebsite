package com.accenture.weatherForecastWebsite.weatherAPI;

import org.springframework.beans.factory.annotation.Value;
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


}
