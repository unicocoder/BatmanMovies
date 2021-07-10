package com.unicocoder.batmanmovies.utils;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseReceiverActivity extends AppCompatActivity {

    private BroadcastReceiver  receiver;
    private IntentFilter filter;

    public abstract BroadcastReceiver getReceiver();
    public abstract IntentFilter getFilter();

    @Override
    public void onStart(){
        super.onStart();
        configureReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

//    @Override
//    public void onSt{
//        super.onPause();
//        unregisterReceiver(receiver);
//    }

    private void registerMyReceiver(){
        registerReceiver(receiver, filter);
    }

    private void configureReceiver(){
        receiver = getReceiver();
        filter   = getFilter();
    }

}
