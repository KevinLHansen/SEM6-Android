package com.example.simplist.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

public class ShoppingList implements Parcelable {
    String title;
    Date date;
    HashMap<String, String> items;


    public ShoppingList() {
    }

    public ShoppingList(Parcel parcel) {
        title = parcel.readString();
        date = new Timestamp(parcel.readLong());
        items = parcel.readHashMap(String.class.getClassLoader());
    }

    public ShoppingList(String title) {
        this.title = title;
        this.date = new Timestamp(new Date().getTime());
        items = new HashMap<String, String>();
    }

    public ShoppingList(String title, Timestamp date, HashMap<String, String> list) {
        this.title = title;
        this.date = date;
        items = list;
    }

    public static final Creator<ShoppingList> CREATOR = new Creator<ShoppingList>() {
        @Override
        public ShoppingList createFromParcel(Parcel in) {
            return new ShoppingList(in);
        }

        @Override
        public ShoppingList[] newArray(int size) {
            return new ShoppingList[size];
        }
    };

    public void addItem(ShoppingListItem item) {
        items.put(item.getTitle(), item.getAmount());
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeLong(date.getTime());
        parcel.writeMap(items);
    }
}

