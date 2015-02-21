package com.shermpay.yodaforecast;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by shermpay on 2/17/15.
 */
public interface Weatherman {
    public static final String ENDPOINT = "https://george-vustrey-weather.p.mashape.com";

    @Headers("X-Mashape-Key: ydKmPwoWlZmshIirpJmXOUBuZb5cp1RnQJAjsniLWQZEwasqjw")
    @GET("/api.php")
    List<WeatherForecast> report(@Query("location") String location);
}
