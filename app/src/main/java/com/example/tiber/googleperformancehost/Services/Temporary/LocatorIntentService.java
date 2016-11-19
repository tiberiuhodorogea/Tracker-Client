package com.example.tiber.googleperformancehost.Services.Temporary;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.tiber.googleperformancehost.R;
import com.example.tiber.googleperformancehost.Services.Daemons.SmallDataSender;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.LocationData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

/**
 * Created by tiber on 10/17/2016.
 */

public class LocatorIntentService extends IntentService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private final String debugTag = this.getClass().getSimpleName().toString() + "";
    private Context appContext;

    public LocatorIntentService() {
        super("intent_service_name");
        Log.wtf(debugTag, " ctor fara params");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.wtf(debugTag, "onStartCommang()");
        return super.onStartCommand(intent, flags, startId);
    }

    public LocatorIntentService(String name) {
        super(name);
        Log.wtf(debugTag, this.getClass().toString() + "constructor cu name");
        this.appContext = getApplicationContext();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.wtf(debugTag, "::onHandleIntent()");
        this.appContext = getApplicationContext();

            mGoogleApiClient = new GoogleApiClient.Builder(appContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                    .setFastestInterval(1 * 1000); // 1 second, in milliseconds

            mGoogleApiClient.connect();
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

        mGoogleApiClient.disconnect();

        stopSelf();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.wtf(debugTag, "onConnected()");
        if (mGoogleApiClient.isConnected()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){}
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            Log.wtf(debugTag,"is connected..");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.wtf(debugTag,"onConnectionSuspended() " + i);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.wtf(debugTag,"onConectionFailed(), connection result = " + connectionResult.toString());

    }

}
