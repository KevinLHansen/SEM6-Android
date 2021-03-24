package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.simplist.R;
import com.example.simplist.viewmodels.ShoppingListViewModel;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SLListAdapter adapter;
    ShoppingListViewModel slvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slvm = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        adapter = new SLListAdapter();

        recyclerView = findViewById(R.id.shoppingListList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        slvm.getLists().observe(this, list -> {
            adapter.setLists(list);
        });
    }
}