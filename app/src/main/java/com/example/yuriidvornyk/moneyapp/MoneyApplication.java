package com.example.yuriidvornyk.moneyapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public class MoneyApplication extends Application {

    private static Context context;
    private static SharedPreferences sharedPreferences;

    public static Context getAppContext() {
        return context;
    }

    public static SharedPreferences getDefaultSharedPreferences() {
        return sharedPreferences;
    }

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sharedPreferences = getSharedPreferences("app-preferences", MODE_PRIVATE);
        AndroidThreeTen.init(this);
    }
}
