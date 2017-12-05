package com.zx.zxutils.views.ExpandableView;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecycleSimpleAdapter;
import com.zx.zxutils.util.ZXSystemUtil;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/4.
 * 功能：
 */

public class ZXExpandAdapter extends ZXRecycleSimpleAdapter {

    private List<ZXExpandBean> showList;
    private Context context;
    private boolean isMultiSelected = true;

    public ZXExpandAdapter(Context context, List<ZXExpandBean> showList, boolean isMultiSelected) {
        hasLoadMore = false;
        this.context = context;
        this.showList = showList;
        this.isMultiSelected = isMultiSelected;
    }

    @Override
    public RecyclerView.ViewHolder onItemHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ZXExpandBean expandBean = showList.get(position);
        myHolder.tvInfo.setText(expandBean.getItemText());
        ViewGroup.LayoutParams params = myHolder.viewIndex.getLayoutParams();
        params.width = ZXSystemUtil.dp2px(30 * expandBean.getIndex());
        myHolder.viewIndex.setLayoutParams(params);
        if (expandBean.getChildList() == null) {
            myHolder.ivArrow.setVisibility(View.INVISIBLE);
        } else if (expandBean.isShowChild()) {
            myHolder.ivArrow.setVisibility(View.VISIBLE);
            myHolder.ivArrow.setBackground(ContextCompat.getDrawable(context, R.mipmap.arrow_open));
        } else {
            myHolder.ivArrow.setVisibility(View.VISIBLE);
            myHolder.ivArrow.setBackground(ContextCompat.getDrawable(context, R.mipmap.arrow_close));
        }
        if (!isMultiSelected) {
            myHolder.ivSelect.setVisibility(View.INVISIBLE);
        } else if (expandBean.getChildList() != null) {
            myHolder.ivSelect.setVisibility(View.INVISIBLE);
        } else if (expandBean.isSelected()) {
            myHolder.ivSelect.setVisibility(View.VISIBLE);
            myHolder.ivSelect.setBackground(ContextCompat.getDrawable(context, R.mipmap.select));
        } else {
            myHolder.ivSelect.setVisibility(View.VISIBLE);
            myHolder.ivSelect.setBackground(ContextCompat.getDrawable(context, R.mipmap.not_select));
        }
    }

    @Override
    public List onItemList() {
        return showList;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvInfo;
        private View viewIndex;
        private ImageView ivArrow, ivSelect;

        public MyHolder(View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tv_info);
            viewIndex = itemView.findViewById(R.id.view_index);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            ivSelect = itemView.findViewById(R.id.iv_select);
        }
    }

}
