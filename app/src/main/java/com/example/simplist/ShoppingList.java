package com.example.simplist;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ShoppingList {
    String title;
    Timestamp date;
    HashMap<String, String> items;


    public ShoppingList() {
    }

    public ShoppingList(String title) {
        this.title = title;
        this.date = new Timestamp(new Date());
        items = new HashMap<String, String>();
    }

    public ShoppingList(String title, Timestamp date, HashMap<String, String> list) {
        this.title = title;
        this.date = date;
        items = list;
    }

    public void addItem(ShoppingListItem item) {
        items.put(item.getTitle(), item.getAmount());
    }

    public String getTitle() {
        return title;
    }

    public Timestamp getDate() {
        return date;
    }

    public HashMap<String, String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "\n ShoppingList{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", items=" + items +
                '}';
    }
}

