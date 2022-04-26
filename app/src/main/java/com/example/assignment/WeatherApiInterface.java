package com.example.assignment;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherApiInterface {

    // https://api.openweathermap.org/data/2.5/weather?lat=-37.813629&lon=144.963058&appid=80273ca2896861a72eca02c8f231e796

    @GET("weather?lat=-37.924351448611155&lon=145.1283415904359&appid=80273ca2896861a72eca02c8f231e796")
    Call<Root> getWeather();
}
