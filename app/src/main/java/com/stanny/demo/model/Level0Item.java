package com.stanny.demo.model;


import com.stanny.demo.adapter.ExpandableItemAdapter;
import com.zx.zxutils.other.QuickAdapter.entity.AbstractExpandableItem;
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */
public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {
    public String title;
    public String subTitle;

    public Level0Item( String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
