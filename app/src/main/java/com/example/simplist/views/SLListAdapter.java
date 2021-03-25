package com.example.simplist.views;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplist.R;
import com.example.simplist.models.ShoppingList;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SLListAdapter extends RecyclerView.Adapter<SLListAdapter.ShoppingListListViewHolder> {

    private ArrayList<ShoppingList> shoppingLists;
    private ViewHolderListener listener;

    public SLListAdapter(ViewHolderListener listener) {
        this.listener = listener;
    }

    public void setLists(ArrayList<ShoppingList> shoppingLists){
        this.shoppingLists = shoppingLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list, parent, false);
        ShoppingListListViewHolder shoppingListListViewHolder = new ShoppingListListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(shoppingListListViewHolder.getAdapterPosition(), view);
            }
        });
        return shoppingListListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListListViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);
        holder.title.setText(shoppingList.getTitle());
        Date date = shoppingList.getDate().toDate();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm, dd/MM/yyyy");
        holder.date.setText(formatter.format(date));
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

    private void deleteItem(int position, View view) {
        String message = String.format("Removing list at position: %s with name %s", position, shoppingLists.get(position).getTitle());
        Log.d("SSListAdapter", message);

        String title = shoppingLists.get(position).getTitle();
        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snack.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.addCellOnClick(position, title);
            }
        });
        snack.show();
        listener.deleteCellOnClick(position);
    }

    interface ViewHolderListener {
        public void deleteCellOnClick(int position);
        public void addCellOnClick(int position, String title);
    }
}
