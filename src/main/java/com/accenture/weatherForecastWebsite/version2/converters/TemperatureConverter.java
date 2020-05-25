package com.accenture.weatherForecastWebsite.version2.converters;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;


@Component
public class TemperatureConverter {
    public double getCelsiusFormKelvin(double temperatureKelvin) {
        double constantForConversion = -273.15;
        double temperatureInCelsius = temperatureKelvin + constantForConversion;
        NumberFormat formatter = new DecimalFormat("#0.0");
        String temperatureFormatted = formatter.format(temperatureInCelsius);
        double celsiusTemperature = Double.parseDouble(temperatureFormatted);
        return celsiusTemperature;
    }


}
