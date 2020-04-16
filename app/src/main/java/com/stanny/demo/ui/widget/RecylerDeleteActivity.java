package com.stanny.demo.ui.widget;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.views.TabViewPager.ZXTabViewPager;

public class RecylerDeleteActivity extends BaseActivity {

    private ZXTabViewPager mytab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxtab);

        mytab = (ZXTabViewPager) findViewById(R.id.vp_tab);
        mytab.setManager(getSupportFragmentManager())
                .setTabLayoutGravity(ZXTabViewPager.TabGravity.GRAVITY_TOP)
//                .addTab(RecylerDeleteFragment.newInstance(""), "测试1")
//                .addTab(RecylerDeleteFragment.newInstance(""), "测试2")
                .addTab(RecylerDeleteFragment.newInstance(""), "测试3")
                .build();
    }
}
