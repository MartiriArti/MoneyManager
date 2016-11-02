package com.whoami.moneytracker.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.SignInButton;
import com.whoami.moneytracker.BuildConfig;
import com.whoami.moneytracker.MoneyManagerApplication;
import com.whoami.moneytracker.R;
import com.whoami.moneytracker.rest.RestService;
import com.whoami.moneytracker.rest.models.UserLoginModel;
import com.whoami.moneytracker.rest.models.UserRegistrationModel;
import com.whoami.moneytracker.ui.utils.ConstantManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

@EActivity(R.layout.registration_activity)
public class RegistrationActivity extends AppCompatActivity {

    @ViewById(R.id.registration_layout_root)
    LinearLayout linearLayout;
    @ViewById(R.id.enter_login)
    EditText loginEt;
    @ViewById(R.id.enter_password)
    EditText pass;
    @ViewById(R.id.confirm_password)
    EditText confirm_pass;
    @ViewById(R.id.registration_btn)
    Button registration;
    @ViewById(R.id.login_google)
    SignInButton login_google_btn;
    @ViewById(R.id.no_reg_CB)
    CheckBox noRegCB;

    @Background
    public void register(String login, String password) {
        RestService restService = new RestService();
        try {
            UserRegistrationModel registrationModel = restService.register(login, password);
            if (registrationModel.getStatus().equals(ConstantManager.LOGIN_SUCCEED)) {
                successRegistration();
                finish();
            } else {
                loginBusy();
            }
        } catch (IOException e) {
            showUnknownError();
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    @Background
    public void login(String login, String password) {
        RestService restService = new RestService();
        try {
            UserLoginModel userLoginModel = restService.login(login, password);
            if (userLoginModel.getStatus().equals(ConstantManager.LOGIN_SUCCEED)) {
                MoneyManagerApplication.saveAuthToken(userLoginModel.getAuthToken());
                successRegistration();
                finish();
            } else {
                wrongLogin();
            }
        } catch (IOException e) {
            showUnknownError();
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

    }

    @UiThread
    void wrongLogin() {
        Snackbar.make(linearLayout, R.string.wrong_login, Snackbar.LENGTH_SHORT).show();
    }

    @UiThread
    void successRegistration() {
        noRegCB.setChecked(false);
        loginEt.setText("");
        pass.setText("");
        Snackbar.make(linearLayout, R.string.registration_succsess, Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @UiThread
    void loginBusy() {
        Snackbar.make(linearLayout, R.string.registration_login_is_already_taken, Snackbar.LENGTH_SHORT).show();
    }


    @UiThread
    void showUnknownError() {
        Snackbar.make(linearLayout, R.string.registration_error, Snackbar.LENGTH_SHORT).show();
    }

    @AfterViews
    public void run() {
        setTitle(R.string.registration);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

    if (noRegCB.isChecked()) {
        if (isNwConnected(RegistrationActivity.this)) {
            String login = loginEt.getText().toString();
            String password = pass.getText().toString();
            String confirm = confirm_pass.getText().toString();
            if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
                if (login.length() >= 5 && password.length() >= 5) {
                    if (confirm.equals(password)) {
                        register(login, password);
                    } else {
                        Snackbar.make(linearLayout, R.string.wrong_confirm, Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(linearLayout, R.string.registration_login, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                if (TextUtils.isEmpty(login))
                    Snackbar.make(linearLayout, getString(R.string.wrong_login), Snackbar.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(password))
                    Snackbar.make(linearLayout, getString(R.string.wrong_password), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(linearLayout, R.string.network_not_available, Snackbar.LENGTH_SHORT).show();
        }

    } else {
        if (isNwConnected(RegistrationActivity.this)) {
            String login = loginEt.getText().toString();
            String password = pass.getText().toString();
            if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
                login(login, password);
            } else {
                Snackbar.make(linearLayout, R.string.wrong_login, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(linearLayout, R.string.network_not_available, Snackbar.LENGTH_SHORT).show();
        }
    }

}

        });

        noRegCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (noRegCB.isChecked()) {
                    confirm_pass.setVisibility(View.VISIBLE);
                    registration.setText(getString(R.string.registration));
                } else {
                    confirm_pass.setVisibility(View.GONE);
                    registration.setText(getString(R.string.login_activity_btn));
                }

            }
        });

        login_google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false,
                        null, null, null, null);
                startActivityForResult(intent, ConstantManager.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantManager.REQUEST_CODE && resultCode == RESULT_OK) {
            logInWithGoogle(data);
        }
    }

    @Background
    void logInWithGoogle(Intent data) {
        String token = null;
        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        final String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

        Account account = new Account(accountName, accountType);
        try {
            token = GoogleAuthUtil.getToken(this, account, ConstantManager.SCOPES);

        } catch (UserRecoverableAuthException userAuthEx) {
            startActivityForResult(userAuthEx.getIntent(), ConstantManager.REQUEST_CODE);
        } catch (IOException | GoogleAuthException e) {
            e.printStackTrace();
        }
        if (token != null) {
            MoneyManagerApplication.saveGoogleAuthToken(token);
            successRegistration();
            finish();
        }
    }

    public static boolean isNwConnected(Context context) {
        if (context == null) {
            return true;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
        if (nwInfo != null && nwInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


}