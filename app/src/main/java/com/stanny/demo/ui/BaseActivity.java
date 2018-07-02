package com.stanny.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.stanny.demo.view.BtnBarView;
import com.zx.zxutils.views.SwipeBack.ZXSwipeBackHelper;

/**
 * Created by Xiangb on 2017/8/30.
 * 功能：
 */

public class BaseActivity extends AppCompatActivity implements BtnBarView.OnItemClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ZXSwipeBackHelper.onCreate(this)
                .setSwipeBackEnable(true)
                .setSwipeRelateEnable(true);

    }

    @Override
    public void onItemClick(int position) {

    }
}
