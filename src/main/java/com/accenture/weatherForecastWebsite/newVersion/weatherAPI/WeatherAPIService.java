package com.accenture.weatherForecastWebsite.newVersion.weatherAPI;

import com.accenture.weatherForecastWebsite.newVersion.model.Cities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class WeatherAPIService {

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

/* //TODO for user location class
    private String latitude = "lat="; //prefix before latitude
    private double latNumbers = 0.0; //need to be updated for actual latitude
    private String longitude = "&lon="; //prefix before longitude;
    private double lonNumbers = 0.0;//need to be updated for actual latitude


    private String userLocation = latitude + String.format("%12.3f", latNumbers) + longitude + String.format("%12.3f", lonNumbers);    //lat={lat}&lon={lon}
   //TODO to string probably in place of this^^^
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

       /* StringBuilder content = new StringBuilder();
        return bufferedReader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
//TODO put in try cycle for   bufferedReader.close();!!!

        */
    }

    @Cacheable(value = "cityId", key = "#cityId")
    public Cities getForecastByCityID(String cityID) {
        try {
            URL url = new URL(requestUrlBegin + prefixCityId + cityID + apiKey); //request link here needs to be different
            String JsonResponse = getJsonResponse(url);

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode node = objectMapper.readValue(JsonResponse, JsonNode.class);
            JsonNode idNode = node.get("id");
            String id = idNode.asText();

            JsonNode cityNameNode = node.get("name");
            String cityName = cityNameNode.asText();

            JsonNode child = node.get("sys");
            JsonNode childCountry = child.get("country");
            String country = childCountry.asText();

            JsonNode childSunrise = child.get("sunrise");
            Long sunriseUnix = childSunrise.asLong();
            Date sunriseRaw = new java.util.Date(sunriseUnix * 1000L);
            SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("HH:mm");
            String sunrise = simpleDateFormat.format(sunriseRaw);

            JsonNode childSunset = child.get("sunset");
            Long sunsetUnix = childSunset.asLong();
            Date sunsetRaw = new java.util.Date(sunsetUnix * 1000L);
            String sunset = simpleDateFormat.format(sunsetRaw);

            JsonNode childMain = node.get("main");
            JsonNode childMainTemp = childMain.get("temp");
            double tempK = childMainTemp.asDouble();

            double tempC = tempK - 273.15; //we get it in Kelvins, so we need to change it celsius
            NumberFormat formatter = new DecimalFormat("#0.0");
            String tempFormatted = formatter.format(tempC);
            double temp = Double.parseDouble(tempFormatted);


            Cities forecast = new Cities(id, cityName, country, temp, sunrise, sunset);


            return forecast;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    @Cacheable(value = "city", key = "#userInput")
    public Cities getForecastByCity(String userInput) {

        String requestedLocation = prepareLocationName(userInput);
        try {

            URL url = new URL(requestUrlBegin + prefixName + requestedLocation + apiKey);
            String JsonResponse = getJsonResponse(url);

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode node = objectMapper.readValue(JsonResponse, JsonNode.class);
            JsonNode idNode = node.get("id");
            String id = idNode.asText();

            JsonNode cityNameNode = node.get("name");
            String cityName = cityNameNode.asText();

            JsonNode child = node.get("sys");
            JsonNode childCountry = child.get("country");
            String country = null;
            if (childCountry == null) {
                country = "No one owns it!";
            } else {
                country = childCountry.asText();
            }


            JsonNode childSunrise = child.get("sunrise");
            Long sunriseUnix = childSunrise.asLong();
            Date sunriseRaw = new java.util.Date(sunriseUnix * 1000L);
            SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("HH:mm");
            String sunrise = simpleDateFormat.format(sunriseRaw);

            JsonNode childSunset = child.get("sunset");
            Long sunsetUnix = childSunset.asLong();
            Date sunsetRaw = new java.util.Date(sunsetUnix * 1000L);
            String sunset = simpleDateFormat.format(sunsetRaw);

            JsonNode childMain = node.get("main");
            JsonNode childMainTemp = childMain.get("temp");
            double tempK = childMainTemp.asDouble();

            double tempC = tempK - 273.15; //we get it in Kelvins, so we need to change it celsius
            NumberFormat formatter = new DecimalFormat("#0.0");
            String tempFormatted = formatter.format(tempC);
            double temp = Double.parseDouble(tempFormatted);


            Cities forecast = new Cities(id, cityName, country, temp, sunrise, sunset);


            return forecast;


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();

        }

    }
    //TODO          Make classes for conversions date/Kelvin and all the rest ones
    //TODO          Make fields that are identical as one


}
