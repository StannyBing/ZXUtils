package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXNetworkUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetWorkTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnbarView
                .addBtn("打开网络设置界面")
                .addBtn("判断网络是否连接")
                .addBtn("判断移动数据是否打开")
                .addBtn("判断wifi是否打开")
                .addBtn("获取网络运营商名称")
                .addBtn("获取IP地址")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://打开网络设置界面
                ZXNetworkUtil.openNetWordSettings(this);
                break;
            case 1://判断网络是否连接
                btnbarView.printInfo("网络" + (ZXNetworkUtil.isConnected() ? "已连接" : "未连接"));
                break;
            case 2://判断移动数据是否打开
                btnbarView.printInfo("移动数据" + (ZXNetworkUtil.isMobileDataEnable() ? "已打开" : "未打开"));
                break;
            case 3://判断wifi是否打开
                btnbarView.printInfo("Wifi" + (ZXNetworkUtil.isWifiEnabled() ? "已打开" : "未打开"));
                break;
            case 4://获取网络运营商名称
                btnbarView.printInfo("网络运营商：" + ZXNetworkUtil.getNetworkOperatorName());
                break;
            case 5://获取IP地址
                btnbarView.printInfo("当前ip地址为：" + ZXNetworkUtil.getIPAddress(true));
                break;
            default:
                break;
        }
    }
}
