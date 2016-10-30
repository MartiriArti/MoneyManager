package com.whoami.moneytracker.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.whoami.moneytracker.MoneyManagerApplication;
import com.whoami.moneytracker.R;
import com.whoami.moneytracker.ui.utils.ConstantManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.splash_activity)
public class SplashScreenActivity extends AppCompatActivity {
    @AfterViews
    void main() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (MoneyManagerApplication.getGoogleAuthToken().equals("") && MoneyManagerApplication.getAuthToken().equals("")) {
                    startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity_.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity_.class));
                    finish();
                }

            }
        }, ConstantManager.DELAY);
    }
}




