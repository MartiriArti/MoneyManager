package com.whoami.moneytracker.rest;


import android.support.annotation.NonNull;

import com.whoami.moneytracker.rest.models.UserLoginModel;
import com.whoami.moneytracker.rest.models.UserRegistrationModel;
import com.whoami.moneytracker.ui.utils.ConstantManager;

import java.io.IOException;


public final class RestService {

    private RestClient restClient;

    public RestService() {
        restClient = new RestClient();
    }

    public UserRegistrationModel register(@NonNull String login,
                                          @NonNull String password) throws IOException {

        return restClient.getLoftSchoolAPI()
                .registerUser(login, password, ConstantManager.REGISTER_FLAG).execute().body();
    }

    public UserLoginModel login(@NonNull String login,
                                @NonNull String password) throws IOException {
        return restClient.getLoftSchoolAPI()
                .login(login, password).execute().body();
    }
}
