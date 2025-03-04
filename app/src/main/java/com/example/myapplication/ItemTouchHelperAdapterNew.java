package com.example.myapplication;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapterNew {
    void onItemClear(RecyclerView.ViewHolder viewHolder);

    void onItemDissmiss(RecyclerView.ViewHolder viewHolder);

    void onItemMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2);

    void onItemSelect(RecyclerView.ViewHolder viewHolder);
}
