package com.whoami.moneytracker.rest.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.whoami.moneytracker.database.ExpenseEntity;

public class UserExpensesModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ExpenseEntity> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ExpenseEntity> getData() {
        return data;
    }

    public void setData(List<ExpenseEntity> data) {
        this.data = data;
    }

}