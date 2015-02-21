package com.shermpay.yodaforecast;
import java.util.*;
import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;

public class Client {
    private static final long LOADING = 200l;
    private static final String LOCATION = "Seattle";

    public static void main(String[] args) {
        final BlockingQueue<String> channel = new SynchronousQueue<String>();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        RestAdapter weatherAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(Weatherman.ENDPOINT)
                .build();

        Weatherman weatherman = weatherAdapter.create(Weatherman.class);

        System.out.println("Weather - " + LOCATION);
        List<WeatherForecast> forecast = weatherman.report(LOCATION);

        RestAdapter yodaAdapter = new RestAdapter.Builder()
                .setEndpoint(Yoda.ENDPOINT)
                .build();
        Yoda yoda = yodaAdapter.create(Yoda.class);


        for (WeatherForecast dayForecast : forecast) {
            String sentence = dayForecast.forecastString();
            yoda.asyncTranslate(sentence, new Callback<Response>() {
                @Override
                public void success(Response yodaResponse, Response response) {
                    try {
                        TypedInput body = yodaResponse.getBody();
                        channel.put(typedInputString(body));
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    switch (retrofitError.getKind()) {
                        case NETWORK:
                            System.err.println("Network Error occured while contacting " + retrofitError.getUrl());
                            break;
                        case HTTP:
                            System.err.println("HTTP Request error: " + retrofitError.getResponse().getStatus() +
                                    " while contacting " + retrofitError.getUrl());
                            break;
                        case CONVERSION:
                            System.err.println("Unable to convert response body into json. \nResponse: " +
                                    typedInputString(retrofitError.getResponse().getBody()));

                            break;
                        case UNEXPECTED:
                            System.err.println("Unexpected error has occurred.");
                            break;
                    }
                }
            });
        }

        while(true) {
            try {
                String yodaSentence = channel.take();
                System.out.println(yodaSentence);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static String typedInputString(TypedInput input) {
        long len = input.length();
        try {
            InputStream in = input.in();
            byte[] buff = new byte[(int) len];
            in.read(buff);
            return new String(buff);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}
