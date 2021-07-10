package com.unicocoder.batmanmovies.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String Base_UEL = "http://www.omdbapi.com/";

    private static ApiClient apiClient;

    private final Retrofit retrofit;

    private ApiClient() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Base_UEL)
                .client(new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build())
                .build();

//        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        myApi = retrofit.create(Api.class);
    }

    public static synchronized ApiClient getInstance() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public ApiInterface getApiStack() {
        return retrofit.create(ApiInterface.class);
    }

}