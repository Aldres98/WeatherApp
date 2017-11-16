package com.example.aldres.workingwithapis.Api;

import com.example.aldres.workingwithapis.models.WeatherData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface Api {
    @GET("weather?")
    Observable<WeatherData> getWeatherData(@Query("q") String city, @Query("units") String unitsFormat,@Query("APPID") String apiKey);}
