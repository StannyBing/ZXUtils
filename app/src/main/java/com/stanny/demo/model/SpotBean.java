package com.stanny.demo.model;


import com.stanny.demo.adapter.ExpandableItemAdapter;
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */

public class SpotBean implements MultiItemEntity {
    public SpotBean(String name) {
        this.name = name;
    }

    public String name;

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_SPOT;
    }
}