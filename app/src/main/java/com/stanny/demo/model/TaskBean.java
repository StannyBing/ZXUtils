package com.stanny.demo.model;


import com.stanny.demo.adapter.ExpandableItemAdapter;
import com.zx.zxutils.other.QuickAdapter.entity.AbstractExpandableItem;
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */

public class TaskBean extends AbstractExpandableItem<SpotBean> implements MultiItemEntity {
    public String title;

    public TaskBean(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_TASK;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}