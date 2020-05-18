package com.accenture.weatherForecastWebsite.newVersion.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ForecastNotFoundException extends RuntimeException {
    public ForecastNotFoundException() {
        super();
    }

    public ForecastNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForecastNotFoundException (String message) {
        super(message);
    }

    public ForecastNotFoundException(Throwable cause) {
        super(cause);
    }
}

