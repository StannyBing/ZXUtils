package com.stanny.demo.ui;

import android.os.Bundle;
import android.view.View;

import com.stanny.demo.R;
import com.zx.zxutils.util.ZXLogUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test_logE, R.id.btn_test_logD, R.id.btn_test_logI, R.id.btn_test_logW})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_logE:
                ZXLogUtil.loge("这是error信息");
                break;
            case R.id.btn_test_logD:
                ZXLogUtil.logd("提示", "这是debug信息");
                break;
            case R.id.btn_test_logI:
                ZXLogUtil.logi("这是info信息");
                break;
            case R.id.btn_test_logW:
                ZXLogUtil.logw("提示", "这是warn信息");
                break;
        }
    }
}
