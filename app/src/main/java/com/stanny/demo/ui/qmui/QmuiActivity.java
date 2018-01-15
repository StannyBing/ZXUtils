package com.stanny.demo.ui.qmui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.stanny.demo.R;
import com.zx.zxutils.qmui.ZXStatusBarCompat;


/**
 * Create By Xiangb On 2017/12/26
 * 功能：兼容腾讯QMUI库
 */
public class QmuiActivity extends AppCompatActivity {

    private RecyclerView rvQmui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmui);
        ZXStatusBarCompat.translucent(this);
        rvQmui = findViewById(R.id.rv_qmui);
        rvQmui.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
