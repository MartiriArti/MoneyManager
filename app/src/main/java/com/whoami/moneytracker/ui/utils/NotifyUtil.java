package com.whoami.moneytracker.ui.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.ui.MainActivity_;

public class NotifyUtil{

    public static void updateNotifications(Context context)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String displayNotificationKey = context.getString(R.string.pref_enable_notifications_key);
        boolean displayNotifications = prefs.getBoolean(displayNotificationKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        String displaySoundKey = context.getString(R.string.pref_enable_sound_key);
        boolean displaySound = prefs.getBoolean(displaySoundKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        String displayVibrationKey = context.getString(R.string.pref_enable_vibrate_key);
        boolean displayVibration = prefs.getBoolean(displayVibrationKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        String displayLedKey = context.getString(R.string.pref_enable_indicator_key);
        boolean displayLed = prefs.getBoolean(displayLedKey,
                Boolean.parseBoolean(context.getString(R.string.pref_enable_notifications_default)));

        if(displayNotifications)
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Intent i= new Intent(context, MainActivity_.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(context, 0, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(intent);
            builder.setSmallIcon(R.mipmap.icon_app2);
            if(displayLed) {
                builder.setLights(Color.CYAN, 300, 1500);
            }
            if(displayVibration) {
                builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            }
            if(displaySound) {
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            }
            builder.setAutoCancel(true);

            String title = context.getString(R.string.app_name);
            String contentText = context.getResources().getString(R.string.notification_message);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_app2);
            builder.setLargeIcon(largeIcon);
            builder.setContentTitle(title);
            builder.setContentText(contentText);

            Notification notification= builder.build();
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(ConstantManager.NOTIFICATION_ID, notification);
        }
    }
}