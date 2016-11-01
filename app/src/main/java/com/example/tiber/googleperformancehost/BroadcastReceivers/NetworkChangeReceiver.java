package com.example.tiber.googleperformancehost.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.tiber.googleperformancehost.Services.Daemons.SmallDataSender;

/**
 * Created by tiber on 10/25/2016.
 */

public class NetworkChangeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startSmallDataSender = new Intent(context.getApplicationContext(),SmallDataSender.class);
        startSmallDataSender.putExtra("what","networkChange");

        if(internetAccess(context.getApplicationContext())){
            startSmallDataSender.putExtra("internetAccess",true);
        }
        else{
            startSmallDataSender.putExtra("internetAccess",false);
        }
        context.getApplicationContext().startService(startSmallDataSender);
    }
    public boolean internetAccess(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
