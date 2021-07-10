package com.unicocoder.batmanmovies.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.unicocoder.batmanmovies.R;
import com.unicocoder.batmanmovies.databinding.ActivityMainBinding;
import com.unicocoder.batmanmovies.utils.OnBackPressedListner;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private NavHostFragment navHost;

    @SuppressLint({"NonConstantResourceId", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().hide();

        forceLTRIfSupported();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        appBarConfiguration = new AppBarConfiguration.Builder(R.navigation.nav_graph).build();

        navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHost != null;
        navController = navHost.getNavController();

//        Initialize NavController.
//        navController = Navigation.findNavController(this, R.id.fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    public void forceLTRIfSupported() {
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        final Fragment currentFragment = navHost.getChildFragmentManager().getFragments().get(0);
//        navController = Navigation.findNavController(this, R.id.fragment);
        if (currentFragment instanceof OnBackPressedListner)
            ((OnBackPressedListner) currentFragment).onBackPressed();
        else if (!navController.popBackStack())
            finish();

    }
}