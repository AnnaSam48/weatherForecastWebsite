package com.accenture.weatherForecastWebsite.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class WeatherConverter implements AttributeConverter<Weather, String> {

    private static final String SEPARATOR = "--";

    @Override
    public String convertToDatabaseColumn(Weather weather) {
        if (weather == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        long weatherId = weather.getDescriptionID();
        String id = stringBuilder.append(weatherId).toString();
        stringBuilder.append(SEPARATOR);

        if (weather.getMain() != null && !weather.getMain().isEmpty()) {
            stringBuilder.append(weather.getMain());
            stringBuilder.append(SEPARATOR);
        }

        if (weather.getDescription() != null && !weather.getDescription().isEmpty()) {
            stringBuilder.append(weather.getDescription());
            stringBuilder.append(SEPARATOR);
        }

        if (weather.getIcon() != null && weather.getIcon().isEmpty()) {
            stringBuilder.append(weather.getIcon());

        }

        return stringBuilder.toString();
    }

    @Override
    public Weather convertToEntityAttribute(String dbWeather) {
        if (dbWeather == null || dbWeather.isEmpty()) {
            return null;
        }

        String[] partsOfWeather = dbWeather.split(SEPARATOR);

        if (partsOfWeather == null || partsOfWeather.length == 0) {
            return null;
        }
        Weather weather = new Weather();

        String idPart = !partsOfWeather[0].isEmpty() ? partsOfWeather[0] : null;
        String mainPart = !partsOfWeather[1].isEmpty() ? partsOfWeather[1] : " ";
        String descriptionPart = !partsOfWeather[2].isEmpty() ? partsOfWeather[2] : " ";
        String iconPart = !partsOfWeather[3].isEmpty() ? partsOfWeather[3] : " ";

        weather.setDescriptionID(Long.parseLong(idPart));
        weather.setMain(mainPart);
        weather.setDescription(descriptionPart);
        weather.setIcon(iconPart);

        return weather;
    }
}
