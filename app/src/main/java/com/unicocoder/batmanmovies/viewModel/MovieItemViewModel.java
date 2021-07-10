package com.unicocoder.batmanmovies.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unicocoder.batmanmovies.Api.ApiClient;
import com.unicocoder.batmanmovies.model.ResponseItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieItemViewModel extends ViewModel {

    private MutableLiveData<ResponseItem> responseItemMutableLiveData = new MutableLiveData();
    public MutableLiveData<Integer> errorMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ResponseItem> getMovieItem(String key , String i) {

        ApiClient.getInstance().getApiStack().getBatmanMoviesItem(key, i).enqueue(new Callback<ResponseItem>() {
            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {
                if (response.isSuccessful() && response.body() != null)
                    responseItemMutableLiveData.setValue(response.body());
                else
                    errorMutableLiveData.setValue(0);
            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
            errorMutableLiveData.setValue(-1);
            }
        });

        return responseItemMutableLiveData;
    }
}