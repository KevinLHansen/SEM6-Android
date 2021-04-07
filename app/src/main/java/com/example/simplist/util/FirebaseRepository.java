package com.example.simplist.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.simplist.models.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseRepository {

    private OnFireStoreTaskComplete onFireStoreTaskComplete;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference shoppingRef = db.collection("ShoppingLists");


    public FirebaseRepository(OnFireStoreTaskComplete onFireStoreTaskComplete) {
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
    }
    public void getAllListsData() {
        shoppingRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    onFireStoreTaskComplete.shoppingListDataAdded(task.getResult().toObjects(ShoppingList.class));
                } else {
                    onFireStoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface OnFireStoreTaskComplete {
        void shoppingListDataAdded(List<ShoppingList> shoppingListModelsList);
        void onError(Exception e);
    }
}
