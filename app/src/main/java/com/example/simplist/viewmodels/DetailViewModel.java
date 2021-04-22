package com.example.simplist.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplist.models.ShoppingList;
import com.example.simplist.repositories.FirebaseRepository;

public class DetailViewModel extends ViewModel {
    private FirebaseRepository firebaseRepository= FirebaseRepository.getInstance();
    private MutableLiveData<ShoppingList> shoppingListModelData;
    private ShoppingList list;

    public LiveData<ShoppingList> getShoppingListModelData(String id) {
        if (shoppingListModelData == null) {
            shoppingListModelData = firebaseRepository.getListById(id);
        }
        return shoppingListModelData;
    }

    public void newItem() {
        list = shoppingListModelData.getValue();
        list.addItem();
        shoppingListModelData.setValue(list);
    }

    public void sendList() {
        firebaseRepository.insertList(shoppingListModelData.getValue());
    }
}
