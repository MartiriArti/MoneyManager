package com.whoami.moneytracker.sync.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UserSyncCategoriesModel {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<CategoryModel> data = new ArrayList<>();
    @SerializedName("code")
    int code;

    public String getStatus() {
        return status;
    }

    public List<CategoryModel> getData() {
        return data;
    }
}


