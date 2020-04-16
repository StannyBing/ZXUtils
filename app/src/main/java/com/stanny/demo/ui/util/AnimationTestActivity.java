package com.stanny.demo.ui.util;

import android.os.Bundle;
import android.widget.ImageView;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXAnimUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimationTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;
    @BindView(R.id.btn_openAnimTest)
    ImageView btnOpenAnimTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);
        ButterKnife.bind(this);
        btnBarView.addBtn("旋转")
                .addBtn("渐变-显示")
                .addBtn("渐变-隐藏")
                .addBtn("缩小")
                .addBtn("放大")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://旋转
                btnOpenAnimTest.startAnimation(ZXAnimUtil.getRotateAnimationByCenter(2000, null));
                break;
            case 1://渐变-显示
                btnOpenAnimTest.startAnimation(ZXAnimUtil.getAlphaAnimation(0.0f, 1.0f, 2000, null));
                break;
            case 2://渐变-隐藏
                btnOpenAnimTest.startAnimation(ZXAnimUtil.getHiddenAlphaAnimation(2000, null));
                break;
            case 3://缩小
                btnOpenAnimTest.startAnimation(ZXAnimUtil.getLessenScaleAnimation(2000, null));
                break;
            case 4://放大
                btnOpenAnimTest.startAnimation(ZXAnimUtil.getAmplificationAnimation(2000, null));
                break;
            default:
                break;
        }
    }
}
