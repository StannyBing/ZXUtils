package com.zx.zxutils.other.QuickAdapter.listener;

import android.view.View;

import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter;


/**
 * create by: allen on 16/8/3.
 */

public abstract class OnItemLongClickListener extends SimpleClickListener {
    @Override
    public void onItemClick(ZXQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(ZXQuickAdapter adapter, View view, int position) {
        onSimpleItemLongClick(adapter, view, position);
    }

    @Override
    public void onItemChildClick(ZXQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(ZXQuickAdapter adapter, View view, int position) {
    }

    public abstract void onSimpleItemLongClick(ZXQuickAdapter adapter, View view, int position);
}
