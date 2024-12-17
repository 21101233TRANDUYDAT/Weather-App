package com.example.weatherapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ImageView btnSearch;

    ImageView icon;
    EditText edtQuery;
    TextView tvName;
    TextView tvTem;
    RecyclerView recyclerView;

    TextView astro;
    WeatherApiService apiService;
    AstroAdapter astroAdapter;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            getCurrentLocation();
        }

        edtQuery = findViewById(R.id.etSearchCity);
        btnSearch = findViewById(R.id.btnSearch);
        tvName = findViewById(R.id.tvCity);
        tvTem = findViewById(R.id.tvTemperature);
        icon = findViewById(R.id.weatherIcon);
        astro = findViewById(R.id.txt_status);
        recyclerView = findViewById(R.id.rcy_astro);
        astroAdapter = new AstroAdapter(this);
        recyclerView.setAdapter(astroAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        btnSearch.setOnClickListener((v) -> {
            search();
        });
        apiService = RetrofitClient.getInstance().create(WeatherApiService.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied. Unable to fetch location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No permission for this action", Toast.LENGTH_SHORT).show();
            return;
        }
        fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                new CancellationToken() {
                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }

                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(OnTokenCanceledListener onTokenCanceledListener) {
                        return this;
                    }
                }
        ).addOnSuccessListener(this, location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                callApi(String.format("%s,%s", latitude, longitude));
            } else {
                Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to get location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public void callApi(String q) {
        apiService.getWeatherByCity(WeatherApiService.API_KEY, q, 1).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    // bind data
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        bindData(weatherResponse);
                    }
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void bindData(WeatherResponse response) {
        tvName.setText(response.getLocation().getName());
        tvTem.setText(response.getCurrent().getTemp_c() + "°");
        Glide.with(MainActivity.this).load(response.getCurrent().getCondition().getIcon().replace("//", "https://")).into(icon);
        // update recycle view
        astro.setText(response.getCurrent().getCondition().getText());
        astroAdapter.updateData(response.getForecast().getForecastday().get(0).getHours());
    }

    public void search() {
        String query = String.valueOf(edtQuery.getText());
        if (query.trim().equals("")) {
            return;
        }
        callApi(query);
    }
}
