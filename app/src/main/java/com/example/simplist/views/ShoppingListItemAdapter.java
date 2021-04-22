package com.example.simplist.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplist.R;
import com.example.simplist.models.ShoppingList;

import java.util.ArrayList;

public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ShoppingListItemViewHolder> {

    private ShoppingList shoppingList;

    public void setList(ShoppingList shoppingList){
        this.shoppingList = shoppingList;
        notifyDataSetChanged();
    }

    public void newItem(){
        shoppingList.addItem("", "");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list_item, parent, false);
        ShoppingListItemViewHolder shoppingListItemViewHolder = new ShoppingListItemViewHolder(view);
        return shoppingListItemViewHolder;
    }


    // FIX: Memory leak
    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemViewHolder holder, int position) {
        holder.name.setText(shoppingList.getItems().get(position).getTitle());
        holder.amount.setText(shoppingList.getItems().get(position).getAmount());
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
