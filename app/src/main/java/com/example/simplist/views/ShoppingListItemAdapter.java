package com.example.simplist.views;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplist.R;
import com.example.simplist.models.ShoppingList;
import com.example.simplist.models.ShoppingListItem;

import java.util.ArrayList;

public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ShoppingListItemViewHolder> {

    private ShoppingList shoppingList;
    private ArrayList<ShoppingListItem> items;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setList(ShoppingList shoppingList){
        this.shoppingList = shoppingList;
        //items = new ArrayList<ShoppingListItem>(shoppingList.getItems().values());
    }

    @NonNull
    @Override
    public ShoppingListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);
        ShoppingListItemViewHolder shoppingListItemViewHolder = new ShoppingListItemViewHolder(view);
        return shoppingListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (shoppingList != null){
            return shoppingList.getItems().size();
        }
        return 0;
    }

    public class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {

        EditText name, amount;

        public ShoppingListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameEditText);
            amount = itemView.findViewById(R.id.amountEditText);
        }
    }
}
