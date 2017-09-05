package com.stanny.demo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.stanny.demo.R;
import com.stanny.demo.util.ApiData;
import com.zx.zxutils.http.ZXBaseResult;
import com.zx.zxutils.http.ZXHttpApi;
import com.zx.zxutils.http.ZXHttpListener;
import com.zx.zxutils.http.common.Callback;
import com.zx.zxutils.util.ZXSystemUtil;
import com.zx.zxutils.util.ZXToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadTestActivity extends BaseActivity implements ZXHttpListener {
    @BindView(R.id.pb_test_simpleUpload)
    ProgressBar pbTestSimpleUpload;
    @BindView(R.id.pb_test_apiUpload)
    ProgressBar pbTestApiUpload;

    private Callback.Cancelable cancelable;
    private ApiData uploadApi = new ApiData(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_test);
        ButterKnife.bind(this);

        uploadApi.setHttpListener(this);
    }

    @OnClick({R.id.btn_test_simpleStart, R.id.btn_test_simplePause, R.id.btn_test_simpleCancel, R.id.btn_test_apiStart, R.id.btn_test_apiPause, R.id.btn_test_apiCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_simpleStart:
                cancelable = ZXHttpApi.uploadFile("http://192.168.110.238:7070/GAMarketSupervise/eventfileUpload", "file", new File(ZXSystemUtil.getSDCardPath() + "ga.txt"), new ZXHttpListener() {
                    @Override
                    public void OnHttpStart(int apiType) {

                    }

                    @Override
                    public void OnHttpError(int apiType, String errorMsg) {
                        ZXToastUtil.showToast(errorMsg);
                    }

                    @Override
                    public void OnHttpSuccess(int apiType, ZXBaseResult baseResult) {

                    }

                    @Override
                    public void OnHttpProgress(int apiType, int progress) {
                        pbTestSimpleUpload.setProgress(progress);
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
                File file = new File(ZXSystemUtil.getSDCardPath() + "GAMarket.jar");
                if (!file.exists()) {
                    return;
                }
                uploadApi.loadData(file);
                break;
            case R.id.btn_test_apiPause:
                uploadApi.cancel();
                break;
            case R.id.btn_test_apiCancel:
                uploadApi.cancel();
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
        pbTestApiUpload.setProgress(progress);
    }
}
