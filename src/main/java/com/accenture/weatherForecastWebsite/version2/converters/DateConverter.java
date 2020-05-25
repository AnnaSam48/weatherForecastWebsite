package com.accenture.weatherForecastWebsite.version2.converters;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateConverter {
    public String getDateFormUnx(int timeUnixRaw) {

        Calendar localTime = Calendar.getInstance();
        long localTimeUnix = (localTime.getTimeZone().getOffset(localTime.getTimeInMillis()) / 1000L); //change for local time zone
        long timeUnix=timeUnixRaw-localTimeUnix;

        Date dateRaw = new java.util.Date(timeUnix * 1000L);
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("HH:mm");
        String dateConverted = simpleDateFormat.format(dateRaw);
        return dateConverted;
    }
}
