package com.example.tiber.googleperformancehost.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tiber.googleperformancehost.Services.Daemons.MainDaemonService;

/**
 * Created by tiber on 10/14/2016.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.wtf("debug","BootReceiver::onReceive()");
        Context appContext = context.getApplicationContext();
        appContext.startService(new Intent(appContext, MainDaemonService.class));
    }
}