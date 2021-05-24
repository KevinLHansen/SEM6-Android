package com.example.simplist.models;

public class ShoppingListItem {
    private String title;
    private String amount;

    public ShoppingListItem(){

    }

    public ShoppingListItem(String title, String amount) {
        this.title = title;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
