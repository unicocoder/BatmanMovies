package com.unicocoder.batmanmovies.Api;

import com.unicocoder.batmanmovies.model.BatmanMovies;
import com.unicocoder.batmanmovies.model.ResponseItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(" ")
    Call<BatmanMovies> getBatmanMovies(@Query("apikey") String apikey, @Query("s") String s);

    @GET(" ")
    Call<ResponseItem> getBatmanMoviesItem(@Query("apikey") String apikey, @Query("i") String s);
}
    