package com.stannytestobject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stannytestobject.R;
import com.stannytestobject.model.MyEntity;

import java.util.ArrayList;

/**
 * Created by Xiangb on 2017/8/16.
 * 功能：
 */

public class MyAdapter extends RecyclerView.Adapter {

    private ArrayList<MyEntity> dataList;

    public MyAdapter(ArrayList<MyEntity> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.tvTime.setText(dataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvTime;

        public MyHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_record_time);
        }
    }
}
