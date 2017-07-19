package com.zx.zxutils.http;


import com.zx.zxutils.http.common.Callback;
import com.zx.zxutils.http.ex.HttpException;
import com.zx.zxutils.util.ZXFileUtil;
import com.zx.zxutils.util.ZXLogUtil;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Xiangb on 2017/5/23.
 * 功能：http请求，使用时，请继承该类，并实现方法
 */

public abstract class ZXHttpApi {
    private int apiId = 0;//网络请求id，用于区分类型
    private Callback.Cancelable cancelable;
    private ZXApiParams apiParams;
    private ZXHttpListener mListener;
    private boolean isLoad = false;
    public ZXApiParams params = new ZXApiParams();
    public ZXBaseResult result = new ZXBaseResult();

    public enum HTTP_MOTHOD {
        POST,
        GET,
        DOWNLOAD,
        UPLOAD
    }

    /**
     * 构造
     *
     * @param apiType
     */
    public ZXHttpApi(int apiType) {
        this.apiId = apiType;
    }

    public int getApiId() {
        return apiId;
    }

    /**
     * 加载数据
     *
     * @param parmas
     */
    public void loadData(Object... parmas) {
        params.clearParam();
        try {
            apiParams = getHttpParams(apiId, parmas);
        } catch (Exception e) {
            ZXLogUtil.loge("请求出错，请检查是否有参数未填入");
            e.printStackTrace();
        }
        if (apiParams.getApiMethod() == HTTP_MOTHOD.UPLOAD) {
            start();
        } else if (apiParams.getApiMethod() == HTTP_MOTHOD.DOWNLOAD) {
            start();
        } else if (apiParams.getApiMethod() == HTTP_MOTHOD.POST) {
            post();
        } else {
            get();
        }
    }

    //抽象-获取http请求参数
    public abstract ZXApiParams getHttpParams(int apiType, Object... infos) throws Exception;

    //抽象-得到http请求结果
    public abstract ZXBaseResult getHttpResult(int apiType, String response) throws Exception;

