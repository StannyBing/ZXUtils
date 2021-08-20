package com.stanny.demo.adapter;

import androidx.annotation.NonNull;
import android.view.View;

import com.stanny.demo.R;
import com.stanny.demo.model.SpotBean;
import com.stanny.demo.model.TaskBean;
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder;
import com.zx.zxutils.other.QuickAdapter.ZXMultiItemQuickAdapter;
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableItemAdapter extends ZXMultiItemQuickAdapter<MultiItemEntity, ZXBaseHolder> {

    public static final int TYPE_TASK = 1;
    public static final int TYPE_SPOT = 2;

    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_TASK, R.layout.item_task_0);
        addItemType(TYPE_SPOT, R.layout.item_task_1);
    }


    @Override
    protected void convert(@NonNull final ZXBaseHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_TASK:
                final TaskBean lv1 = (TaskBean) item;
                holder.setText(R.id.tv_task_name, lv1.title);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos,false);
                        } else {
                            expand(pos,false);
                        }
                    }
                });
                break;
            case TYPE_SPOT:
                final SpotBean spotBean = (SpotBean) item;
                holder.setText(R.id.tv_spot_name, spotBean.name);
                break;
            default:
                break;
        }
    }
}
