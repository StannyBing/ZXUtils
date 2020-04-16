package com.stanny.demo.adapter;

import android.support.annotation.Nullable;

import com.stanny.demo.R;
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder;
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter;

import java.util.List;

/**
 * Created by Xiangb on 2018/7/5.
 * 功能：
 */
public class QuickAdapter extends ZXQuickAdapter<String, ZXBaseHolder> {
    public QuickAdapter(@Nullable List<String> data) {
        super(R.layout.item_adapter_simple1, data);
    }

    @Override
    protected void convert(ZXBaseHolder helper, String item) {
        helper.setText(R.id.tv_item_simple, item);
    }
}
