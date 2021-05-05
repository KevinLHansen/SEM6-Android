package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    EditText listTitleText;
    RecyclerView recyclerView;
    ShoppingListItemAdapter adapter;
    DetailViewModel detailViewModel;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_list);
        mBinding.setLifecycleOwner(this);

        mBinding.setViewModel(detailViewModel);

        Intent intent = getIntent();
        id = intent.getStringExtra(Constants.EXTRA_SHOPPINGLIST);
        if (id != null) {
            detailViewModel = new DetailViewModel(id);

            //mBinding.setShoppingList(detailViewModel.getShoppingListModelData(id));
//            detailViewModel.getShoppingListModelData(id).observe(this, list -> {
//                mBinding.setViewmodel();
//                Log.d("WTF", list.toString());
//            });
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
    public void onBackPressed() {
        super.onBackPressed();
        if (id != null) {
            detailViewModel.sendList(id, adapter.getList());
        } else {
            Log.d(TAG, "bruh: " + adapter.getList());
            detailViewModel.sendList(adapter.getList());
        }
//        for (int i = 0; i < adapter.getItemCount(); i++){
//            detailViewModel.addItem();
//        }

//        if(id != null) {
//            detailViewModel.updateList(id);
//        } else {
//            detailViewModel.sendList();
//        }
    }
}