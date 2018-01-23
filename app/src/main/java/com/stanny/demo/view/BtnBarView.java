package com.stanny.demo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stanny.demo.R;
import com.stanny.demo.model.KeyValueEntity;
import com.zx.zxutils.other.ZXItemClickSupport;
import com.zx.zxutils.util.ZXTimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2018/1/22.
 * 功能：
 */

public class BtnBarView extends LinearLayout {
    private Context context;
    private List<String> datas = new ArrayList<>();
    private List<KeyValueEntity> printInfos = new ArrayList<>();
    private RecyclerView rvBtnbar, rvPrint;
    private LinearLayout llPrintInfo;
    private BtnBarAdapter mAdapter;
    private PrintInfoAdapter mPrintAdapter;
    private OnItemClickListener onItemClickListener;

    public BtnBarView(Context context) {
        this(context, null);
    }

    public BtnBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BtnBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_normal_btnbarlist, this, true);
        rvBtnbar = findViewById(R.id.rv_btnbar);
        rvPrint = findViewById(R.id.rv_printInfo);
        llPrintInfo = findViewById(R.id.ll_print_info);
        rvPrint.setLayoutManager(new LinearLayoutManager(context));
        rvBtnbar.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new BtnBarAdapter(datas);
        rvBtnbar.setAdapter(mAdapter);
        ZXItemClickSupport.addTo(rvBtnbar)
                .setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                });

        mPrintAdapter = new PrintInfoAdapter(printInfos);
        rvPrint.setAdapter(mPrintAdapter);
    }

    public BtnBarView addBtn(String name) {
        datas.add(name);
        return this;
    }

    /**
     * 打印信息
     *
     * @param printInfo
     */
    public void printInfo(String printInfo) {
        llPrintInfo.setVisibility(VISIBLE);
        printInfos.add(new KeyValueEntity(printInfo, ZXTimeUtil.getCurrentTime()));
        mPrintAdapter.notifyItemInserted(printInfos.size() - 1);
        rvPrint.scrollToPosition(printInfos.size() - 1);
    }

    public BtnBarView setItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public void build() {
        mAdapter.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class BtnBarAdapter extends RecyclerView.Adapter<BtnBarAdapter.BtnBarHolder> {

        private List<String> dataList;

        public BtnBarAdapter(List<String> datas) {
            dataList = datas;
        }

        @Override
        public BtnBarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_btn_bar, null, false);
            return new BtnBarHolder(view);
        }

        @Override
        public void onBindViewHolder(BtnBarHolder holder, int position) {
            holder.tvBtnBar.setText(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class BtnBarHolder extends RecyclerView.ViewHolder {

            private TextView tvBtnBar;

            public BtnBarHolder(View itemView) {
                super(itemView);
                tvBtnBar = itemView.findViewById(R.id.tv_btn_bar);
            }
        }
    }

    class PrintInfoAdapter extends RecyclerView.Adapter<PrintInfoAdapter.PrintInfoHolder> {

        private List<KeyValueEntity> printInfos;

        public PrintInfoAdapter(List<KeyValueEntity> printInfos) {
            this.printInfos = printInfos;
        }

        @Override
        public PrintInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_print_info, null, false);
            return new PrintInfoHolder(view);
        }

        @Override
        public void onBindViewHolder(PrintInfoHolder holder, int position) {
            holder.tvInfo.setText(printInfos.get(position).getKey());
            holder.tvDate.setText(printInfos.get(position).getValue());
        }

        @Override
        public int getItemCount() {
            return printInfos.size();
        }

        class PrintInfoHolder extends RecyclerView.ViewHolder {

            private TextView tvInfo, tvDate;

            public PrintInfoHolder(View itemView) {
                super(itemView);
                tvInfo = itemView.findViewById(R.id.tv_print_info);
                tvDate = itemView.findViewById(R.id.tv_print_date);
            }
        }
    }
}
