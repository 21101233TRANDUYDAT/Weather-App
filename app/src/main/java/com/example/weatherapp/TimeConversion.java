package com.example.weatherapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConversion {
    public static String convertToReadableTime(String dateStr) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("h a");
        return dateTime.format(outputFormatter);
    }
}
