package com.example.simplist.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simplist.models.ShoppingList;
import com.example.simplist.repositories.FirebaseRepository;

public class DetailViewModel extends ViewModel {
    private FirebaseRepository firebaseRepository= FirebaseRepository.getInstance();
    private MutableLiveData<ShoppingList> shoppingListModelData;

    public LiveData<ShoppingList> getShoppingListModelData(String id) {
        return firebaseRepository.getListById(id);
    }

    public void sendList(ShoppingList shoppingList) {
        firebaseRepository.insertList(shoppingList);
    }
}
