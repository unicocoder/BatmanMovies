package com.unicocoder.batmanmovies.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unicocoder.batmanmovies.Api.ApiClient;
import com.unicocoder.batmanmovies.model.BatmanMovies;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<BatmanMovies> batmanMoviesMutableLiveData = new MutableLiveData();
    public MutableLiveData<Integer> errorMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<BatmanMovies> getMovies(String key , String s) {

        ApiClient.getInstance().getApiStack().getBatmanMovies(key, s).enqueue(new Callback<BatmanMovies>() {
            @Override
            public void onResponse(@NotNull Call<BatmanMovies> call, @NotNull Response<BatmanMovies> response) {
                if (response.isSuccessful() && response.body() != null)
                    batmanMoviesMutableLiveData.setValue(response.body());
                else
                    errorMutableLiveData.setValue(0);
            }

            @Override
            public void onFailure(@NotNull Call<BatmanMovies> call, @NotNull Throwable t) {
                errorMutableLiveData.setValue(-1);
            }
        });

        return batmanMoviesMutableLiveData;
    }
}