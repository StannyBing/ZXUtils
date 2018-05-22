package com.stanny.demo.ui.widget;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.views.CameraView.ZXCameraView;
import com.zx.zxutils.views.CameraView.listener.CameraListener;
import com.zx.zxutils.views.ZXStatusBarCompat;

import java.io.File;

public class CameraActivity extends BaseActivity {

    private ZXCameraView zxCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        zxCameraView = findViewById(R.id.jcamprieraview);

        ZXStatusBarCompat.translucent(this);

        //设置视频保存路径
        zxCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera")
                .setCameraMode(ZXCameraView.BUTTON_STATE_BOTH)
                .setMediaQuality(ZXCameraView.MEDIA_QUALITY_MIDDLE)
                .setJCameraLisenter(new CameraListener() {
                    @Override
                    public void onCaptureCommit(Bitmap bitmap) {

                    }

                    @Override
                    public void onRecordCommit(String url, Bitmap firstFrame) {
                    }

                    @Override
                    public void onActionSuccess(CameraType type) {

                    }

                    @Override
                    public void onError(ErrorType errorType) {
                        //打开Camera失败回调
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        zxCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zxCameraView.onPause();
    }
}
