package com.stanny.demo.ui.widget;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecycleAdapter;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecycleSimpleAdapter;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZxRvHolder;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.RecylerMenu.ZXRecyclerDeleteHelper;
import com.zx.zxutils.views.SwipeRecylerView.ZXSRListener;
import com.zx.zxutils.views.SwipeRecylerView.ZXSwipeRecyler;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshRecylerActivity extends BaseActivity {

    private ZXSwipeRecyler swipeRecyler;
    private List<String> datalist = new ArrayList<>();
    private int totalNum = 100;
    Test2Adapter test2Adapter;
    ZXRecyclerDeleteHelper deleteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_recyler);
        swipeRecyler = findViewById(R.id.sr_layout);
        swipeRecyler.setLayoutManager(new LinearLayoutManager(this))
                .setAdapter(test2Adapter = new Test2Adapter(datalist))
                .setPageSize(15)
                .autoLoadMore()
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
//                        swipeRecyler.stopRefresh();
                        addList();
//                        swipeRecyler.setLoadInfo("阿西吧");
                    }

                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                addList();
                            }
                        }, 1000);
//                        swipeRecyler.setLoadInfo("啊了个阿西吧");
                    }
                });
        swipeRecyler.notifyDataSetChanged();
//        addList();
        swipeRecyler.refreshData(new ArrayList(), 0);
    }

    public void addList() {
        List<String> newData =  new ArrayList();
        for (int i = 0; i < 15; i++) {
            newData.add(Math.random() * 10 + "");
        }
        swipeRecyler.refreshData(newData, 100);
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

    public class Test1Adpater extends ZXRecycleSimpleAdapter {

        @Override
        public RecyclerView.ViewHolder onItemHolder(ViewGroup parent, int viewType) {

            return null;
        }

        @Override
        public void onBindHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public List onItemList() {
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public class Test2Adapter extends ZXRecyclerQuickAdapter<String, ZXBaseHolder> {

        public Test2Adapter(@Nullable List data) {
            super(R.layout.layout_custom, data);
        }

        @Override
        public void quickConvert(ZXBaseHolder helper, String item) {
            helper.setText(R.id.tv_text, item);
        }
    }
}
