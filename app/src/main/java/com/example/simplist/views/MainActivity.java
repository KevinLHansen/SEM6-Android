package com.example.simplist.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplist.R;

import com.example.simplist.models.ShoppingList;

import com.example.simplist.receivers.NetworkReceiver;

import com.example.simplist.viewmodels.*;


import java.util.List;


public class MainActivity extends AppCompatActivity implements SLListAdapter.ViewHolderListener {

    SwipeRefreshLayout swipeContainer;
    RecyclerView recyclerView;
    SLListAdapter adapter;
    ShoppingListViewModel slvm;
    NetworkReceiver rcv;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                refreshLists();
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

        // Broadcast Receiver for network changes
        rcv = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(rcv, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(rcv);
    }

    public void addList(View view) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }

    public void refreshLists () {
            swipeContainer.setRefreshing(true);
            slvm.getShoppingListsModelData().observe(this, new Observer<List<ShoppingList>>() {
                @Override
                public void onChanged(List<ShoppingList> shoppingLists) {
                    swipeContainer.setRefreshing(false);
                }
            });
            //slvm.getShoppingListsModelData();
    }

    @Override
    public void launchActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void deleteCellOnClick(int position) {
        slvm.deleteList(position);
    }

    @Override
    public void addCellOnClick(int position, String title) {
        slvm.addList(position, title);
    }

    public void onNetworkChange(boolean hasNetwork) {
        if (hasNetwork) {
            recyclerView.setEnabled(true);
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                recyclerView.getChildAt(i).setEnabled(true);
            }
        } else {
            recyclerView.setEnabled(false);
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                recyclerView.getChildAt(i).setEnabled(false);
            }
        }
    }
}