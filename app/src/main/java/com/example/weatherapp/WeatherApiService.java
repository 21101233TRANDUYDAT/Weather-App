package com.example.weatherapp;

import com.example.weatherapp.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    // Endpoint để lấy thông tin thời tiết theo tên thành phố
    @GET("weather")
    Call<WeatherResponse> getWeatherByCity(
            @Query("q") String cityName,       // Tên thành phố
            @Query("appid") String apiKey,    // API Key
            @Query("units") String units      // Đơn vị nhiệt độ ("metric" cho °C)
    );
}
