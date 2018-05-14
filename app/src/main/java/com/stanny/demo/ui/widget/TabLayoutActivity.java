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
                .addTab(TabFragment.newInstance(""), "测试1", R.drawable.selector_tab_test)
                .addTab(TabFragment.newInstance(""), "测试2", R.drawable.selector_tab_test)
                .addTab(TabFragment.newInstance(""), "测试3", R.drawable.selector_tab_test)
                .setTitleColor(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.red))
                .setIndicatorColor(ContextCompat.getColor(this, R.color.wheat))
                .setIndicatorHeight(3)
                .setSelectOn(2)
                .setViewpagerCanScroll(false)
                .build();
        zxTabPager.getTabLayout().setBackgroundColor(ContextCompat.getColor(this, R.color.violet));

    }
}
