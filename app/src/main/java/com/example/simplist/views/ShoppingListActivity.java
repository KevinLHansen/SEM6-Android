package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.simplist.util.Constants;
import com.example.simplist.models.ShoppingList;
import com.example.simplist.viewmodels.DetailViewModel;


public class ShoppingListActivity extends AppCompatActivity {

    EditText listTitleText;
    RecyclerView recyclerView;
    ShoppingListItemAdapter adapter;
    DetailViewModel detailViewModel;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        adapter = new ShoppingListItemAdapter();
        listTitleText = findViewById(R.id.listTitleEditText);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        id = intent.getStringExtra(Constants.EXTRA_SHOPPINGLIST);
        if (id != null) {
            detailViewModel.getShoppingListModelData(id).observe(this, list -> {
                listTitleText.setText(list.getTitle());
                adapter.setList(list);
                Log.d("WTF", list.toString());
            });
        }
        else {
            Toast.makeText(this, "fak", Toast.LENGTH_SHORT).show();
        }

    }

    public void newItem(View view) {
        //adapter.newItem();
        detailViewModel.newItem();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        for (int i = 0; i < adapter.getItemCount(); i++){
            detailViewModel.addItem();
        }

        if(id != null) {
            detailViewModel.updateList(id);
        } else {
            detailViewModel.sendList();
        }
    }
}