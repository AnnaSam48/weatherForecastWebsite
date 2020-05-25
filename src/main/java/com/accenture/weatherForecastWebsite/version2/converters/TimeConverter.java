package com.accenture.weatherForecastWebsite.version2.converters;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class TimeConverter {
    public String getTimeFormUnx(int timeUnixRaw) {

        Calendar localTime = Calendar.getInstance();
        long localTimeUnix = (localTime.getTimeZone().getOffset(localTime.getTimeInMillis()) / 1000L); //change for local time zone
        long timeUnix = timeUnixRaw - localTimeUnix;

        Date timeRaw = new java.util.Date(timeUnix * 1000L);
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("HH:mm");
        String timeConverted = simpleDateFormat.format(timeRaw);
        return timeConverted;
    }


    public Timestamp timeHourAgo() {
        Timestamp hourOld = new Timestamp((System.currentTimeMillis() - (60 * 60 * 1000)));
        return hourOld;
    }


}

