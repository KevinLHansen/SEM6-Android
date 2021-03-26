package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplist.R;
import com.example.simplist.viewmodels.*;


public class MainActivity extends AppCompatActivity implements SLListAdapter.ViewHolderListener {

    SwipeRefreshLayout swipeContainer;
    RecyclerView recyclerView;
    SLListAdapter adapter;
    ShoppingListViewModel slvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slvm = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        adapter = new SLListAdapter(this);

        swipeContainer = findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                slvm.getShoppingListsModelData();
                swipeContainer.setRefreshing(false);
            }
        });

        recyclerView = findViewById(R.id.shoppingListList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        DividerItemDecoration separator = new DividerItemDecoration(this, llm.getOrientation());
        recyclerView.addItemDecoration(separator);

        recyclerView.setAdapter(adapter);
        slvm.getShoppingListsModelData().observe(this, list -> {
            adapter.setLists(list);
        });
    }

    public void addList(View view) {
        Toast.makeText(this, "Lolle", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteCellOnClick(int position) {
        slvm.deleteList(position);
    }

    @Override
    public void addCellOnClick(int position, String title) {
        slvm.addList(position, title);
    }
}