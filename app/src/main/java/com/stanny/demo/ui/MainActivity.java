package com.stanny.demo.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.stanny.demo.R;
import com.zx.zxutils.util.ZXPermissionUtil;
import com.zx.zxutils.views.TabViewPager.ZXTabViewPager;

import static com.zx.zxutils.util.ZXToastUtil.showToast;

/**
 * zxutil测试类
 */

public class MainActivity extends BaseActivity {

    private String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZXTabViewPager zxTabViewPager = findViewById(R.id.zxtvp_main);
        zxTabViewPager.setManager(getSupportFragmentManager())
                .addTab(MyUtilFragment.newInstance(), "基础工具库(部分)")
                .addTab(MyUIFragment.newInstance(), "基础UI库")
                .setTitleColor(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.white))
                .setIndicatorHeight(4)
                .setIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent))
                .build();

        if (!ZXPermissionUtil.checkPermissionsByArray(permissions)) {
            ZXPermissionUtil.requestPermissionsByArray(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!ZXPermissionUtil.checkPermissionsByArray(permissions)) {
            showToast("当前应用缺乏必要权限");
            finish();
        }
    }
}
