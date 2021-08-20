package com.stanny.demo.ui.widget;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.stanny.demo.R;
import com.stanny.demo.adapter.DrawAdapter;
import com.stanny.demo.model.DrawEntity;
import com.zx.zxutils.other.ZXItemClickSupport;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.SlidingLayout.ZXSlidingRootNav;
import com.zx.zxutils.views.SlidingLayout.ZXSlidingRootNavBuilder;
import com.zx.zxutils.views.TabViewPager.ZXTabViewPager;

import java.util.ArrayList;
import java.util.List;

public class SlidingActivity extends AppCompatActivity {

    private DrawAdapter adapter;
    private ZXSlidingRootNav slidingNav;
    private Toolbar toolbar;
    private TextView tvDetail;
    private ZXTabViewPager vpTest;
    private List<DrawEntity> dataList = new ArrayList<>();
    private int[] iconArray = new int[]{R.drawable.ic_account_outline_grey600_24dp, R.drawable.ic_cart_outline_grey600_24dp, R.drawable.ic_email_outline_grey600_24dp, R.drawable.ic_home_outline_grey600_24dp, R.drawable.ic_logout_grey600_24dp};
    private String[] titleArray = new String[]{"Dashboard", "My Account", "Message", "Cart", "Log Out"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        vpTest = (ZXTabViewPager) findViewById(R.id.vp_sliding_test);
        vpTest.setManager(getSupportFragmentManager())
                .setTabLayoutGravity(ZXTabViewPager.TabGravity.GRAVITY_BOTTOM)
                .addTab(TabFragment.newInstance(""), "1", R.mipmap.ic_empty_picture)
                .addTab(TabFragment.newInstance(""), "2", R.mipmap.ic_empty_picture)
                .addTab(TabFragment.newInstance(""), "3", R.mipmap.ic_empty_picture)
                .setTitleColor(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.red))
                .setIndicatorColor(ContextCompat.getColor(this, R.color.wheat))
                .setIndicatorHeight(3)
                .setSelectOn(2)
                .build();

        slidingNav = new ZXSlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .withMenuOpened(false)
                .build();

        tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZXToastUtil.showToast(tvDetail.getText().toString());
            }
        });

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
