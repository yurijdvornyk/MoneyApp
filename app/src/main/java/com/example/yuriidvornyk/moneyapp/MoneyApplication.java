package com.example.yuriidvornyk.moneyapp;

import android.app.Application;
import android.content.Context;

import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public class MoneyApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AndroidThreeTen.init(this);
    }
}
