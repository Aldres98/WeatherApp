package com.example.aldres.workingwithapis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aldres.workingwithapis.Api.Api;
import com.example.aldres.workingwithapis.models.WeatherData;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ImageView weatherIcon;
    private TextView temperatureField;
    private TextView cityNameField;
    private TextView weatherConditions;
    private  TextView forecastTime;
    private static final String WEATHER_API_KEY = "ab2fe8bd7faa988c55c3d9b13c21435a";
    private static final String WEATHER_API_BASEURL = "http://api.openweathermap.org/data/2.5/";
    private String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherIcon = findViewById(R.id.weather_icon);
        Context context = this;
        temperatureField = findViewById(R.id.temperature_field);
        cityNameField = findViewById(R.id.city_name);
        weatherConditions = findViewById(R.id.weather_conditions);
        forecastTime = findViewById(R.id.forecast_time);

        Intent intent = getIntent();
        String cityLat = intent.getStringExtra("cityLat");
        String cityLong = intent.getStringExtra("cityLong");
        cityName = intent.getStringExtra("cityName");
        System.out.println(cityLat + " " + cityLong);


        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WEATHER_API_BASEURL)
                .build();
        Api apiService = retrofit.create(Api.class);

        Observable<WeatherData> observable = apiService.getWeatherData(cityLat, cityLong,"metric", WEATHER_API_KEY);

                observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        Date currentTime = Calendar.getInstance().getTime();
                        String iconId = weatherData.getWeather().get(0).getIcon();
                        temperatureField.setText(weatherData.getMain().getTemp().toString() + " °C");
                        weatherConditions.setText(weatherData.getWeather().get(0).getDescription());
                        forecastTime.setText(currentTime.toString());
                        cityNameField.setText(cityName);
                        System.out.println(iconId);
                        Picasso.with(context)
                                .load("http://openweathermap.org/img/w/" + iconId + ".png")
                                .into(weatherIcon);
                    }
                });
    }
}






