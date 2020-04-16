package com.stanny.demo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanny.demo.R;
import com.stanny.demo.model.KeyValueEntity;

import java.util.List;

/**
 * Created by Xiangb on 2017/5/9.
 * 功能：
 */

public class DeleteTestAdapter extends RecyclerView.Adapter<DeleteTestAdapter.MyHolder> {
    private Context context;
    private List<KeyValueEntity> dataList;

    public DeleteTestAdapter(Context context, List<KeyValueEntity> datalist) {
        this.context = context;
        this.dataList = datalist;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_draw, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        KeyValueEntity drawEntity = dataList.get(position);
        holder.icon.setBackground(ContextCompat.getDrawable(context, R.drawable.__picker_camera));
        holder.title.setText(drawEntity.getValue());
        if (position % 2 == 1) {
            holder.cancel.setVisibility(View.GONE);
        } else {
            holder.cancel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private TextView cancel;

        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            cancel = (TextView) itemView.findViewById(R.id.tv_delete);
        }
    }
}
