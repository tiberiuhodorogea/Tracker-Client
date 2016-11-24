package com.example.tiber.googleperformancehost.Services.Abstract;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.tiber.googleperformancehost.R;
import com.example.tiber.googleperformancehost.SharedClasses.Utils.DateUtil;

import static android.app.Notification.FLAG_AUTO_CANCEL;
import static android.app.Notification.VISIBILITY_SECRET;

/**
 * Created by tiber on 10/17/2016.
 */

public abstract class MainAbstractService extends Service{

     private SharedPreferences.Editor editor = null;
     private SharedPreferences sharedPref = null;
     private final static int NOTIFICATION_ID = 945182;//random...
     private final static int MILIS_200 = 200;
     protected Context appContext = null;

     public void onCreate() {
          super.onCreate();
          appContext = getApplicationContext();
          this.sharedPref = appContext.getSharedPreferences("data",Context.MODE_PRIVATE);
          this.editor = sharedPref.edit();
     }

     protected void writePersistentInt(String key,int value){
          editor.putInt(key,value);
          editor.commit();
     }
     protected int getPersistentInt(String key){
          return sharedPref.getInt(key,0);
     }


    protected int getLatestTimeLocatorStarted(){

        int ret =  sharedPref.getInt(appContext.getString(R.string.LATEST_TIME_OF_LOCATOR_STARTED_KEY),0);
        if( ret != 0 )
            return ret;

        int twentyMinutesAgo = DateUtil.nowIntFormat()+ 60* 20;
        writePersistentInt(getApplicationContext().getString(R.string.LATEST_TIME_OF_LOCATOR_STARTED_KEY),twentyMinutesAgo );

        return twentyMinutesAgo;
    }

    protected void updateTimeOfLatestLocatorStarted() {
        writePersistentInt(getApplicationContext().
                getString(R.string.LATEST_TIME_OF_LOCATOR_STARTED_KEY), DateUtil.nowIntFormat());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = new Notification();
        notification.flags |= VISIBILITY_SECRET;
        notification.flags |= FLAG_AUTO_CANCEL;
        startForeground(NOTIFICATION_ID,notification);

        try {
            Thread.sleep(MILIS_200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);

        return super.onStartCommand(intent, flags, startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
