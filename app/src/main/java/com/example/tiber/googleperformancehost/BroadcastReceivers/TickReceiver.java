package com.example.tiber.googleperformancehost.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tiber.googleperformancehost.Services.Daemons.MainDaemonService;

/**
 * Created by tiber on 10/14/2016.
 */

public class TickReceiver extends BroadcastReceiver {

    private static TickReceiver instance = null;

    private TickReceiver(){
        super();
    }


    public synchronized static TickReceiver getInstance(){
        if( instance == null)
            instance  = new TickReceiver();
        return instance;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.wtf("debug","TickReceiver::onReceive()");
        Context appContext = context.getApplicationContext();
        appContext.startService(new Intent(appContext.getApplicationContext(), MainDaemonService.class));
    }
}
