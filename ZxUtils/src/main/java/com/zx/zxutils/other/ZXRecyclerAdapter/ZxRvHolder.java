package com.zx.zxutils.other.ZXRecyclerAdapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Xiangb on 2017/9/5.
 * 功能：
 */

public class ZxRvHolder {
    private SparseArray<View> viewHolder;
    private View view;

    public static ZxRvHolder getViewHolder(View view) {
        ZxRvHolder viewHolder = (ZxRvHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new ZxRvHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    private ZxRvHolder(View view) {
        this.view = view;
        viewHolder = new SparseArray<View>();
        view.setTag(viewHolder);
    }

    public <T extends View> T get(int id) {
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public View getConvertView() {
        return view;
    }

    public TextView getTextView(int id) {
        return get(id);
    }

    public Button getButton(int id) {

        return get(id);
    }

    public ImageView getImageView(int id) {
        return get(id);
    }

    public void setTextView(int id, CharSequence charSequence) {
        getTextView(id).setText(charSequence);
    }

}
