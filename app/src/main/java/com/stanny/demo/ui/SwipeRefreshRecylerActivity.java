package com.stanny.demo.ui;

import android.os.Bundle;

import com.stanny.demo.R;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecycleAdapter;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZxRvHolder;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener;
import com.zx.zxutils.views.SwipeRecylerView.ZXSwipeRecyler;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshRecylerActivity extends BaseActivity {

    private ZXSwipeRecyler swipeRecyler;
    private List<String> datalist = new ArrayList<>();
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
        public List<?> onItemList() {
            return datalist;
        }

        @Override
        public int onCreateViewLayoutID(int viewType) {
            return R.layout.layout_custom;
        }

        @Override
        public void onBindHolder(ZxRvHolder holder, Object itemEntity, int position) {
            holder.getTextView(R.id.tv_text).setText(datalist.get(position));
        }
    }
}
