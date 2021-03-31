package com.example.simplist.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.simplist.R;
import com.example.simplist.db.Constants;

public class ShoppingListActivity extends AppCompatActivity {

    EditText listTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constants.EXTRA_TITLE);
        listTitleText = findViewById(R.id.listTitleEditText);
        listTitleText.setText(title);
    }
}