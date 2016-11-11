package com.whoami.moneytracker.sync.models;

import com.google.gson.annotations.SerializedName;

public class UserValidTokenModel {

    @SerializedName("status")
    private String status;
    @SerializedName("code")
    private int code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }


    public void setCode(int code) {
        this.code = code;
    }

}