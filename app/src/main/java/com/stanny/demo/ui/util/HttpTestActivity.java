package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.view.BtnBarView;
import com.stanny.demo.util.ApiData;
import com.zx.zxutils.http.ZXBaseResult;
import com.zx.zxutils.http.ZXHttpListener;
import com.zx.zxutils.util.ZXDialogUtil;
import com.zx.zxutils.util.ZXToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpTestActivity extends BaseActivity implements ZXHttpListener {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    private ApiData loginApi1 = new ApiData(1);
    private ApiData loginApi2 = new ApiData(0);
    private ApiData testApi = new ApiData(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnBarView.addBtn("get请求")
                .addBtn("post请求")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://get请求
                loginApi1.loadData("login");
                break;
            case 1://post请求
//                loginApi2.loadData("login");
                testApi.loadData();
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
