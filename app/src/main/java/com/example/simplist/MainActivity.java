package com.example.simplist;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.example.simplist.db.ShoppingListCollection;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView shoppingListList;
    ArrayList<ShoppingList> shoppingLists;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShoppingListCollection coll = new ShoppingListCollection();

        shoppingLists = new ArrayList<ShoppingList>();

        ShoppingList sl1 = new ShoppingList("friday");
        ShoppingList sl2 = new ShoppingList("saturday");

        sl1.addItem(new ShoppingListItem("banana", "2 pcs"));
        sl1.addItem(new ShoppingListItem("flour", "500g"));

        sl2.addItem(new ShoppingListItem("frozen pizza", "3"));
        sl2.addItem(new ShoppingListItem("milk", "2 liters"));
        sl2.addItem(new ShoppingListItem("toffee cups", "5 packs"));

        shoppingLists.add(sl1);
        shoppingLists.add(sl2);

        shoppingListList = findViewById(R.id.shoppingListList);
        ShoppingListListAdapter adapter = new ShoppingListListAdapter(this, shoppingLists);
        shoppingListList.setAdapter(adapter);
        shoppingListList.setLayoutManager(new LinearLayoutManager(this));

        coll.SendData(sl2);
        coll.GetData();
    }
}