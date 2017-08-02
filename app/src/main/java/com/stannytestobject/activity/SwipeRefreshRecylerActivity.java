package com.stannytestobject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stannytestobject.R;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.SwipeRecylerView.ZXRecycleAdapter;
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener;
import com.zx.zxutils.views.SwipeRecylerView.ZXSwipeRecyler;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshRecylerActivity extends AppCompatActivity {

    private ZXSwipeRecyler swipeRecyler;
    private List<String> datalist = new ArrayList<>();
    private TestAdapter.MyHolder myHolder;
    private int totalNum = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_recyler);
        swipeRecyler = (ZXSwipeRecyler) findViewById(R.id.sr_layout);
        swipeRecyler.setAdapter(new TestAdapter())
                .showLoadInfo(true)
                .setSRListener(new ZXSRListener<String>() {
                    @Override
                    public void onItemClick(String item, int position) {
                        ZXToastUtil.showToast("点击:" + item.toString());
                    }

                    @Override
                    public void onItemLongClick(String item, int position) {
                        ZXToastUtil.showToast("长按:" + item.toString());
                    }

                    @Override
                    public void onRefresh() {
                        swipeRecyler.stopRefresh();
                        addList();
                        swipeRecyler.setLoadInfo(totalNum);
                    }

                    @Override
                    public void onLoadMore() {
                        addList();
                        swipeRecyler.setLoadInfo(totalNum);
                    }
                });
        addList();
    }

    public void addList() {
        datalist.clear();
        for (int i = 0; i < 10; i++) {
            datalist.add(Math.random() * 10 + "");
        }
        swipeRecyler.notifyDataSetChanged();
    }

    public class TestAdapter extends ZXRecycleAdapter {

        @Override
        public RecyclerView.ViewHolder onItemHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindHolder(RecyclerView.ViewHolder holder, int position) {
            myHolder = (MyHolder) holder;
            myHolder.tvTest.setText(datalist.get(position));
        }

        @Override
        public List onItemList() {
            return datalist;
        }

        public class MyHolder extends RecyclerView.ViewHolder {

            private TextView tvTest;

            public MyHolder(View itemView) {
                super(itemView);
                tvTest = (TextView) itemView.findViewById(R.id.tv_text);
            }
        }
    }
}
