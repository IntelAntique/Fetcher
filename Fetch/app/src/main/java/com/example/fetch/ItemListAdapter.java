package com.example.fetch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private final List<ItemList> itemLists;

    public ItemListAdapter(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemList itemList = itemLists.get(position);
        holder.tvListId.setText(String.valueOf(itemList.getListId()));
        holder.rvItems.setLayoutManager(new LinearLayoutManager(holder.rvItems.getContext()));
        holder.rvItems.setAdapter(new ItemAdapter(itemList.getItems()));
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvListId;
        public RecyclerView rvItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListId = itemView.findViewById(R.id.tvListId);
            rvItems = itemView.findViewById(R.id.rvItems);
            rvItems.addItemDecoration(new DividerItemDecoration(rvItems.getContext(), DividerItemDecoration.VERTICAL));
        }
    }
}
