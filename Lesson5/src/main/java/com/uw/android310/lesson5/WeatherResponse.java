package com.uw.android310.lesson5;


import java.util.List;

public class WeatherResponse {
    private List<Weather> weather;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
