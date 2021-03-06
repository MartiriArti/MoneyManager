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

    public static void saveAuthToken (String token){
        sharedPreferences.edit().putString(ConstantManager.TOKEN_KEY, token).apply();
    }

    public static String getAuthToken(){
        return sharedPreferences.getString(ConstantManager.TOKEN_KEY, "");
    }

    public static void saveGoogleAuthToken (String token){
        sharedPreferences.edit().putString(ConstantManager.GOOGLE_TOKEN_KEY, token).apply();
    }

    public static String getGoogleAuthToken(){
        return sharedPreferences.getString(ConstantManager.GOOGLE_TOKEN_KEY, "");
    }

    public static String getGoogleAvatar(){
        return sharedPreferences.getString(ConstantManager.AVATAR, "");
    }

    public static void saveGoogleAvatar (String url){
        sharedPreferences.edit().putString(ConstantManager.AVATAR, url).apply();
    }

    public static String getUserName(){
        return sharedPreferences.getString(ConstantManager.USER_NAME, "");
    }

    public static void saveGoogleUserName(String name){
        sharedPreferences.edit().putString(ConstantManager.USER_NAME, name).apply();
    }

    public static String getUserEmile(){
        return sharedPreferences.getString(ConstantManager.USER_EMALE, "");
    }

    public static void saveGoogleUserEmail(String emile){
        sharedPreferences.edit().putString(ConstantManager.USER_EMALE, emile).apply();
    }

}
