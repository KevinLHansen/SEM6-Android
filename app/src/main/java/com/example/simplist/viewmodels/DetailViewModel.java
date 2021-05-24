package com.example.simplist.viewmodels;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.simplist.BR;
import com.example.simplist.models.ShoppingList;
import com.example.simplist.models.ShoppingListItem;
import com.example.simplist.repositories.FirebaseRepository;

import java.util.ArrayList;
import java.util.List;

public class DetailViewModel extends ViewModel {
    private FirebaseRepository firebaseRepository= FirebaseRepository.getInstance();
    private MutableLiveData<ShoppingList> shoppingListModelData;
    private String title = "bruh";
    private MediatorLiveData<String> shoppingListTitle = new MediatorLiveData<>();
    private MediatorLiveData<List<ShoppingListItem>>  shoppingListItems = new MediatorLiveData<>();
    private ShoppingList list;

    private static final String TAG = "DetailViewModel";

    public DetailViewModel () {
        shoppingListModelData = new MutableLiveData<>();
        String title = "";
        shoppingListTitle.setValue(title);
        List<ShoppingListItem> items = new ArrayList<>();
        shoppingListItems.setValue(items);
    }

    public DetailViewModel (String id) {
            shoppingListModelData = firebaseRepository.getListById(id);
            shoppingListTitle.addSource(shoppingListModelData, shoppingList -> shoppingListTitle.setValue(shoppingList.getTitle()));
            shoppingListItems.addSource(shoppingListModelData, shoppingList -> shoppingListItems.setValue(shoppingList.getItems()));
    }

    public LiveData<ShoppingList> loadShoppingListModelData(String id) {
        if (shoppingListModelData == null) {
            shoppingListModelData = firebaseRepository.getListById(id);
        }
        return shoppingListModelData;
    }

    public void newItem() {
        List<ShoppingListItem> newItems = new ArrayList<>();
        newItems = shoppingListItems.getValue();
        newItems.add(new ShoppingListItem());
        shoppingListItems.setValue(newItems);
    }

    public void sendList(List<ShoppingListItem> items) {
        ShoppingList newList = new ShoppingList(shoppingListTitle.getValue(), items);
        firebaseRepository.insertList(newList);
    }

    public void sendList(String id, List<ShoppingListItem> items) {
        ShoppingList newList = shoppingListModelData.getValue();
        newList.setTitle(shoppingListTitle.getValue());
        newList.setItems(items);
        shoppingListModelData.setValue(newList);
        Log.d(TAG, "Updating list");
        firebaseRepository.insertList(id, newList);
    }

    public void removeList(String id){
        firebaseRepository.deleteList(id);
    }

    public MutableLiveData<ShoppingList> getShoppingListModelData() {
        return shoppingListModelData;
    }

    public MutableLiveData<String> getTitle(){
        Log.d(TAG, "ShoppingListTitle: " + shoppingListTitle.getValue());
        return shoppingListTitle;
    }

    public MutableLiveData<List<ShoppingListItem>> getItems() {
        Log.d(TAG, "ShoppingListItems: " + shoppingListItems.getValue());
        return shoppingListItems;
    }
}
