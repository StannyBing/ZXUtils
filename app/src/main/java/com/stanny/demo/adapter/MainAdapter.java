package com.stanny.demo.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanny.demo.R;
import com.stanny.demo.model.MainEntity;

import java.util.List;

/**
 * Created by Xiangb on 2018/1/22.
 * 功能：
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private Context context;
    private List<MainEntity> dataList;

    public MainAdapter(Context context, List<MainEntity> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, null, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        holder.tvToolName.setText(dataList.get(position).getToolName());
        holder.tvClassName.setText(dataList.get(position).getClassName());
        if (dataList.get(position).getResId() != 0) {
            holder.ivImg.setBackground(ContextCompat.getDrawable(context, dataList.get(position).getResId()));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MainHolder extends RecyclerView.ViewHolder {

        private TextView tvClassName;
        private TextView tvToolName;
        private ImageView ivImg;

        public MainHolder(View itemView) {
            super(itemView);

            ivImg = itemView.findViewById(R.id.main_item_img);
            tvClassName = itemView.findViewById(R.id.main_item_className);
            tvToolName = itemView.findViewById(R.id.main_item_toolName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, dataList.get(getAdapterPosition()).getClassFile()));
                }
            });
        }
    }
}
