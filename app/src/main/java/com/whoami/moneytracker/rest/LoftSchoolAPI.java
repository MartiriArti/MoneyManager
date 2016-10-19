package com.whoami.moneytracker.rest;

import com.whoami.moneytracker.rest.models.UserLoginModel;
import com.whoami.moneytracker.rest.models.UserRegistrationModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface LoftSchoolAPI {

    @GET("/auth")
    Call<UserRegistrationModel> registerUser(@Query("login") String login,
                                             @Query("password") String password,
                                             @Query("register") String registrationFlag);

    @GET("/auth")
    Call<UserLoginModel> login(@Query("login") String login,
                               @Query("password") String password);
}
