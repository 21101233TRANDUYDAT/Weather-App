package com.example.weatherapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Base URL của OpenWeatherMap API
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    // Instance của Retrofit (Singleton Pattern)
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // URL cơ sở của API
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON thành đối tượng Java
                    .build();
        }
        return retrofit;
    }
}
