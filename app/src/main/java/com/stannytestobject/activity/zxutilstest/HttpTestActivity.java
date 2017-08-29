package com.stannytestobject.activity.zxutilstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stannytestobject.R;
import com.stannytestobject.util.ApiData;
import com.zx.zxutils.http.ZXBaseResult;
import com.zx.zxutils.http.ZXHttpListener;
import com.zx.zxutils.util.ZXDialogUtil;
import com.zx.zxutils.util.ZXToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HttpTestActivity extends AppCompatActivity implements ZXHttpListener {

    private ApiData loginApi1 = new ApiData(1);
    private ApiData loginApi2 = new ApiData(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test_httpGet, R.id.btn_test_httpPost})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_httpGet:
                loginApi1.loadData("login");
                break;
            case R.id.btn_test_httpPost:
                loginApi2.loadData("login");
                break;
        }
    }

    @Override
    public void OnHttpStart(int apiType) {
        ZXDialogUtil.showLoadingDialog(this, "正在加载中...");
    }

    @Override
    public void OnHttpError(int apiType, String errorMsg) {
        ZXToastUtil.showToast(errorMsg);
    }

    @Override
    public void OnHttpSuccess(int apiType, ZXBaseResult baseResult) {
        ZXDialogUtil.dismissLoadingDialog();
        ZXToastUtil.showToast("请求成功");
    }

    @Override
    public void OnHttpProgress(int apiType, int progress) {

    }
}
