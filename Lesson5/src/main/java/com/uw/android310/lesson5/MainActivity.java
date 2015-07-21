package com.uw.android310.lesson5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherRestClient.get().getWeather("Seattle", new Callback<WeatherResponse>() {

            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                Log.i("App", weatherResponse.getMain().toString());
            }

            @Override
            public void failure(RetrofitError error) {
                // something went wrong
            }
        });
    }
}
