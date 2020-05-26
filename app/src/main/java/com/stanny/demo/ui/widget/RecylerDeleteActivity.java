package com.stanny.demo.ui.widget;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.views.TabViewPager.ZXTabViewPager;

public class RecylerDeleteActivity extends BaseActivity {

    private ZXTabViewPager mytab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxtab);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            // 仅当缺口区域完全包含在状态栏之中时，才允许窗口延伸到刘海区域显示
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            // 永远不允许窗口延伸到刘海区域
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            // 始终允许窗口延伸到屏幕短边上的刘海区域
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }

        mytab = (ZXTabViewPager) findViewById(R.id.vp_tab);
        mytab.setManager(getSupportFragmentManager())
                .setViewpagerCanScroll(false)
                .setTabLayoutGravity(ZXTabViewPager.TabGravity.GRAVITY_TOP)
//                .addTab(RecylerDeleteFragment.newInstance(""), "测试1")
//                .addTab(RecylerDeleteFragment.newInstance(""), "测试2")
                .addTab(RecylerDeleteFragment.newInstance(""), "测试3")
                .build();
    }
}
