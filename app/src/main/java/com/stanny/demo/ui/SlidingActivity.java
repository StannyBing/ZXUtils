package com.stanny.demo.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.stanny.demo.R;
import com.stanny.demo.adapter.DrawAdapter;
import com.stanny.demo.model.DrawEntity;
import com.zx.zxutils.other.ZXItemClickSupport;
import com.zx.zxutils.views.SlidingLayout.ZXSlidingRootNav;
import com.zx.zxutils.views.SlidingLayout.ZXSlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.List;

public class SlidingActivity extends AppCompatActivity {

    private DrawAdapter adapter;
    private ZXSlidingRootNav slidingNav;
    private Toolbar toolbar;
    private TextView tvDetail;
    private List<DrawEntity> dataList = new ArrayList<>();
    private int[] iconArray = new int[]{R.drawable.ic_account_outline_grey600_24dp, R.drawable.ic_cart_outline_grey600_24dp, R.drawable.ic_email_outline_grey600_24dp, R.drawable.ic_home_outline_grey600_24dp, R.drawable.ic_logout_grey600_24dp};
    private String[] titleArray = new String[]{"Dashboard", "My Account", "Message", "Cart", "Log Out"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvDetail = (TextView) findViewById(R.id.tv_detail);

        slidingNav = new ZXSlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withMenuOpened(false)
                .build();

        RecyclerView rv = (RecyclerView) findViewById(R.id.list);
        adapter = new DrawAdapter(this, dataList);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        initDataList();
        ZXItemClickSupport.addTo(rv)
                .setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                        for (int i = 0; i < dataList.size(); i++) {
                            if (i == position) {
                                dataList.get(i).setChecked(true);
                            } else {
                                dataList.get(i).setChecked(false);
                            }
                        }
                        adapter.notifyDataSetChanged();

                        tvDetail.setText(dataList.get(position).getTitle());
                        slidingNav.closeMenu();
                    }
                });
    }

    private void initDataList() {
        for (int i = 0; i < iconArray.length; i++) {
            dataList.add(new DrawEntity(ContextCompat.getDrawable(this, iconArray[i]), titleArray[i]));
        }
        dataList.get(0).setChecked(true);
        adapter.notifyDataSetChanged();
    }
}
