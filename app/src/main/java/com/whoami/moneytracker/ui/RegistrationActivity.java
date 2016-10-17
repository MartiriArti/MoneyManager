package com.whoami.moneytracker.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.BuildConfig;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RelativeLayout;

import com.whoami.moneytracker.R;
import com.whoami.moneytracker.rest.NetworkStatusChecker;
import com.whoami.moneytracker.rest.RestService;
import com.whoami.moneytracker.rest.models.UserRegistrationModel;
import com.whoami.moneytracker.ui.utils.ConstantManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;


@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends AppCompatActivity {

    private final static String LOG_TAG = RegistrationActivity_.class.getSimpleName();

    @ViewById(R.id.registration_layout_root)
    RelativeLayout registrationLayout;
    @ViewById(R.id.enter_login)
    EditText enterLogin;
    @ViewById(R.id.enter_password)
    EditText enterPassword;
    @ViewById(R.id.registration_btn)
    Button btnRegistration;
    @ViewById(R.id.registration_btn_sign_up)
    Button btnSignUp;

    @Click(R.id.registration_btn)
    void registerUser() {

        if (NetworkStatusChecker.isNetworkAvailable(this)) {
            String login = enterLogin.getText().toString();
            String password = enterPassword.getText().toString();
            if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
                if (login.length() >= ConstantManager.MIN_LENGTH && password.length() >= ConstantManager.MIN_LENGTH) {
                    register(login, password);
                } else {
                    Snackbar.make(registrationLayout,
                            getString(R.string.registration_error),
                            Snackbar.LENGTH_LONG).show();
                }
            } else {
                if (TextUtils.isEmpty(login)) showRegistrationErrorLogin();
                if (TextUtils.isEmpty(password)) showRegistrationErrorPassword();
            }
        } else {
            Snackbar.make(registrationLayout,
                    getString(R.string.network_not_available),
                    Snackbar.LENGTH_SHORT).show();
        }
    }
    @Background
    void register(String login, String password) {
        RestService restService = new RestService();
        try {
            UserRegistrationModel registrationModel = restService.register(login, password);
            Log.d(LOG_TAG, "Status: " + registrationModel.getStatus());
            if (registrationModel.getStatus().equals(ConstantManager.STATUS_SUCCEED)) {
                navigateToMainScreen();
            } else if (registrationModel.getStatus().equals(ConstantManager.STATUS_LOGIN_BUSY_ALREADY)) {
                showLoginBusy();
            }
        } catch (IOException e) {
            showUnknownError();
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    @UiThread
    void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity_.class));
    }
    @UiThread
    void showLoginBusy() {
        Snackbar.make(registrationLayout,
                getString(R.string.wrong_login),
                Snackbar.LENGTH_SHORT).show();
    }
    @UiThread
    void showUnknownError() {
        Snackbar.make(registrationLayout,
                getString(R.string.registration_error),
                Snackbar.LENGTH_SHORT).show();
    }
    @UiThread
    void showRegistrationErrorLogin() {
        Snackbar.make(registrationLayout,
                getString(R.string.registration_login),
                Snackbar.LENGTH_LONG).show();
    }
    @UiThread
    void showRegistrationErrorPassword() {
        Snackbar.make(registrationLayout,
                getString(R.string.wrong_password),
                Snackbar.LENGTH_LONG).show();
    }
}
