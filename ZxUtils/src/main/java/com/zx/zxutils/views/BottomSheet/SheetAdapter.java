package com.zx.zxutils.views.BottomSheet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.zxutils.R;

import java.util.List;

/**
 * Created by Xiangb on 2018/1/19.
 * 功能：
 */

public class SheetAdapter extends RecyclerView.Adapter<SheetAdapter.MyHolder> {

    private List<SheetData> dataList;
    private ZXBottomSheet.Type type;

    public SheetAdapter(List<SheetData> dataList, ZXBottomSheet.Type type) {
        this.dataList = dataList;
        this.type = type;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (type == ZXBottomSheet.Type.LIST_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sheet_list, null, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sheet_grid, null, false);
        }
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        SheetData sheetData = dataList.get(position);
        if (type == ZXBottomSheet.Type.LIST_TYPE) {
            if (sheetData.getDetail() != null) {
                holder.tvDetail.setVisibility(View.VISIBLE);
                holder.tvDetail.setText(sheetData.getDetail());
            } else {
                holder.tvDetail.setVisibility(View.GONE);
            }
            if (sheetData.getImg() != null) {
                holder.ivImg.setBackground(sheetData.getImg());
                holder.ivImg.setVisibility(View.VISIBLE);
            } else {
                holder.ivImg.setVisibility(View.GONE);
            }
            if (sheetData.isSelected()) {
                holder.ivCheck.setVisibility(View.VISIBLE);
            } else {
                holder.ivCheck.setVisibility(View.GONE);
            }
            holder.tvName.setText(sheetData.getName());
        } else {
            holder.tvName.setText(sheetData.getName());
            if (sheetData.getImg() != null) {
                holder.ivImg.setBackground(sheetData.getImg());
            }
            if (sheetData.isSelected()) {
                holder.ivCheck.setVisibility(View.VISIBLE);
            } else {
                holder.ivCheck.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvDetail, tvName;
        private ImageView ivImg, ivCheck;

        public MyHolder(View itemView) {
            super(itemView);
            tvDetail = itemView.findViewById(R.id.tv_sheet_detail);
            tvName = itemView.findViewById(R.id.tv_sheet_name);
            ivImg = itemView.findViewById(R.id.iv_sheet_img);
            ivCheck = itemView.findViewById(R.id.iv_sheet_check);
        }
    }
}
