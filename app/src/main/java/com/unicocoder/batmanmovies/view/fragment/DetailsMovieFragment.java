package com.unicocoder.batmanmovies.view.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unicocoder.batmanmovies.R;
import com.unicocoder.batmanmovies.databinding.DetailsMovieFragment1Binding;
import com.unicocoder.batmanmovies.databinding.DetailsMovieFragmentBinding;
import com.unicocoder.batmanmovies.model.RatingsItem;
import com.unicocoder.batmanmovies.model.ResponseItem;
import com.unicocoder.batmanmovies.utils.NetworkConnected;
import com.unicocoder.batmanmovies.utils.OnBackPressedListner;
import com.unicocoder.batmanmovies.view.adapter.AllMoviesAdapter;
import com.unicocoder.batmanmovies.view.adapter.RatingsMovieAdapter;
import com.unicocoder.batmanmovies.viewModel.MovieItemViewModel;
import com.unicocoder.batmanmovies.viewModel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailsMovieFragment extends Fragment {

    private static final String TAG = "DetailsMovieFragment";
    private DetailsMovieFragment1Binding binding;
    private MovieItemViewModel mViewModel;
    private String apikey = "3e974fca";
    private String imdbID = "";
    private List<RatingsItem> ratingsItems = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.details_movie_fragment1, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MovieItemViewModel.class);
        if (getArguments() != null)
            imdbID = getArguments().getString("idItem");

        getListHomeData(imdbID);

        setError();
    }

    private void getListHomeData(String imdbID) {
        if (NetworkConnected.getInstance(requireActivity())) {
            mViewModel.getMovieItem(apikey, imdbID).observe(requireActivity(), data -> {
                if (data != null) {

                    binding.setItem(data);

                    for (int i = 0; i < data.getRatings().size(); i++) {
                        ratingsItems.add(data.getRatings().get(i));
                    }

                    RatingsMovieAdapter ratingsMovieAdapter = new RatingsMovieAdapter(ratingsItems);
                    binding.rcDetailRatings.setAdapter(ratingsMovieAdapter);

                    binding.pbItemLoading.setVisibility(View.GONE);
                    binding.linearLayout2.setVisibility(View.VISIBLE);
                    binding.linearLayout.setVisibility(View.VISIBLE);
                    binding.button.setVisibility(View.VISIBLE);

                }
            });

        } else
            Toast.makeText(requireActivity(), "از اتصال به شبکه اینترنت اطمینان حاصل کنید", Toast.LENGTH_SHORT).show();
    }

    private void setError() {
        mViewModel.errorMutableLiveData.observe(requireActivity(), integer -> {
            if (integer == 0)
                Toast.makeText(requireActivity(), "دریافت اطلاعات از سرور با مشکل مواجه شده است", Toast.LENGTH_SHORT).show();
            else if (integer == -1)
                Toast.makeText(requireActivity(), "ارتباط با سرور با مشکل مواجه شده است", Toast.LENGTH_SHORT).show();
        });
    }
}