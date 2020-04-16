package com.stanny.demo.ui.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXLightUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LightTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_test);
        ButterKnife.bind(this);
        btnBarView.addBtn("判断是否开启自动亮度调节")
                .addBtn("设置是否开启自动调节亮度")
                .addBtn("获取屏幕亮度")
                .addBtn("设置屏幕亮度")
                .addBtn("获取窗口亮度")
                .addBtn("设置窗口亮度")
                .setItemClickListener(this)
                .build();
        dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("窗口亮度测试")
                .setPositiveButton("关闭", null)
                .create();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                btnBarView.printInfo("自动亮度调节：已" + (ZXLightUtil.isAutoBrightnessEnabled() ? "开启" : "关闭"));
                break;
            case 1:
                ZXLightUtil.setAutoBrightnessEnabled(!ZXLightUtil.isAutoBrightnessEnabled());
                btnBarView.printInfo("已修改亮度调节模式：" + (ZXLightUtil.isAutoBrightnessEnabled() ? "开启" : "关闭"));
                break;
            case 2:
                btnBarView.printInfo("当前屏幕亮度:" + ZXLightUtil.getBrightness());
                break;
            case 3:
                if (ZXLightUtil.isAutoBrightnessEnabled()) {
                    btnBarView.printInfo("已开启自动亮度调节，设置亮度无效");
                } else {
                    ZXLightUtil.setBrightness((int) (Math.random() * 230) + 30);
                    btnBarView.printInfo("当前屏幕亮度:" + ZXLightUtil.getBrightness());
                }
                break;
            case 4:
                if (!dialog.isShowing()) {
                    dialog.show();
                }
                btnBarView.printInfo("窗口亮度：" + ZXLightUtil.getWindowBrightness(dialog.getWindow()));
                break;
            case 5:
                if (!dialog.isShowing()) {
                    dialog.show();
                }
                if (ZXLightUtil.isAutoBrightnessEnabled()) {
                    btnBarView.printInfo("已开启自动亮度调节，设置亮度无效");
                } else {
                    ZXLightUtil.setWindowBrightness(dialog.getWindow(), (int) (Math.random() * 230) + 20);
                    btnBarView.printInfo("窗口亮度：" + ZXLightUtil.getWindowBrightness(dialog.getWindow()));
                }
                break;
            default:

                break;
        }
    }
}
