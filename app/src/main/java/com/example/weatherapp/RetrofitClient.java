package com.example.weatherapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Base URL của OpenWeatherMap API
    private static final String BASE_URL = "https://api.weatherapi.com/v1/";

    // Instance của Retrofit (Singleton Pattern)
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //
                    .addConverterFactory(GsonConverterFactory.create()) //
                    .build();
        }
        return retrofit;
    }
}
