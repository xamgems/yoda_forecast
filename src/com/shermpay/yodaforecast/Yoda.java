package com.shermpay.yodaforecast;
import retrofit.Callback;
import retrofit.http.*;
import retrofit.client.Response;

public interface Yoda {
    public static final String ENDPOINT = "https://yoda.p.mashape.com";

    @Headers("X-Mashape-Key: ydKmPwoWlZmshIirpJmXOUBuZb5cp1RnQJAjsniLWQZEwasqjw")
    @GET("/yoda")
    String translate(@Query("sentence") String sentence);

    @Headers("X-Mashape-Key: ydKmPwoWlZmshIirpJmXOUBuZb5cp1RnQJAjsniLWQZEwasqjw")
    @GET("/yoda")
    void asyncTranslate(@Query("sentence") String sentence, Callback<Response> callback);
}
