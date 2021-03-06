package com.whoami.moneytracker.rest;


import android.support.annotation.NonNull;

import com.whoami.moneytracker.MoneyManagerApplication;
import com.whoami.moneytracker.rest.models.UserBalanceModel;
import com.whoami.moneytracker.rest.models.UserCategoriesModel;
import com.whoami.moneytracker.rest.models.UserExpensesModel;
import com.whoami.moneytracker.rest.models.UserLoginModel;
import com.whoami.moneytracker.rest.models.UserLogoutModel;
import com.whoami.moneytracker.rest.models.UserRegistrationModel;
import com.whoami.moneytracker.sync.models.UserGetDataModel;
import com.whoami.moneytracker.sync.models.UserSyncCategoriesModel;
import com.whoami.moneytracker.sync.models.UserSyncExpensesModel;
import com.whoami.moneytracker.sync.models.UserValidTokenModel;
import com.whoami.moneytracker.ui.utils.ConstantManager;

import java.io.IOException;


public final class RestService {

    private RestClient restClient;

    public RestService() {
        restClient = new RestClient();
    }

    public UserRegistrationModel register(@NonNull String login,
                                          @NonNull String password) throws IOException {

        return restClient
                .getLoftSchoolAPI()
                .registerUser(login, password, ConstantManager.REGISTER_FLAG)
                .execute()
                .body();
    }

    public UserLoginModel login(@NonNull String login,
                                @NonNull String password) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .login(login, password)
                .execute()
                .body();
    }

    public UserLogoutModel logoutUser() throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .logoutUser()
                .execute()
                .body();
    }

    public UserValidTokenModel validToken (@NonNull String token) throws IOException{
        return restClient
                .getLoftSchoolAPI()
                .userValidToken(token)
                .execute()
                .body();
    }

    public UserGetDataModel getData(@NonNull String token) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .userData(token)
                .execute()
                .body();
    }

    public UserSyncCategoriesModel userSyncCategoriesModel (@NonNull String data, @NonNull String token, @NonNull String google_token) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .syncCategories(data, token, google_token)
                .execute()
                .body();
    }

    public UserSyncExpensesModel userSyncExpensesModel (@NonNull String data, @NonNull String token, @NonNull String google_token) throws IOException {
        return restClient
                .getLoftSchoolAPI()
                .syncExpenses(data, token, google_token)
                .execute().body();
    }

    public UserBalanceModel getBalance(String gToken) {
        return restClient
                .getLoftSchoolAPI()
                .getBalance(gToken, MoneyManagerApplication
                .getAuthToken());
    }

    public UserBalanceModel setBalance(float balance, String gToken) {
        return restClient
                .getLoftSchoolAPI()
                .setBalance(balance, gToken, MoneyManagerApplication
                .getAuthToken());
    }

    public UserExpensesModel getAllExpenses(String gToken){
        return restClient
                .getLoftSchoolAPI()
                .getAllExpenses(gToken, MoneyManagerApplication
                .getAuthToken());
    }

    public UserCategoriesModel getAllCategories(String gToken) {
        return restClient
                .getLoftSchoolAPI()
                .getAllCategories(gToken, MoneyManagerApplication
                .getAuthToken());
    }
}
