package com.rockerhieu.rvadapter.endless;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rockerhieu.rvadapter.RecyclerViewAdapterWrapper;

import java.util.concurrent.atomic.AtomicBoolean;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * @author rockerhieu on 7/6/15.
 */
public class EndlessRecyclerViewAdapter extends RecyclerViewAdapterWrapper {
    public static final int TYPE_PENDING = 999;
    private final AtomicBoolean keepOnAppending;
    private final AtomicBoolean dataPending;
    private final RequestToLoadMoreListener requestToLoadMoreListener;
    private boolean shouldNotifyAdapter = false;
    private ViewHolder pendingViewHolder;
    private int pendingViewId = R.layout.item_loading;

    public EndlessRecyclerViewAdapter(Adapter wrapped, RequestToLoadMoreListener requestToLoadMoreListener) {
        this(wrapped, requestToLoadMoreListener, true);
    }

    public EndlessRecyclerViewAdapter(Adapter wrapped, RequestToLoadMoreListener requestToLoadMoreListener,
          boolean keepOnAppending) {
        super(wrapped);
        this.requestToLoadMoreListener = requestToLoadMoreListener;
        this.keepOnAppending = new AtomicBoolean(keepOnAppending);
        dataPending = new AtomicBoolean(false);
    }

    /**
     * Set layout Id in order to inflate your custom pending view.
     * If not set, default is R.layout.item_loading
     */
    public void setPendingViewId(@LayoutRes int layoutId) {
        if (pendingViewHolder != null) {
            throw new IllegalStateException("Cannot change pendingViewId once ViewHolder is created.");
        }
        pendingViewId = layoutId;
    }

    /**
     * Set your own pendingViewHolder, when custom viewHolder is provided that will have priority over
     * {@linkplain EndlessRecyclerViewAdapter#setPendingViewId(int)}
     */
    public void setPendingViewHolder(ViewHolder holder) {
        if (pendingViewHolder != null) {
            throw new IllegalStateException("Cannot change pendingViewHolder once it is created.");
        }
        pendingViewHolder = holder;
    }

    /**
     * Instead of calling notifyDataSetChanged, can be called notifyInsert or notifyRemove
     * This flag is false as default, you need to activate
     * if you do not intent to notify adapter yourself
     */
    public void notifyAutomatically(boolean enable) {
        shouldNotifyAdapter = enable;
    }

    /**
     * Let the adapter know that data is load and ready to view.
     *
     * @param keepOnAppending whether the adapter should request to load more when scrolling to the bottom.
     */
    public void onDataReady(boolean keepOnAppending) {
        dataPending.set(false);
        setKeepOnAppending(keepOnAppending);
    }

    /**
     * Forces adapter to display pending view
     */
    public void restartAppending() {
        dataPending.set(false);
        setKeepOnAppending(true);
    }

    private void setKeepOnAppending(boolean newValue) {
        keepOnAppending.set(newValue);
        if (shouldNotifyAdapter) {
            getWrappedAdapter().notifyDataSetChanged();
        } else {
            // Notify only pendingView to show\hide self
            notifyItemChanged(getWrappedAdapter().getItemCount());
        }
    }

    private ViewHolder getPendingViewHolder(ViewGroup parent) {
        if (pendingViewHolder == null) {
            pendingViewHolder = new PendingViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(pendingViewId, parent, false));
        }
        return pendingViewHolder;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (keepOnAppending.get() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getWrappedAdapter().getItemCount()) {
            return TYPE_PENDING;
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PENDING) {
            return getPendingViewHolder(parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_PENDING) {
            if (!dataPending.get()) {
                dataPending.set(true);
                requestToLoadMoreListener.onLoadMoreRequested();
            }
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public interface RequestToLoadMoreListener {
        /**
         * The adapter requests to load more data.
         */
        void onLoadMoreRequested();
    }

    static class PendingViewHolder extends ViewHolder {

        public PendingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
