package com.example.simplist.db;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.simplist.models.ShoppingList;
import com.example.simplist.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ShoppingListCollection {

    private static final String TAG = "ShoppingListColl";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String, Object> CreateList(ShoppingList shoppingList){

        Map<String, Object> list = new HashMap<>();
        Map<String, Object> items = new HashMap<>();

        shoppingList.getItems().forEach((title, amount) -> {
            items.put(title, amount);
        });

        list.put("title", shoppingList.getTitle());
        list.put("date", shoppingList.getDate());
        list.put("items", items);

        return list;
    }


    public ArrayList<ShoppingList> getData() {
        ArrayList<ShoppingList> data = new ArrayList<>();

        // Request Data from ShoppingLists in Firebase
        db.collection(Constants.SHOPPING_COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Convert Data to ShoppingList and add to data ArrayList
                        ShoppingList list = document.toObject(ShoppingList.class);
                        Log.d("debug", "Received shoppinglist with title: " + list.getTitle());
                        data.add(list);
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        Log.d("debug", "returning: " + data);

        return data;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendData(ShoppingList shoppingList){
                db.
                collection(Constants.SHOPPING_COLLECTION)
                .add(CreateList(shoppingList))
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
