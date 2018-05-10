package com.stanny.demo.ui.widget;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.views.TabViewPager.ZXTabViewPager;

public class TabLayoutActivity extends BaseActivity {

    private ZXTabViewPager zxTabPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxtab);
        zxTabPager = (ZXTabViewPager) findViewById(R.id.vp_tab);
        zxTabPager.setManager(getSupportFragmentManager())
                .setTabLayoutGravity(ZXTabViewPager.TabGravity.GRAVITY_BOTTOM)
                .addTab(TabFragment.newInstance(""), "测试", R.mipmap.ic_empty_picture)
                .addTab(TabFragment.newInstance(""), "测试", R.mipmap.ic_empty_picture)
                .addTab(TabFragment.newInstance(""), "测试", R.mipmap.ic_empty_picture)
                .setTitleColor(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.red))
                .setIndicatorColor(ContextCompat.getColor(this, R.color.wheat))
                .setIndicatorHeight(3)
                .setSelectOn(2)
                .build();

    }
}
