package com.stanny.demo.ui.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.other.ZXBroadCastManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BroadCastTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnBarView.addBtn("发送广播")
                .setItemClickListener(this)
                .build();

        //接收广播
        ZXBroadCastManager.getInstance(this).getAction("com.zx.default_action", new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.zx.default_action")) {
                    btnBarView.printInfo(intent.getStringExtra("boolean"));

                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://发送广播
                ZXBroadCastManager.getInstance(this)
                        .setAction("com.zx.default_action")
                        .addExtra("string", "字符串类型")
                        .addExtra("boolean", "布尔类型")
                        .send();
                break;

            default:
                break;
        }
    }
}
