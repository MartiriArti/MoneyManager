package com.whoami.moneytracker.rest;

import com.whoami.moneytracker.ui.utils.ConstantManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private LoftSchoolAPI loftSchoolAPI;

    public RestClient(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantManager.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loftSchoolAPI = retrofit.create(LoftSchoolAPI.class);
    }

    public LoftSchoolAPI getLoftSchoolAPI() {
        return loftSchoolAPI;
    }
}
