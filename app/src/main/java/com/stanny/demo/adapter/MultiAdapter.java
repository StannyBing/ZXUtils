package com.stanny.demo.adapter;

import com.stanny.demo.R;
import com.stanny.demo.model.AdapterTestEntity;
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder;
import com.zx.zxutils.other.QuickAdapter.ZXMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by Xiangb on 2018/7/5.
 * 功能：
 */
public class MultiAdapter extends ZXMultiItemQuickAdapter<AdapterTestEntity, ZXBaseHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultiAdapter(List<AdapterTestEntity> data) {
        super(data);
        addItemType(0, R.layout.item_adapter_simple1);
        addItemType(1, R.layout.item_adapter_simple2);
    }

    @Override
    protected void convert(ZXBaseHolder helper, AdapterTestEntity item) {
        if (helper.getItemViewType() == 0) {
            helper.setText(R.id.tv_item_simple, item.name);
        } else if (helper.getItemViewType() == 1) {
            helper.setText(R.id.tv_item_simple, item.name);
        }
    }
}
