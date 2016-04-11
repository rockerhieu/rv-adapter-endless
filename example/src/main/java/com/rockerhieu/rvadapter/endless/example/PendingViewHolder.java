package com.rockerhieu.rvadapter.endless.example;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Yahya Bayramoglu on 07/04/16.
 */
public class PendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public PendingViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("PendingViewHolder", "Custom ViewHolder clicked.");
    }

}