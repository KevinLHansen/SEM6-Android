package com.example.simplist.views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.simplist.R;
import com.example.simplist.db.Constants;
import com.example.simplist.models.ShoppingList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SLListAdapter extends RecyclerView.Adapter<SLListAdapter.ShoppingListListViewHolder> {

    private List<ShoppingList> shoppingLists;
    private ViewHolderListener listener;

    public SLListAdapter(ViewHolderListener listener) {
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
        ShoppingListListViewHolder shoppingListListViewHolder = new ShoppingListListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadList(shoppingListListViewHolder.getAdapterPosition(), view);
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
        if (shoppingLists != null) {
            return shoppingLists.size();
        }
        return 0;
    }

    public class ShoppingListListViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;

        public ShoppingListListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_txt);
            date = itemView.findViewById(R.id.date_txt);

        }
    }

    private void loadList(int position, View view) {
        Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);
        intent.putExtra(Constants.EXTRA_TITLE, shoppingLists.get(position).getTitle());

        ArrayList<String> names = new ArrayList<String>();
        names.addAll(shoppingLists.get(position).getItems().keySet());
        intent.putStringArrayListExtra(Constants.EXTRA_NAME, names);

        ArrayList<String> amounts = new ArrayList<String>();
        amounts.addAll(shoppingLists.get(position).getItems().values());
        intent.putStringArrayListExtra(Constants.EXTRA_AMOUNT, amounts);
        listener.launchActivity(intent);
//        String message = String.format("Removing list at position: %s with name %s", position, shoppingLists.get(position).getTitle());
//        Log.d("SSListAdapter", message);
//
//        String title = shoppingLists.get(position).getTitle();
//        Snackbar snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
//        snack.setAction("Undo", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.addCellOnClick(position, title);
//            }
//        });
//        snack.show();
//        listener.deleteCellOnClick(position);
    }

    interface ViewHolderListener {
        public void launchActivity(Intent intent);
        public void deleteCellOnClick(int position);
        public void addCellOnClick(int position, String title);
    }
}
