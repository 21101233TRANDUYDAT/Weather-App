package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

    // Tên thành phố
    @SerializedName("name")
    private String name;

    // Nhiệt độ và độ ẩm
    @SerializedName("main")
    private Main main;

    // Mô tả thời tiết (clear sky, rain, etc.)
    @SerializedName("weather")
    private List<Weather> weather;

    // Getter cho name
    public String getName() {
        return name;
    }

    // Getter cho main
    public Main getMain() {
        return main;
    }

    // Getter cho weather
    public List<Weather> getWeather() {
        return weather;
    }

    // Lớp con để chứa dữ liệu trong "main"
    public static class Main {
        @SerializedName("temp")
        private double temp; // Nhiệt độ

        @SerializedName("humidity")
        private int humidity; // Độ ẩm

        // Getter cho temp
        public double getTemp() {
            return temp;
        }

        // Getter cho humidity
        public int getHumidity() {
            return humidity;
        }
    }

    // Lớp con để chứa dữ liệu trong "weather"
    public static class Weather {
        @SerializedName("description")
        private String description; // Mô tả thời tiết

        @SerializedName("icon")
        private String icon; // Mã biểu tượng thời tiết

        // Getter cho description
        public String getDescription() {
            return description;
        }

        // Getter cho icon
        public String getIcon() {
            return icon;
        }
    }
}
