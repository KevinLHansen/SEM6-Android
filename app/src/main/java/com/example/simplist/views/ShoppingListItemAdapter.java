package com.example.simplist.views;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private List<ShoppingListItem> items;
    private boolean isEdit;


    public ShoppingListItemAdapter(){
        items = new ArrayList<>();
        isEdit = false;
    }

    public List<ShoppingListItem> getList(){
        return items;
    }

    public void setList(List<ShoppingListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    // Hide/unhide remove buttons on items
    public void toggleEdit(){
        isEdit = !isEdit;
        notifyDataSetChanged();
    }

    public boolean getIsEdit(){
        return isEdit;
    }


    @NonNull
    @Override
    public ShoppingListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShoppingListItemBinding itemBinding = ShoppingListItemBinding.inflate(inflater, parent, false);
        return new ShoppingListItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemViewHolder holder, int position) {
        holder.bind(items.get(position));
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });
        if (!isEdit)
            holder.remove.setVisibility(View.GONE);
        else
            holder.remove.setVisibility(View.VISIBLE);
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
        Button remove;

        public ShoppingListItemViewHolder(ShoppingListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ShoppingListItem item) {
            this.name = binding.nameEditText;
            this.amount = binding.amountEditText;
            this.remove = binding.removeItemButton;
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
