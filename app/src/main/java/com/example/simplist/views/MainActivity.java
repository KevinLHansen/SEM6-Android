package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.simplist.R;
import com.example.simplist.models.ShoppingList;
import com.example.simplist.viewmodels.ShoppingListViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MainActivity context;
    RecyclerView recyclerView;
    ShoppingListListAdapter adapter;
    ShoppingListViewModel slvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        slvm = new ViewModelProvider(this).get(ShoppingListViewModel.class);

        recyclerView = findViewById(R.id.shoppingListList);
        ShoppingListListAdapter adapter = new ShoppingListListAdapter(this, slvm.getLists().getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        slvm.getLists().observe(this, shoppingListUpdateObserver);

    }

    Observer<ArrayList<ShoppingList>> shoppingListUpdateObserver = new Observer<ArrayList<ShoppingList>>() {

        @Override
        public void onChanged(ArrayList<ShoppingList> shoppingLists) {
            adapter = new ShoppingListListAdapter(context, shoppingLists);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }
    };
}