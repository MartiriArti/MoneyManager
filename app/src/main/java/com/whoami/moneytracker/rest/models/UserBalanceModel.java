package com.whoami.moneytracker.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBalanceModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("balance")
    @Expose
    private String balance;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}