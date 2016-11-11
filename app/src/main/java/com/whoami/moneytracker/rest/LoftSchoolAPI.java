package com.whoami.moneytracker.rest;

import com.whoami.moneytracker.rest.models.UserLoginModel;
import com.whoami.moneytracker.rest.models.UserRegistrationModel;
import com.whoami.moneytracker.sync.models.UserGetDataModel;
import com.whoami.moneytracker.sync.models.UserSyncCategoriesModel;
import com.whoami.moneytracker.sync.models.UserSyncExpensesModel;
import com.whoami.moneytracker.sync.models.UserValidTokenModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface LoftSchoolAPI {

    @GET("/auth")
    Call<UserRegistrationModel> registerUser(@Query("login") String login,
                                             @Query("password") String password,
                                             @Query("register") String registrationFlag);

    @GET("/auth")
    Call<UserLoginModel> login(@Query("login") String login,
                               @Query("password") String password);

    @GET("/gcheck")
    Call<UserValidTokenModel> userValidToken(@Query("google_token") String googleToken);

    @GET("/gjson")
    Call<UserGetDataModel> userData(@Query("google_token") String googleToken);

    @POST("/categories/synch")
    Call<UserSyncCategoriesModel> syncCategories(@Query("data") String data,
                                                 @Query("auth_token") String token,
                                                 @Query("google_token") String googleToken);

    @POST("/transactions/synch")
    Call<UserSyncExpensesModel> syncExpenses(@Query("data") String data,
                                             @Query("auth_token") String token,
                                             @Query("google_token") String googleToken);
}
