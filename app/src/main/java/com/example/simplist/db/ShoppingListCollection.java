package com.example.simplist.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ShoppingListCollection {

    private static final String TAG = "ShoppingListRef";
    private static final String COLLECTION = Constants.SHOPPING_COLLECTION;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map<String, Object> list = new HashMap<>();
    private Map<String, Object> items = new HashMap<>();

    public ShoppingListCollection(){
        Log.v(TAG, "ShoppingListRef Initialized");
        items.put("Banan", "2 stk");
        items.put("Isterninger", "1 stk");

        list.put("title", "test");
        list.put("items", items);

    }





    public void AddListToDatabase(){

        db.collection("ShoppingLists")
                .add(list)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
