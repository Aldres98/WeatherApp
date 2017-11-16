package com.example.aldres.workingwithapis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.aldres.workingwithapis.Api.Api;
import com.example.aldres.workingwithapis.models.WeatherData;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView middleText;
    private ImageView weatherIcon;
    private static final String WEATHER_API_KEY = "ab2fe8bd7faa988c55c3d9b13c21435a";
    private static final String WEATHER_API_BASEURL = "http://api.openweathermap.org/data/2.5/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        middleText = findViewById(R.id.middleText);
        weatherIcon = findViewById(R.id.weatherView);

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WEATHER_API_BASEURL)
                .build();
        Api apiService = retrofit.create(Api.class);

        Observable<WeatherData> observable = apiService.getWeatherData("London", "metric", WEATHER_API_KEY);

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
                        middleText.setText(R.string.temperatureNow + String.valueOf(weatherData.getMain().getTemp()));
                        weatherIcon.setImageResource(R.drawable.weather_clear);
                    }
                });
    }
}






