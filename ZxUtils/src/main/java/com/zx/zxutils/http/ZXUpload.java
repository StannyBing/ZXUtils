package com.zx.zxutils.http;

import com.zx.zxutils.http.common.Callback;
import com.zx.zxutils.http.ex.HttpException;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xiangb on 2017/8/28.
 * 功能：
 */

public class ZXUpload {
    private ZXHttpListener mListener;
    private Callback.Cancelable cancelable;
    private int apiId = 0;//网络请求id，用于区分类型

    public Callback.Cancelable upload(String uploadUrl, String fileKey, File file, ZXHttpListener httpListener) {
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put(fileKey, file);
        return upload(uploadUrl, null, fileMap, httpListener);
    }

    public Callback.Cancelable upload(String uploadUrl, Map<String, String> dataMap, String fileKey, File file, ZXHttpListener httpListener) {
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put(fileKey, file);
        return upload(uploadUrl, dataMap, fileMap, httpListener);
    }

    public Callback.Cancelable upload(String uploadUrl, Map<String, File> fileMap, ZXHttpListener httpListener) {
        return upload(uploadUrl, null, fileMap, httpListener);
    }

    public Callback.Cancelable upload(String uploadUrl, Map<String, String> dataMap, Map<String, File> fileMap, ZXHttpListener httpListener) {
        mListener = httpListener;
        ZXApiParams apiParams = new ZXApiParams();
        apiParams.setApiUrl(uploadUrl);
        if (fileMap != null) {
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                apiParams.addParam(entry.getKey(), entry.getValue());
            }
        }
        if (dataMap != null) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                apiParams.addParam(entry.getKey(), entry.getValue());
            }
        }
        cancelable = x.http().post(apiParams._requestParams(), new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(int requestCode, String result) {
                ZXBaseResult baseResult = new ZXBaseResult();
                baseResult.setSuccess(true);
                baseResult.setEntry(result);
                mListener.OnHttpSuccess(0, baseResult);
                cancelable.cancel();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mListener.OnHttpError(0, checkExcption(ex));
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
            public void onLoading(long total, long current, boolean isUoloading) {
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
