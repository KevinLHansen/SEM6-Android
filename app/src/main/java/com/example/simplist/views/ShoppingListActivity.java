package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.Button;
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

        shoppingListItemAdapter = new ShoppingListItemAdapter();
        listTitleText = findViewById(R.id.listTitleEditText);

        Intent intent = getIntent();
        if ((shoppingList = intent.getParcelableExtra(Constants.EXTRA_SHOPPINGLIST)) != null){
            shoppingListItemAdapter.setList(shoppingList);
            listTitleText.setText(shoppingList.getTitle());
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shoppingListItemAdapter);


    }
}