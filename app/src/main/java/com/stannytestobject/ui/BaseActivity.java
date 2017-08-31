package com.stannytestobject.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zx.zxutils.views.SwipeBack.ZXSwipeBackHelper;

/**
 * Created by Xiangb on 2017/8/30.
 * 功能：
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXSwipeBackHelper.onCreate(this)
                .setSwipeBackEnable(true)
                .setSwipeRelateEnable(true);
    }


}
