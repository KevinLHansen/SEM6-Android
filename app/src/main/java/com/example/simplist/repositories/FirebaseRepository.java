package com.example.simplist.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.simplist.models.ShoppingList;
import com.example.simplist.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FirebaseRepository {

    private static final String TAG = "FirebaseRepository";

    private static FirebaseRepository instance = null;

    //private OnFireStoreTaskComplete onFireStoreTaskComplete;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference shoppingRef = db.collection(Constants.SHOPPING_COLLECTION);


    /*public FirebaseRepository(OnFireStoreTaskComplete onFireStoreTaskComplete) {
        this.onFireStoreTaskComplete = onFireStoreTaskComplete;
        this.instance = this;
    }*/

    public static FirebaseRepository getInstance() {
        if (instance == null){
            instance = new FirebaseRepository();
        }
        return instance;
    }

    // Fetches all ShoppingLists from firebase
    public MutableLiveData<List<ShoppingList>> getAllListsData(MutableLiveData data){
        shoppingRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<ShoppingList> temp;

                    // try-catch for processing the result if result is somehow not a valid object
                    try {
                        temp = task.getResult().toObjects(ShoppingList.class);
                        Collections.sort(temp, (shoppingList1, shoppingList2) -> {
                            if (shoppingList1.getDate().after(shoppingList2.getDate()))
                                return -1;
                            else if (shoppingList1.getDate().before(shoppingList2.getDate()))
                                return 1;
                            else
                                return 0;
                        });
                        data.setValue(temp);
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                        Log.d(TAG, "Failed to create objects from firebase result");
                    }
                }
            }
        });
        return data;
    }

//    public void getAllListsData(OnFireStoreTaskComplete onFireStoreTaskComplete) {
//        shoppingRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    onFireStoreTaskComplete.shoppingListDataAdded(task.getResult().toObjects(ShoppingList.class));
//                } else {
//                    onFireStoreTaskComplete.onError(task.getException());
//                }
//            }
//        });
//    }

    // Retrives a single ShoppingList based on id
    public MutableLiveData<ShoppingList> getListById(String id) {
        MutableLiveData<ShoppingList> data = new MutableLiveData<>();
        shoppingRef.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                data.setValue(task.getResult().toObject(ShoppingList.class));
                Log.d(TAG, "Received list");
            }
        });
        return data;
    }

    // Update a existing list from id
    public void insertList(String id, ShoppingList shoppingList) {
        shoppingRef.document(id).set(shoppingList).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });

    }

    // Add a new list
    public void insertList(ShoppingList shoppingList) {
        shoppingRef.add(shoppingList).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
    }

    // Delete list from id
    public void deleteList(String id){
        shoppingRef.document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.w(TAG, "Error deleting document", e);
            }
        });
    }

    public interface OnFireStoreTaskComplete {
        void shoppingListDataAdded(List<ShoppingList> shoppingListModelsList);
        void onError(Exception e);
    }
}
