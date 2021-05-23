package com.example.simplist.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;


import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplist.R;

import com.example.simplist.databinding.ActivityMainBinding;
import com.example.simplist.models.ShoppingList;

import com.example.simplist.receivers.NetworkReceiver;
import com.example.simplist.receivers.NetworkReceiverInterface;

import com.example.simplist.viewmodels.*;


import java.util.List;


public class MainActivity extends AppCompatActivity implements ShoppingListAdapter.ViewHolderListener, NetworkReceiverInterface {

    ActivityMainBinding mBinding;
    SwipeRefreshLayout swipeContainer;
    static RecyclerView recyclerView;
    ShoppingListAdapter adapter;
    ShoppingListViewModel shoppingListViewModel;
    NetworkReceiver rcv;
    static AlertDialog.Builder builder;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setLifecycleOwner(this);

        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        adapter = new ShoppingListAdapter(this);

        swipeContainer = findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLists();
            }
        });

        recyclerView = findViewById(R.id.shoppingListList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mBinding.shoppingListList.setLayoutManager(llm);

        DividerItemDecoration separator = new DividerItemDecoration(this, llm.getOrientation());
        mBinding.shoppingListList.addItemDecoration(separator);

        mBinding.shoppingListList.setAdapter(adapter);
        shoppingListViewModel.getShoppingListsModelData().observe(this, list -> {
            adapter.setLists(list);
        });

        // Broadcast Receiver for network changes
        rcv = new NetworkReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(rcv, filter);

        // Initiate builder
        builder = new AlertDialog.Builder(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(rcv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shoppingListViewModel.getShoppingListsModelData();
    }

    public void addList(View view) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }

    public void refreshLists () {
            swipeContainer.setRefreshing(true);
            shoppingListViewModel.getShoppingListsModelData().observe(this, new Observer<List<ShoppingList>>() {
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
        shoppingListViewModel.deleteList(position);
    }

    @Override
    public void addCellOnClick(int position, String title) {
        shoppingListViewModel.addList(position, title);
    }

    @Override
    public void networkOn() {
        Toast.makeText(this, R.string.toast_network_on, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkOff() {
        builder.setTitle(R.string.popup_network_title).setMessage(R.string.popup_network_message);
        builder.setNeutralButton(R.string.popup_button_txt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog popup = builder.create();
        popup.setCanceledOnTouchOutside(false);
        popup.show();
    }
}