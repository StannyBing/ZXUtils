package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.func.BtnBarView;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.util.ZXLocationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);
        ButterKnife.bind(this);
        btnBarView.addBtn("获取当前坐标")
                .addBtn("获取两个坐标间的距离")
                .addBtn("判断GPS是否可用")
                .addBtn("判断定位是否可用")
                .addBtn("打开GPS设置界面")
                .addBtn("根据经纬度获取所在国家")
                .addBtn("根据经纬度获取所在地")
                .addBtn("根据经纬度获取所在街道")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                btnBarView.printInfo(ZXLocationUtil.getLocation(this).toString());
                break;
            case 1:
                btnBarView.printInfo("坐标:106.115679,29.1368972与坐标：135.1587221,18.236842289之间的距离为:" + ZXLocationUtil.getDistance(29.1368972, 106.115679, 28.236842289, 135.1587221) + "米");
                break;
            case 2:
                btnBarView.printInfo("当前GPS：" + (ZXLocationUtil.isGpsEnabled() ? "可用" : "不可用"));
                break;
            case 3:
                btnBarView.printInfo("当前定位：" + (ZXLocationUtil.isLocationEnabled() ? "可用" : "不可用"));
                break;
            case 4:
                ZXLocationUtil.openGpsSettings();
                break;
            case 5:
                btnBarView.printInfo(ZXLocationUtil.getCountryName(29.1368972, 106.115679));
                break;
            case 6:
                btnBarView.printInfo(ZXLocationUtil.getLocality(29.1368972, 106.115679));
                break;
            case 7:
                btnBarView.printInfo(ZXLocationUtil.getStreet(29.1368972, 106.115679));
                break;
            default:
                break;
        }
    }
}
