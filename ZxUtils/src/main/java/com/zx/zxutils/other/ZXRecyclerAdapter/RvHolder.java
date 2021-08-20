package com.zx.zxutils.other.ZXRecyclerAdapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by Xiangb on 2017/9/5.
 * 功能：
 */

public class RvHolder extends RecyclerView.ViewHolder {
    private ZxRvHolder viewHolder;

    public RvHolder(View itemView) {
        super(itemView);
        viewHolder = ZxRvHolder.getViewHolder(itemView);
    }


    public ZxRvHolder getViewHolder() {
        return viewHolder;
    }
}