    /**
     * 设置请求监听
     *
     * @param mListener
     */
    public void setHttpListener(ZXHttpListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 下载
     *
     * @param downLoadUrl  下载地址
     * @param savePath     保存路径
     * @param autoRename   是否根据文件名自动重命名
     * @param httpListener 下载监听
     */
    public void downLoadFile(String downLoadUrl, String savePath, boolean autoRename, ZXHttpListener httpListener) {
        mListener = httpListener;
        apiParams = new ZXApiParams()
                .setApiUrl(downLoadUrl)
                .setApiMethod(HTTP_MOTHOD.DOWNLOAD)
                //自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
                .setSavePath(savePath)
                //自动为文件命名
                .setAutoRename(autoRename);
        start();
    }

    /**
     * 上传(单独调用上传的方式)
     *
     * @param uploadUrl 上传地址
     * @param file      文件
     */
    public void uploadFile(String uploadUrl, String fileKey, File file, ZXHttpListener httpListener) {
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put(fileKey, file);
        uploadFile(uploadUrl, fileMap, httpListener);
    }

    /**
     * 上传(单独调用上传的方式)
     *
     * @param uploadUrl 上传地址
     * @param fileMap   文件map
     */
    public void uploadFile(String uploadUrl, Map<String, File> fileMap, ZXHttpListener httpListener) {
        uploadFile(uploadUrl, null, fileMap, httpListener);
    }

    /**
     * 上传(单独调用上传的方式)
     *
     * @param uploadUrl 上传地址
     * @param dataMap   信息map
     * @param fileMap   文件map
     */
    public void uploadFile(String uploadUrl, Map<String, String> dataMap, Map<String, File> fileMap, ZXHttpListener httpListener) {
        mListener = httpListener;
        apiParams = new ZXApiParams();
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
        start();
    }

    /**
     * 使用get进行网络请求
     */
    private void get() {
        loadStart();
        cancelable = x.http().get(apiParams._requestParams(), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                getResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadError(checkExcption(ex));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 使用post进行网络请求
     */
    private void post() {
        loadStart();
        cancelable = x.http().post(apiParams._requestParams(), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                getResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadError(checkExcption(ex));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 用于对请求进行出错处理
     *
     * @param res
     * @return
     */
    private ZXBaseResult getResult(String res) {
        try {
            getHttpResult(apiId, res);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        if (result.isSuccess()) {
            ZXLogUtil.loge("complete" + res);
            loadComplete(result);
        } else {
            ZXLogUtil.loge("error" + res);
            loadError(result.getMessage());
        }
        return result;
    }

    /**
     * 暂停
     */
    public void pauseDownload() {
        isLoad = false;
        if (cancelable != null && !cancelable.isCancelled()) {
            cancelable.cancel();
        }
    }

    /**
     * 取消
     */
    public void cancel() {
        isLoad = false;
        pauseDownload();
        if (apiParams.getApiMethod() == HTTP_MOTHOD.DOWNLOAD) {
            ZXFileUtil.deleteFiles(apiParams.getSavePath() + ".tmp");
            loadProgress(0);
        } else if (apiParams.getApiMethod() == HTTP_MOTHOD.UPLOAD) {
            loadProgress(0);
        } else {
            //TODO
        }
    }

    /**
     * 开始
     */
    public void start() {
        switch (apiParams.getApiMethod()) {
            case GET:
            case POST:

                break;
            case DOWNLOAD:
                if (!isLoad) {
                    startDownload();
                }
                break;
            case UPLOAD:
                if (!isLoad) {
                    startUpload();
                }
                break;
            default:
                break;
        }

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

    /**
     * 开始上传
     */
    private void startUpload() {
        loadStart();
        Map<String, File> fileMap = apiParams.getFileMap();
        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
            if (!entry.getValue().exists()) {
                loadError("文件:" + entry.getValue().getName() + "不存在");
                return;
            }
        }
        cancelable = x.http().post(apiParams._requestParams(), new Callback.ProgressCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getResult(result);
                cancelable.cancel();
                isLoad = false;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadError(checkExcption(ex));
                isLoad = false;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                isLoad = false;
            }

            @Override
            public void onFinished() {
                isLoad = false;
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                isLoad = true;
            }

            @Override
            public void onLoading(long total, long current, boolean isUoloading) {
                int progress = (int) ((current * 100) / total);
                loadProgress(progress);
            }
        });
    }

    /**
     * 开始下载
     */
    private void startDownload() {
        loadStart();
        cancelable = x.http().post(apiParams._requestParams(), new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                ZXBaseResult baseResult = getResult("");
                baseResult.setFile(result);
                isLoad = false;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadError(checkExcption(ex));
                isLoad = false;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                isLoad = false;
            }

            @Override
            public void onFinished() {
                isLoad = false;
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                isLoad = true;
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int progress = (int) ((current * 100) / total);
                loadProgress(progress);
            }
        });
    }

    /**
     * 加载成功
     *
     * @param result 结果
     */
    private void loadComplete(ZXBaseResult result) {
        if (mListener != null) {
            mListener.OnHttpSuccess(apiId, result);
        }
    }

    /**
     * 加载失败
     *
     * @param error error信息
     */
    private void loadError(String error) {
        if (mListener != null) {
            mListener.OnHttpError(apiId, error);
        }
    }

    /**
     * 开始加载
     */
    private void loadStart() {
        ZXLogUtil.loge(apiParams._requestParams().toString());
        if (mListener != null) {
            mListener.OnHttpStart(apiId);
        }
    }

    /**
     * 上传/下载进度
     *
     * @param progress 进度(0~100)
     */
    private void loadProgress(int progress) {
        if (mListener != null) {
            mListener.OnHttpProgress(apiId, progress);
        }
    }

}
