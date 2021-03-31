package com.example.simplist.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import com.example.simplist.R;
import com.example.simplist.receivers.NetworkReceiver;
import com.example.simplist.viewmodels.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity implements SLListAdapter.ViewHolderListener {

    FloatingActionButton addButton;
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

        addButton = findViewById(R.id.floatingAddButton);
        recyclerView = findViewById(R.id.shoppingListList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        DividerItemDecoration separator = new DividerItemDecoration(this, llm.getOrientation());
        recyclerView.addItemDecoration(separator);

        recyclerView.setAdapter(adapter);
        slvm.getLists().observe(this, list -> {
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

    @Override
    public void deleteCellOnClick(int position) {
        slvm.deleteList(position);
    }

    @Override
    public void addCellOnClick(int position, String title) {
        slvm.addList(position, title);
    }
}