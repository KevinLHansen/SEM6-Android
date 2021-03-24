package com.example.simplist;

public class ShoppingListItem {
    String title; // eg. "Banana" or "Flour"
    String amount; // eg. "2" or "500g"

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

    public void setAmount() {
        this.amount = amount;
    }
}
