package com.example.simplist.views;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplist.R;
import com.example.simplist.databinding.ShoppingListItemBinding;
import com.example.simplist.models.ShoppingList;
import com.example.simplist.models.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListItemAdapter extends RecyclerView.Adapter<ShoppingListItemAdapter.ShoppingListItemViewHolder> {

    private static final String TAG = "ItemAdapter";

    //private MutableLiveData<List<ShoppingListItem>> items;
    private List<ShoppingListItem> items;

//    public ShoppingListItemAdapter(ShoppingList shoppingList) {
//        this.shoppingList = shoppingList;
//        notifyDataSetChanged();
//    }

    public ShoppingListItemAdapter(){
        items = new ArrayList<>();
    }

//    public ShoppingListItemAdapter(MutableLiveData<List<ShoppingListItem>> items) {
//        this.items = items;
//    }

    public List<ShoppingListItem> getList(){
        return items;
    }

    public void setList(List<ShoppingListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
//    public void setList(MutableLiveData<List<ShoppingListItem>> items){
//        this.items = items;
//        notifyDataSetChanged();
//    }


    @NonNull
    @Override
    public ShoppingListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShoppingListItemBinding itemBinding = ShoppingListItemBinding.inflate(inflater, parent, false);
        return new ShoppingListItemViewHolder(itemBinding);
    }


    // FIX: Memory leak
    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemViewHolder holder, int position) {
        holder.bind(items.get(position));
        Log.d(TAG, "ViewHolder bound!");
    }

    @Override
    public int getItemCount() {
        if (items != null){
            return items.size();
        }
        return 0;
    }

    public class ShoppingListItemViewHolder extends RecyclerView.ViewHolder {
        private ShoppingListItemBinding binding;
        EditText name, amount;

        public ShoppingListItemViewHolder(ShoppingListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShoppingListItem item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
