package com.stanny.demo.model;

import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity;

/**
 * Created by Xiangb on 2018/7/5.
 * 功能：
 */
public class AdapterTestEntity implements MultiItemEntity{

    public int itemType = 0;
    public String name;

    public AdapterTestEntity(int itemType, String name) {
        this.itemType = itemType;
        this.name = name;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


}
