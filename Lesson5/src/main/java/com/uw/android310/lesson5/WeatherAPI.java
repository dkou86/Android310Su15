package com.uw.android310.lesson5;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Defines the contract for accessing data from the OpenWeatherMap API.
 */
public interface WeatherAPI {

    /**
     * Get weather data for the specified city.
     *
     * @param cityName City to retrieve weather data for.
     * @param callback Callback to handle API response.
     */
    @GET("/weather")
    void getWeather(@Query("q") String cityName, Callback<WeatherResponse> callback);
}
