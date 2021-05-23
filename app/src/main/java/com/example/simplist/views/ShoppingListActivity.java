package com.example.simplist.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.simplist.R;
import com.example.simplist.databinding.ActivityShoppingListBinding;
import com.example.simplist.util.Constants;
import com.example.simplist.models.ShoppingList;
import com.example.simplist.viewmodels.DetailViewModel;


public class ShoppingListActivity extends AppCompatActivity {

    private static final String TAG = "ShoppingListActivity";
    ActivityShoppingListBinding mBinding;
    ShoppingListItemAdapter adapter;
    DetailViewModel detailViewModel;
    String id;
    Boolean isNewList = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_list);
        mBinding.setLifecycleOwner(this);

        mBinding.setViewModel(detailViewModel);

        // Get intent, if null list is new
        Intent intent = getIntent();
        id = intent.getStringExtra(Constants.EXTRA_SHOPPINGLIST);
        if (id != null) {
            isNewList = false;
            detailViewModel = new DetailViewModel(id);
        }
        else {
            detailViewModel = new DetailViewModel();
            Toast.makeText(this, "fak", Toast.LENGTH_SHORT).show();
        }
        adapter = new ShoppingListItemAdapter();
        detailViewModel.getItems().observe(this, items -> adapter.setList(items));
        mBinding.setViewModel(detailViewModel);
        //mBinding.setShoppingList(detailViewModel.getShoppingListModelData());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(adapter);
        mBinding.executePendingBindings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shopping_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_remove:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.popup_delete_title)
                        .setMessage(getString(R.string.popup_delete_message, detailViewModel.getTitle().getValue()))
                        .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!isNewList)
                                    detailViewModel.removeList(id);
                                finish();
                            }
                        }).setNegativeButton(R.string.popup_no, null).show();
                return true;
            case R.id.action_edit:
                adapter.toggleEdit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!isNewList) {
            detailViewModel.sendList(id, adapter.getList());
        } else {
            Log.d(TAG, "newList: " + adapter.getList());
            detailViewModel.sendList(adapter.getList());
        }
    }
}