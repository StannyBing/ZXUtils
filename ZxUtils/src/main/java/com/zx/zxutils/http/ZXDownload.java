package com.zx.zxutils.http;

import com.zx.zxutils.http.common.Callback;
import com.zx.zxutils.http.ex.HttpException;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Created by Xiangb on 2017/8/28.
 * 功能：
 */

public class ZXDownload {
    private ZXHttpListener mListener;
    private Callback.Cancelable cancelable;

    public Callback.Cancelable downLoad(String downLoadUrl, String savePath, boolean autoRename, ZXHttpListener httpListener) {
        mListener = httpListener;
        ZXApiParams apiParams = new ZXApiParams()
                .setApiUrl(downLoadUrl)
//                .setApiMethod(HTTP_MOTHOD.DOWNLOAD)
                //自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
                .setSavePath(savePath)
                //自动为文件命名
                .setAutoRename(autoRename);

        cancelable = x.http().post(apiParams._requestParams(), new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                ZXBaseResult baseResult = new ZXBaseResult();
                baseResult.setFile(result);
                baseResult.setSuccess(true);
                baseResult.setFile(result);
                mListener.OnHttpSuccess(0, baseResult);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (mListener != null) {
                    mListener.OnHttpError(0, checkExcption(ex));
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                mListener.OnHttpStart(0);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int progress = (int) ((current * 100) / total);
                mListener.OnHttpProgress(0, progress);
            }
        });
        return cancelable;
    }

    /**
     * 检查出错原因
     *
     * @param ex 错误抛出
     * @return
     */
    private String checkExcption(Throwable ex) {
        String errorMsg = "网络请求出错";
        if (ex instanceof NullPointerException) {
            errorMsg = "请检查getHttpResult中是否设置相应返回";
        } else if (ex instanceof SocketException) {
            errorMsg = "请确认已添加网络请求权限";
        } else if (ex instanceof ConnectException) {
            errorMsg = "地址无法连接";
        } else if (ex instanceof SocketTimeoutException) {
            errorMsg = "请求超时";
        } else if (ex instanceof HttpException && ((HttpException) ex).getCode() == 400) {
            errorMsg = "与服务器的请求出错";
        } else if (ex instanceof HttpException && ((HttpException) ex).getCode() == 404) {
            errorMsg = "未找到该请求地址";
        }
        return errorMsg;
    }

}
