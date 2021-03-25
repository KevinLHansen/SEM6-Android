package com.example.simplist.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplist.models.ShoppingList;
import com.example.simplist.models.ShoppingListItem;

import java.util.ArrayList;

public class ShoppingListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ShoppingList>> lists;
    private ArrayList<ShoppingList> slList = new ArrayList<ShoppingList>();
    public LiveData<ArrayList<ShoppingList>> getLists() {
        if (lists == null) {
            lists = new MutableLiveData<ArrayList<ShoppingList>>();
            loadLists();
        }
        return lists;
    }

    private void loadLists() {
        ShoppingList sl1 = new ShoppingList("friday");
        ShoppingList sl2 = new ShoppingList("saturday");

        sl1.addItem(new ShoppingListItem("banana", "2 pcs"));
        sl1.addItem(new ShoppingListItem("flour", "500g"));

        sl2.addItem(new ShoppingListItem("frozen pizza", "3"));
        sl2.addItem(new ShoppingListItem("milk", "2 liters"));
        sl2.addItem(new ShoppingListItem("toffee cups", "5 packs"));

        slList.add(sl1);
        slList.add(sl2);
        lists.setValue(slList);
    }

    public void deleteList(int position) {
        lists.getValue().remove(position);
        // Does the same as notifyDataSetChanged()
        // So why this?
        lists.setValue(lists.getValue());
    }

    public void addList(int position, String title) {
        lists.getValue().add(position, new ShoppingList(title));
        // Does the same as notifyDataSetChanged()
        // So why this?
        lists.setValue(lists.getValue());
    }

}
