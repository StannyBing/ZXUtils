package com.zx.zxutils.views.CameraView.listener;

import android.graphics.Bitmap;

/**
 * =====================================
 * 作    者: 陈嘉桐
 * 版    本：1.1.4
 * 创建日期：2017/4/26
 * 描    述：
 * =====================================
 */
public interface CameraListener {

    enum ErrorType {
        NotPermission,
        OpenCameraError,
        RecordIllegalStateError,
        RecordIOError
    }

    enum CameraType{
        Picture,
        Vedio
    }

    void onCaptureCommit(Bitmap bitmap);

    void onRecordCommit(String url, Bitmap firstFrame);

    void onActionSuccess(CameraType type);

    void onError(ErrorType errorType);

}
