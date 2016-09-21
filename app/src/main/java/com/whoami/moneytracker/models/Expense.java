package com.whoami.moneytracker.models;

public class Expense {

    public String description;
    public String price;

    public Expense(String description, String price) {
        this.description = description;
        this.price = price;
    }
}