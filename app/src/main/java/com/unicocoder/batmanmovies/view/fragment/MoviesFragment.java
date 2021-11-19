package com.unicocoder.batmanmovies.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import com.unicocoder.batmanmovies.R;
import com.unicocoder.batmanmovies.databinding.MoviesFragmentBinding;
import com.unicocoder.batmanmovies.db.AppDatabase;
import com.unicocoder.batmanmovies.db.model.Batman;
import com.unicocoder.batmanmovies.model.BatmanItem;
import com.unicocoder.batmanmovies.model.BatmanMovies;
import com.unicocoder.batmanmovies.utils.NetworkConnected;
import com.unicocoder.batmanmovies.utils.OnBackPressedListner;
import com.unicocoder.batmanmovies.view.adapter.AllMoviesAdapter;
import com.unicocoder.batmanmovies.view.adapter.AllMoviesDBAdapter;
import com.unicocoder.batmanmovies.viewModel.MoviesViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment implements OnBackPressedListner {

    private static final String TAG = "HomeFragment";
    private MoviesViewModel mViewModel;

    private MoviesFragmentBinding binding;
    private NavController navController;
    private boolean doubleBackToExitPressedOnce = false;

    //save state
    private static String LIST_STATE = "list_state";
    private Parcelable savedRecyclerLayputState;
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private ArrayList<BatmanMovies> homeDataItemsInstance = new ArrayList<>();

    private String apikey = "3e974fca";
    private String s = "batman";

    private AppDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);
        NavHostFragment navHost = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        assert navHost != null;
        navController = navHost.getNavController();

        database = AppDatabase.getAppDatabase(requireActivity());

        if (savedInstanceState != null) {
            savedRecyclerLayputState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        } else {
            getListHomeData();
            setError();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putParcelableArrayList(LIST_STATE, (ArrayList<? extends Parcelable>) homeDataItemsInstance);
        state.putParcelable(BUNDLE_RECYCLER_LAYOUT, binding.rcMoviesShowAll.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(state);
    }

    private void setError() {
        mViewModel.errorMutableLiveData.observe(requireActivity(), integer -> {
            if (integer == 0)
                Toast.makeText(requireActivity(), "دریافت اطلاعات از سرور با مشکل مواجه شده است", Toast.LENGTH_SHORT).show();
            else if (integer == -1)
                Toast.makeText(requireActivity(), "ارتباط با سرور با مشکل مواجه شده است", Toast.LENGTH_SHORT).show();

            mViewModel.errorMutableLiveData.removeObservers(requireActivity());
        });
    }

    private void getListHomeData() {
        if (NetworkConnected.getInstance(requireActivity())) {
            mViewModel.getMovies(apikey, s).observe(requireActivity(), data -> {
                if (data != null) {

                    binding.setHomeData(data);

                    binding.pbHomeLoading.setVisibility(View.GONE);
                    binding.nsvHomeScroll.setVisibility(View.VISIBLE);

                    if (database.userDao().countMovies() == 0) {
                        Batman batman = new Batman();
                        for (int i = 0; i < data.getSearch().size(); i++) {
                            batman.setImdbID(data.getSearch().get(i).getImdbID());
                            batman.setPoster(data.getSearch().get(i).getPoster());
                            batman.setTitle(data.getSearch().get(i).getTitle());
                            batman.setType(data.getSearch().get(i).getType());
                            batman.setYear(data.getSearch().get(i).getYear());
                            database.userDao().insertAll(batman);
                        }
                    }

//                    All Movies
                    AllMoviesAdapter allMoviesAdapter = new AllMoviesAdapter(data.getSearch(), (batmanItem, imageView) -> {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("item", batmanItem);
                        bundle.putString("idItem", batmanItem.getImdbID());

                        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                                .addSharedElement(imageView, getString(R.string.hero_image))
                                .build();

                        navController.navigate(R.id.action_MoviesFragment_to_DetailsMovieFragment
                                , bundle
                                , null
                                , extras);
                    });

                    binding.rcMoviesShowAll.setAdapter(allMoviesAdapter);

                    mViewModel.getMovies(apikey, s).removeObservers(requireActivity());
                }
            });

        } else {

            binding.pbHomeLoading.setVisibility(View.GONE);
            binding.nsvHomeScroll.setVisibility(View.VISIBLE);
            List<Batman> batmanItems = database.userDao().getAll();
            AllMoviesDBAdapter allMoviesDBAdapter = new AllMoviesDBAdapter(batmanItems);
            binding.rcMoviesShowAll.setAdapter(allMoviesDBAdapter);

            Toast.makeText(requireActivity(), "از اتصال به شبکه اینترنت اطمینان حاصل کنید", Toast.LENGTH_SHORT).show();
        }
    }

     private void getListHomeDataSingle() {
        if (NetworkConnected.getInstance(requireActivity())) {
            compositeDisposable.add( mViewModel.getMoviesSingle(apikey, s)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<Response<BatmanMovies>>() {
                        @Override
                        public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Response<BatmanMovies> batmanMoviesResponse) {
                            if (batmanMoviesResponse.code() == 200 && batmanMoviesResponse.body() != null) {
                                Log.i(TAG, "getListHomeData: data " + batmanMoviesResponse.body().getSearch().size());
                                Log.i(TAG, "getListHomeData: code " + batmanMoviesResponse.code());

                                binding.setHomeData(batmanMoviesResponse.body());

                                binding.pbHomeLoading.setVisibility(View.GONE);
                                binding.nsvHomeScroll.setVisibility(View.VISIBLE);

                                if (database.userDao().countMovies() == 0) {
                                    Batman batman = new Batman();
                                    for (int i = 0; i < batmanMoviesResponse.body().getSearch().size(); i++) {
                                        batman.setImdbID(batmanMoviesResponse.body().getSearch().get(i).getImdbID());
                                        batman.setPoster(batmanMoviesResponse.body().getSearch().get(i).getPoster());
                                        batman.setTitle(batmanMoviesResponse.body().getSearch().get(i).getTitle());
                                        batman.setType(batmanMoviesResponse.body().getSearch().get(i).getType());
                                        batman.setYear(batmanMoviesResponse.body().getSearch().get(i).getYear());
                                        database.userDao().insertAll(batman);
                                    }
                                }

//                    All Movies
                                AllMoviesAdapter allMoviesAdapter = new AllMoviesAdapter(batmanMoviesResponse.body().getSearch(), (batmanItem, imageView) -> {
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("item", batmanItem);
                                    bundle.putString("idItem", batmanItem.getImdbID());

                                    FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                                            .addSharedElement(imageView, getString(R.string.hero_image))
                                            .build();

                                    navController.navigate(R.id.action_MoviesFragment_to_DetailsMovieFragment
                                            , bundle
                                            , null
                                            , extras);
                                });

                                binding.rcMoviesShowAll.setAdapter(allMoviesAdapter);

                                mViewModel.getMovies(apikey, s).removeObservers(requireActivity());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            ((HttpException) e).code();
                        }
                    }));

        } else {

            binding.pbHomeLoading.setVisibility(View.GONE);
            binding.nsvHomeScroll.setVisibility(View.VISIBLE);
            List<Batman> batmanItems = database.userDao().getAll();
            AllMoviesDBAdapter allMoviesDBAdapter = new AllMoviesDBAdapter(batmanItems);
            binding.rcMoviesShowAll.setAdapter(allMoviesDBAdapter);

            Toast.makeText(requireActivity(), "از اتصال به شبکه اینترنت اطمینان حاصل کنید", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
    
    @Override
    public boolean onBackPressed() {
        if (!doubleBackToExitPressedOnce) {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(getActivity(), "جهت خروج دوباره فشار دهید", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            return true;
        } else {
            getActivity().finish();
            return false;
        }
    }

}
