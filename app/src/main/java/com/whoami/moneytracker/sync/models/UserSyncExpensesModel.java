package com.whoami.moneytracker.sync.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSyncExpensesModel {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<ExpensesModel> data = new ArrayList<ExpensesModel>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ExpensesModel> getData() {
        return data;
    }

    public void setData(List<ExpensesModel> data) {
        this.data = data;
    }

}