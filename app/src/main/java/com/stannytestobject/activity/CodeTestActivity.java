package com.stannytestobject.activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.stannytestobject.R;

import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

import static com.zx.zxutils.ZXApp.getContext;

public class CodeTestActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvmac;
    private RadioGroup rgTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_test);

        findViewById(R.id.btn_testMac1).setOnClickListener(this);
        findViewById(R.id.btn_testMac2).setOnClickListener(this);
        rgTest = (RadioGroup) findViewById(R.id.rg_group);
        tvmac = (TextView) findViewById(R.id.tv_mac);

        for (int i = 0; i <3; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, 50);
            layoutParams.setMargins(10, 10, 10, 10);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setText("rb1");
            radioButton.setTextSize(12);
            radioButton.setButtonDrawable(android.R.color.transparent);//隐藏单选圆形按钮
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setPadding(10, 10, 10, 10);
            rgTest.addView(radioButton);
        }

        rgTest.setClickable(false);
//        rgTest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                changeTabColor();
//                // 改变选中标签背景
//                ((RadioButton) main.findViewById(checkedId)).setTextColor(secleted);
//                // 禁用tab
//                order_tab.setClickable(false);
//                listview.setVisibility(View.GONE);
//                refresh_layout.setVisibility(View.VISIBLE);
//            }
//        });
    }

    public void changeTabColor() {
        // 设置所有按钮默认颜se
        int count = rgTest.getChildCount();
        for (int i = 0; i < count; i++) {
//            ((RadioButton) rgTest.getChildAt(i)).setTextColor(default_color);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_testMac1:
                tvmac.setText(getMacAddress(this));
                break;
            case R.id.btn_testMac2:
                tvmac.setText(getMachineHardwareAddress().toLowerCase());
                break;
            default:
                break;
        }
    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    private static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if (hardWareAddress == null)
                    continue;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    private static String getMacAddress(Context c) {
        WifiManager wifi = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return "";
        }
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress() == null ? "" : info.getMacAddress();
    }

}
