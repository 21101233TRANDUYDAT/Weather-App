package com.example.weatherapp;

import com.example.weatherapp.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    public static String API_KEY = "e03cf64593554a11afe102046241612";

    @GET("forecast.json")
    Call<WeatherResponse> getWeatherByCity(
            @Query("key") String apiKey,
            @Query("q") String cityName,
            @Query("days") int day
    );
}
