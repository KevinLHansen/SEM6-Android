package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
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
    ShoppingList shoppingList;
    DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        adapter = new ShoppingListItemAdapter();
        listTitleText = findViewById(R.id.listTitleEditText);
        /*if ((shoppingList = intent.getParcelableExtra(Constants.EXTRA_SHOPPINGLIST)) != null){
            shoppingListItemAdapter.setList(shoppingList);
            listTitleText.setText(shoppingList.getTitle());
        }*/

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        String id = intent.getStringExtra(Constants.EXTRA_SHOPPINGLIST);
        if (id != null) {
            //listTitleText.setText(detailViewModel.getShoppingListModelData(id));
            detailViewModel.getShoppingListModelData(id).observe(this, list -> {
                listTitleText.setText(list.getTitle());
                adapter.setList(list);
            });
        }
        else {
            Toast.makeText(this, "fak", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}