package com.stannytestobject.activity.zxutilstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stannytestobject.R;
import com.zx.zxutils.other.ZXBroadCastManager;
import com.zx.zxutils.util.ZXToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BroadCastTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);
        ButterKnife.bind(this);

        //接收广播
        ZXBroadCastManager.getInstance(this).getAction("com.zx.default_action", new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.zx.default_action")) {
                    ZXToastUtil.showToast(intent.getStringExtra("boolean"));

                }
            }
        });
    }

    @OnClick(R.id.btn_test_sendBroadCast)
    public void onViewClicked() {
        //发送广播
        ZXBroadCastManager.getInstance(this)
                .setAction("com.zx.default_action")
                .addExtra("string", "字符串类型")
                .addExtra("boolean", "布尔类型")
                .send();
    }
}
