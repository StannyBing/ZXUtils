package com.stanny.demo.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.stanny.demo.func.BtnBarView;
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
