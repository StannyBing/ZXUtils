package com.stanny.demo.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanny.demo.R;
import com.stanny.demo.model.DrawEntity;

import java.util.List;

/**
 * Created by Xiangb on 2017/5/9.
 * 功能：
 */

public class DrawAdapter extends RecyclerView.Adapter<DrawAdapter.MyHolder> {
    private Context context;
    private List<DrawEntity> dataList;

    public DrawAdapter(Context context, List<DrawEntity> datalist) {
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
        DrawEntity drawEntity = dataList.get(position);
        if (drawEntity.isChecked()) {
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.skyblue));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.skyblue));
        } else {
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.slategray));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.slategray));
        }
        holder.icon.setBackground(drawEntity.getIcon());
        holder.title.setText(drawEntity.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;

        public MyHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
}
