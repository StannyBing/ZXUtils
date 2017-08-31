package com.stannytestobject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.stannytestobject.R;
import com.stannytestobject.ui.BaseActivity;
import com.stannytestobject.util.ApiData;
import com.zx.zxutils.http.ZXBaseResult;
import com.zx.zxutils.http.ZXDownload;
import com.zx.zxutils.http.ZXHttpApi;
import com.zx.zxutils.http.ZXHttpListener;
import com.zx.zxutils.http.common.Callback;
import com.zx.zxutils.util.ZXSystemUtil;
import com.zx.zxutils.util.ZXToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownTestActivity extends BaseActivity implements ZXHttpListener {

    @BindView(R.id.pb_test_simpleDown)
    ProgressBar pbTestSimpleDown;
    @BindView(R.id.pb_test_apiDown)
    ProgressBar pbTestApiDown;

    private Callback.Cancelable cancelable;
    private ApiData downloadApi = new ApiData(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_test);
        ButterKnife.bind(this);

        downloadApi.setHttpListener(this);
    }

    @OnClick({R.id.btn_test_simpleStart, R.id.btn_test_simplePause, R.id.btn_test_simpleCancel, R.id.btn_test_apiStart, R.id.btn_test_apiPause, R.id.btn_test_apiCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_simpleStart:
                ZXDownload download = new ZXDownload();
                cancelable = ZXHttpApi.downLoadFile("http://192.168.110.238:7070/upload/GAMarketMobile/Android/GAMarketMobile.apk",
                        ZXSystemUtil.getSDCardPath() + "APIdata.jar", true, new ZXHttpListener() {
                            @Override
                            public void OnHttpStart(int apiType) {

                            }

                            @Override
                            public void OnHttpError(int apiType, String errorMsg) {
                                ZXToastUtil.showToast(errorMsg);
                            }

                            @Override
                            public void OnHttpSuccess(int apiType, ZXBaseResult baseResult) {
                                ZXToastUtil.showToast(baseResult.getEntry().toString());
                            }

                            @Override
                            public void OnHttpProgress(int apiType, int progress) {
                                pbTestSimpleDown.setProgress(progress);
                            }
                        });
                break;
            case R.id.btn_test_simplePause:
                cancelable.cancel();
                break;
            case R.id.btn_test_simpleCancel:
                cancelable.cancel();
                break;
            case R.id.btn_test_apiStart:
                downloadApi.loadData(ZXSystemUtil.getSDCardPath() + "APIdata.jar", true);
                break;
            case R.id.btn_test_apiPause:
                downloadApi.pauseDownload();
                break;
            case R.id.btn_test_apiCancel:
                downloadApi.cancel();
                break;
        }
    }

    @Override
    public void OnHttpStart(int apiType) {

    }

    @Override
    public void OnHttpError(int apiType, String errorMsg) {
        ZXToastUtil.showToast(errorMsg);
    }

    @Override
    public void OnHttpSuccess(int apiType, ZXBaseResult baseResult) {
        ZXToastUtil.showToast(baseResult.getEntry().toString());
    }

    @Override
    public void OnHttpProgress(int apiType, int progress) {
        pbTestApiDown.setProgress(progress);
    }
}
