package com.example.simplist.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.simplist.models.ShoppingList;
import com.example.simplist.repositories.FirebaseRepository;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListViewModel extends ViewModel {

    private final String TAG = "ViewModel";
    private FirebaseRepository firebaseRepository = FirebaseRepository.getInstance();

    private MutableLiveData<List<ShoppingList>> shoppingListsModelData;
    private List<ShoppingList> slList = new ArrayList<ShoppingList>();

    public LiveData<List<ShoppingList>> getShoppingListsModelData() {
        if (shoppingListsModelData == null) {
            shoppingListsModelData = new MutableLiveData<>();
        }
        firebaseRepository.getAllListsData(shoppingListsModelData);
        return shoppingListsModelData;
    }


    public void deleteList(int position) {
        shoppingListsModelData.getValue().remove(position);
        shoppingListsModelData.setValue(shoppingListsModelData.getValue());
        Log.d(TAG, String.valueOf(shoppingListsModelData.hasObservers()));
    }

    public void addList(int position, String title) {
        shoppingListsModelData.getValue().add(position, new ShoppingList(title));
        shoppingListsModelData.setValue(shoppingListsModelData.getValue());
    }
}
