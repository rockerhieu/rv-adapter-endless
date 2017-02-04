package com.rockerhieu.rvadapter.endless.example.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleStringAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> mValues;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;
        public TextView mTextView;

        public ItemViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    public SimpleStringAdapter(String[] strings) {
        mValues = new ArrayList<>();
        if (strings != null) {
            Collections.addAll(mValues, strings);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mBoundString = mValues.get(position);
            itemViewHolder.mTextView.setText(position + ":" + mValues.get(position));
            itemViewHolder.mTextView.setMinHeight((50 + mValues.get(position).length() * 10));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void appendItems(List<String> items) {
        mValues.addAll(items);
        notifyItemRangeInserted(getItemCount() + 1, items.size());
    }


    public void clear() {
        mValues.clear();
        notifyDataSetChanged();
    }
}
