package com.whoami.moneytracker.rest.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.whoami.moneytracker.database.CategoryEntity;

public class UserCategoriesModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<CategoryEntity> data = new ArrayList<CategoryEntity>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CategoryEntity> getData() {
        return data;
    }

    public void setData(List<CategoryEntity> data) {
        this.data = data;
    }

}