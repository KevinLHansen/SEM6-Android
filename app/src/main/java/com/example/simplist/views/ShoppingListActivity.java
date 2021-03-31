package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.EditText;

import com.example.simplist.R;
import com.example.simplist.db.Constants;
import com.example.simplist.models.ShoppingList;

public class ShoppingListActivity extends AppCompatActivity {

    EditText listTitleText;
    RecyclerView recyclerView;
    ShoppingListItemAdapter shoppingListItemAdapter;
    ShoppingList shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Intent intent = getIntent();
        shoppingList = intent.getParcelableExtra(Constants.EXTRA_SHOPPINGLIST);
        shoppingListItemAdapter = new ShoppingListItemAdapter();
        shoppingListItemAdapter.setList(shoppingList);
        listTitleText = findViewById(R.id.listTitleEditText);
        listTitleText.setText(shoppingList.getTitle());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shoppingListItemAdapter);


    }
}