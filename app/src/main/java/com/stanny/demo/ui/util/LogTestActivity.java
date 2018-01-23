package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.view.BtnBarView;
import com.zx.zxutils.util.ZXLogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnBarView.addBtn("error")
                .addBtn("debug")
                .addBtn("info")
                .addBtn("warn")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://error
                ZXLogUtil.loge("这是error信息");
                break;
            case 1://debug
                ZXLogUtil.logd("提示", "这是debug信息");
                break;
            case 2://info
                ZXLogUtil.logi("这是info信息");
                break;
            case 3://warn
                ZXLogUtil.logw("提示", "这是warn信息");
                break;
        }
    }
}
