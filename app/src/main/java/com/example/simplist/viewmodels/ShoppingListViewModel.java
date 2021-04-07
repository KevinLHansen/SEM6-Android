package com.example.simplist.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.simplist.models.ShoppingList;
import com.example.simplist.util.FirebaseRepository;
import java.util.ArrayList;
import java.util.List;

public class ShoppingListViewModel extends ViewModel implements FirebaseRepository.OnFireStoreTaskComplete {

    private final String TAG = "ViewModel";
    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    private MutableLiveData<List<ShoppingList>> shoppingListsModelData;
    private List<ShoppingList> slList = new ArrayList<ShoppingList>();

    public LiveData<List<ShoppingList>> getShoppingListsModelData() {
        if (shoppingListsModelData == null) {
            shoppingListsModelData = new MutableLiveData<>();
            firebaseRepository.getAllListsData();
        }
        return shoppingListsModelData;
    }

    private void loadLists() {

        //lists.setValue(slList);

//        db.collection(Constants.SHOPPING_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, document.getId() + " => " + document.getData());
//                        slList.add(document.toObject(ShoppingList.class));
//                    }
//                    lists.setValue(slList);
//                } else {
//                    Log.w(TAG, "Error getting documents.", task.getException());
//                }
//            }
//        });
//
//        Log.d(TAG, String.valueOf(lists.hasObservers()));

//        ShoppingList sl1 = new ShoppingList("friday");
//        ShoppingList sl2 = new ShoppingList("saturday");
//
//        sl1.addItem(new ShoppingListItem("banana", "2 pcs"));
//        sl1.addItem(new ShoppingListItem("flour", "500g"));
//
//        sl2.addItem(new ShoppingListItem("frozen pizza", "3"));
//        sl2.addItem(new ShoppingListItem("milk", "2 liters"));
//        sl2.addItem(new ShoppingListItem("toffee cups", "5 packs"));
//
//        slList.add(sl1);
//        slList.add(sl2);
//
//        lists.setValue(slList);
//        Log.d("ViewModel", coll.getData().toString());
//        lists.setValue(coll.getData());
    }

    public void deleteList(int position) {
        shoppingListsModelData.getValue().remove(position);
        // Does the same as notifyDataSetChanged()
        // So why this?
        shoppingListsModelData.setValue(shoppingListsModelData.getValue());
        Log.d(TAG, String.valueOf(shoppingListsModelData.hasObservers()));
    }

    public void addList(int position, String title) {
        shoppingListsModelData.getValue().add(position, new ShoppingList(title));
        // Does the same as notifyDataSetChanged()
        // So why this?
        shoppingListsModelData.setValue(shoppingListsModelData.getValue());
    }

    @Override
    public void shoppingListDataAdded(List<ShoppingList> shoppingListModelsList) {
        shoppingListsModelData.setValue(shoppingListModelsList);
    }

    @Override
    public void onError(Exception e) {

    }
}
