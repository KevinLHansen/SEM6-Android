package com.example.simplist.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplist.R;
import com.example.simplist.models.ShoppingList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShoppingListListAdapter extends RecyclerView.Adapter<ShoppingListListAdapter.ShoppingListListViewHolder> {

    Context context;
    ArrayList<ShoppingList> shoppingLists;

    public ShoppingListListAdapter(Context context, ArrayList<ShoppingList> shoppingLists) { // INSERT DATA FROM DATABASE IN ARGS
        this.context = context;
        this.shoppingLists = shoppingLists;
    }

    @NonNull
    @Override
    public ShoppingListListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shopping_list, parent, false);
        return new ShoppingListListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListListViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);
        holder.title.setText(shoppingList.getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.date.setText(formatter.format(shoppingList.getDate()));
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public class ShoppingListListViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;

        public ShoppingListListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_txt);
            date = itemView.findViewById(R.id.date_txt);
        }
    }
}
