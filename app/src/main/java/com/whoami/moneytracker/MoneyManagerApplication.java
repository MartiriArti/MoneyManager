package com.whoami.moneytracker;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class MoneyManagerApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

    }
}
