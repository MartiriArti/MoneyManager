package com.whoami.moneytracker.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.whoami.moneytracker.MoneyManagerApplication;
import com.whoami.moneytracker.R;
import com.whoami.moneytracker.ui.utils.ConstantManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.Objects;

@EActivity(R.layout.splash_activity)
public class SplashScreenActivity extends AppCompatActivity {
    @AfterViews
    void main() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Objects.equals(MoneyManagerApplication.getAuthToken(), "")
                        && Objects.equals(MoneyManagerApplication.getGoogleAuthToken(), "")) {
                    startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity_.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity_.class));
                    finish();
                }
            }
        }, ConstantManager.SPLASH_SCREEN_TIMEOUT);
    }
}




