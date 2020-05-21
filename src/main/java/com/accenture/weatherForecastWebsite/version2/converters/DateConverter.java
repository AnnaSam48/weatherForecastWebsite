package com.accenture.weatherForecastWebsite.version2.converters;


import java.text.SimpleDateFormat;
import java.util.Date;


public class DateConverter {
    public String getDateFormUnx(int dateUnixRaw) {
        //TODO
        // dateUnixRaw-userlocationTimeZone unix as int
        // and then convert to date (remove hardcoded riga timezone)
        Date dateRaw = new java.util.Date(dateUnixRaw * 1000L);
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("HH:mm");
        String dateConverted = simpleDateFormat.format(dateRaw);
        return dateConverted;
    }
}
