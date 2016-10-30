package com.whoami.moneytracker;

import android.app.Application;
import android.content.SharedPreferences;

import com.activeandroid.ActiveAndroid;
import com.whoami.moneytracker.ui.utils.ConstantManager;

public class MoneyManagerApplication extends Application{
    private static SharedPreferences sharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        sharedPreferences = getSharedPreferences(ConstantManager.SHARED_PREF, MODE_PRIVATE);
    }

    public static void seveAuthToken (String token){
        sharedPreferences.edit().putString(ConstantManager.TOKEN_KEY, token).apply();
    }

    public static String getAuthToken(){
        return sharedPreferences.getString(ConstantManager.TOKEN_KEY, "");
    }

    public static void seveGoogleAuthToken (String token){
        sharedPreferences.edit().putString(ConstantManager.GOOGLE_TOKEN_KEY, token).apply();
    }

    public static String getGoogleAuthToken(){
        return sharedPreferences.getString(ConstantManager.GOOGLE_TOKEN_KEY, "");
    }

}
