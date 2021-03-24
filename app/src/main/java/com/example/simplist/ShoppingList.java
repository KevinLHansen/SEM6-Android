package com.example.simplist;

import java.util.ArrayList;
import java.util.Date;

public class ShoppingList {
    String title;
    Date date;
    ArrayList<ShoppingListItem> items;

    public ShoppingList(String title) {
        this.title = title;
        this.date = new Date();
        items = new ArrayList<ShoppingListItem>();
    }

    public void addItem(ShoppingListItem item) {
        items.add(item);
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList getItems() {
        return items;
    }
}

