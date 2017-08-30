package com.stannytestobject.activity.zxutilstest;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.stannytestobject.R;
import com.stannytestobject.activity.BaseActivity;
import com.stannytestobject.fragment.TabFragment;
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
                .addTab(TabFragment.newInstance(""), "1")
                .addTab(TabFragment.newInstance(""), "2")
                .addTab(TabFragment.newInstance(""), "3")
                .setTitleColor(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.red))
                .setIndicatorColor(ContextCompat.getColor(this, R.color.wheat))
                .setIndicatorHeight(3)
                .setSelectOn(2);

    }
}
