package com.stanny.demo.ui.util;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScreenTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnbarView
                .addBtn("获取屏幕的宽度")
                .addBtn("获取屏幕的高度")
                .addBtn("设置屏幕为横屏")
                .addBtn("设置屏幕为竖屏")
                .addBtn("获取当前屏幕截图，包含状态栏")
                .addBtn("获取当前屏幕截图，不包含状态栏")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://获取屏幕的宽度
                btnbarView.printInfo("屏幕宽度:" + ZXScreenUtil.getScreenWidth());
                break;
            case 1://获取屏幕的高度
                btnbarView.printInfo("屏幕高度:" + ZXScreenUtil.getScreenHeight());
                break;
            case 2://设置屏幕为横屏
                ZXScreenUtil.setLandscape(this);
                break;
            case 3://设置屏幕为竖屏
                ZXScreenUtil.setPortrait(this);
                break;
            case 4://获取当前屏幕截图，包含状态栏
                Bitmap bitmap1 = ZXScreenUtil.getScreenShot(this);
                if (bitmap1 != null) {
                    btnbarView.printInfo("截图成功,得到截图bitmap");
                } else {
                    btnbarView.printInfo("截图失败");
                }
                break;
            case 5://获取当前屏幕截图，不包含状态栏
                Bitmap bitmap2 = ZXScreenUtil.getScreenShot(this);
                if (bitmap2 != null) {
                    btnbarView.printInfo("截图成功,得到截图bitmap");
                } else {
                    btnbarView.printInfo("截图失败");
                }
                break;

            default:
                break;
        }
    }
}
