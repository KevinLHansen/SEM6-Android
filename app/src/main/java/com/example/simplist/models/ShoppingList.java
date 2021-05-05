package com.example.simplist.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.firebase.firestore.DocumentId;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

public class ShoppingList extends BaseObservable {
    String title;
    Date date;
    @DocumentId
    String id;
    List<ShoppingListItem> items;


    public ShoppingList() {
    }


    public ShoppingList(String title) {
        this.title = title;
        this.date = new Timestamp(new Date().getTime());
        items = new ArrayList<>();
    }

    public ShoppingList(String title, List<ShoppingListItem> list) {
        this.title = title;
        this.date = new Timestamp(new Date().getTime());
        items = list;
    }

    public ShoppingList(String title, Timestamp date, List<ShoppingListItem> list) {
        this.title = title;
        this.date = date;
        items = list;
    }

    public void addItem(String title, String amount) {
        items.add(new ShoppingListItem(title, amount));
    }

    public void addItem() {
        items.add(new ShoppingListItem());
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ShoppingListItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItem> items) {
        this.items = items;
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

