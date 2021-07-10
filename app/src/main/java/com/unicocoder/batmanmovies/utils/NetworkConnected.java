package com.unicocoder.batmanmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkConnected {

    public static boolean getInstance(Context context){
        ConnectivityManager   connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
