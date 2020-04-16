package com.zx.zxutils.other;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

import static android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;

/**
 * Create By Xiangb On 2017/6/1
 * 功能：recylerview的点击事件
 */
public class ZXItemClickSupport {
    private final RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                ViewHolder holder = recyclerView.getChildViewHolder(view);
                onItemClickListener.onItemClicked(recyclerView, holder.getAdapterPosition(), view);
            }
        }
    };
    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClickListener != null) {
                ViewHolder holder = recyclerView.getChildViewHolder(view);
                return onItemLongClickListener.onItemLongClicked(recyclerView, holder.getAdapterPosition(), view);
            }
            return false;
        }
    };
    private OnChildAttachStateChangeListener attachListener = new OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (onItemClickListener != null) {
                view.setOnClickListener(onClickListener);
            }
            if (onItemLongClickListener != null) {
                view.setOnLongClickListener(onLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
        }
    };

    private ZXItemClickSupport(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setTag(recyclerView.getId());
        this.recyclerView.addOnChildAttachStateChangeListener(attachListener);
    }

    public static ZXItemClickSupport addTo(RecyclerView view) {
        ZXItemClickSupport support = (ZXItemClickSupport) view.getTag(view.getId());
        if (support == null) {
            support = new ZXItemClickSupport(view);
        }
        return support;
    }

    public static ZXItemClickSupport removeFrom(RecyclerView view) {
        ZXItemClickSupport support = (ZXItemClickSupport) view.getTag(view.getId());
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public ZXItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
        return this;
    }

    public ZXItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(attachListener);
        view.setTag(view.getId());
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View view);
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View view);
    }
}
