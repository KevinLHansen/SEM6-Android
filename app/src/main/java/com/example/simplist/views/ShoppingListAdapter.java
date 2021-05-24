package com.example.simplist.views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplist.R;
import com.example.simplist.databinding.ShoppingListBinding;
import com.example.simplist.util.Constants;
import com.example.simplist.models.ShoppingList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListListViewHolder> {

    private List<ShoppingList> shoppingLists;
    private ViewHolderListener listener;

    public ShoppingListAdapter(ViewHolderListener listener) {
        this.listener = listener;
    }

    public void setLists(List<ShoppingList> shoppingLists){
        this.shoppingLists = shoppingLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.shopping_list, parent, false);
        ShoppingListBinding listBinding = ShoppingListBinding.inflate(inflater, parent, false);
        ShoppingListListViewHolder shoppingListListViewHolder = new ShoppingListListViewHolder(listBinding);
        return shoppingListListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListListViewHolder holder, int position) {
        holder.bind(shoppingLists.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadList(holder.getAdapterPosition(), view);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (shoppingLists != null) {
            return shoppingLists.size();
        }
        return 0;
    }

    public class ShoppingListListViewHolder extends RecyclerView.ViewHolder {
        private ShoppingListBinding binding;
        TextView title, date, timestamp;

        public ShoppingListListViewHolder(ShoppingListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShoppingList list){
            this.title = binding.titleTxt;
            this.date = binding.dateTxt;
            this.timestamp = binding.timestampTxt;
            binding.setList(list);
            binding.executePendingBindings();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            date.setText(dateFormatter.format(binding.getList().getDate()));
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
            timestamp.setText(timeFormatter.format(binding.getList().getDate()));
        }
    }

    // Create intent for selected list and start new activity
    private void loadList(int position, View view) {
        Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);

        intent.putExtra(Constants.EXTRA_SHOPPINGLIST, shoppingLists.get(position).getId());
        listener.launchActivity(intent);
    }

    interface ViewHolderListener {
        public void launchActivity(Intent intent);
        public void deleteCellOnClick(int position);
        public void addCellOnClick(int position, String title);
    }
}
