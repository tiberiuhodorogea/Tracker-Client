package com.example.tiber.googleperformancehost.Services.Abstract;

import android.app.Service;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.app.Notification.FLAG_AUTO_CANCEL;
import static android.app.Notification.FLAG_FOREGROUND_SERVICE;
import static android.app.Notification.VISIBILITY_SECRET;

/**
 * Created by tiber on 10/24/2016.
 */

public abstract class ForegroundInvisibleService extends Service {

    private final static int NOTIFICATION_ID = 845179;//random...
    private final static int MILIS_200 = 200;
    protected Context appContext = null;
    private int notificationId;//random...

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationId = createID();
        Notification notification = new Notification();
        notification.flags |= VISIBILITY_SECRET;
        notification.flags |= FLAG_AUTO_CANCEL;
        startForeground(notificationId,notification);

        try {
            Thread.sleep(MILIS_200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);

        return super.onStartCommand(intent, flags, startId);
    }
    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
