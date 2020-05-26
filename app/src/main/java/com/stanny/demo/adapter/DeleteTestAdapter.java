package com.stanny.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanny.demo.R;
import com.stanny.demo.model.KeyValueEntity;
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder;
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter;

import java.util.List;

/**
 * Created by Xiangb on 2017/5/9.
 * 功能：
 */

public class DeleteTestAdapter extends ZXQuickAdapter<KeyValueEntity, ZXBaseHolder> {
    private List<KeyValueEntity> dataList;

    public DeleteTestAdapter(List<KeyValueEntity> datalist) {
        super(R.layout.item_draw, datalist);
    }

    @Override
    protected void convert(@NonNull ZXBaseHolder helper, KeyValueEntity item) {
        helper.setBackgroundRes(R.id.icon, R.drawable.__picker_camera);
        helper.setText(R.id.title, item.getValue());
        if (helper.getAdapterPosition() % 2 == 1) {
            helper.getView(R.id.tv_delete).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
        }
    }
}
