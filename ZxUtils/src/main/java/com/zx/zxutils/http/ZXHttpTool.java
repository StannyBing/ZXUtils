package com.zx.zxutils.http;

import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.zx.zxutils.util.ZXLogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Xiangb on 2019/10/15.
 * 功能：网络工具
 */
public class ZXHttpTool {

    private static OkHttpClient httpClient;
    private static Handler handle = new Handler();

    /**
     * 发起get请求
     *
     * @param <T>
     */
    public static <T> void getHttp(String url, Map<String, String> map, ZXHttpListener<T> listener) {
        if (url.isEmpty()) return;
        if (httpClient == null) httpClient = new OkHttpClient();
        if (!map.isEmpty()) {
            StringBuilder urlBuilder = new StringBuilder(url);
            urlBuilder.append("?");
            for (String key : map.keySet()) {
                urlBuilder.append(key).append("=").append(map.get(key)).append("&");
            }
            url = urlBuilder.toString();
        }
        ZXLogUtil.loge("request:" + url);
        handle.post(() -> listener.onStart());
        httpClient.newCall(new Request.Builder()
                .url(url)
                .get()
                .build())
                .enqueue(getCallBack(listener));
    }

    /**
     * 发起post请求
     *
     * @param url
     * @param map
     * @param listener
     * @param <T>
     */
    public static <T> void postHttp(String url, Map<String, String> map, ZXHttpListener<T> listener) {
        if (url.isEmpty()) return;
        if (httpClient == null) httpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        ZXLogUtil.loge("request:" + url);
        handle.post(() -> listener.onStart());
        httpClient.newCall(new Request.Builder()
                .url(url)
                .post(builder.build())
                .build())
                .enqueue(getCallBack(listener));
    }

    /**
     * 上传文件
     *
     * @param url
     * @param files
     * @param map      参数文件混传、可为null
     * @param listener
     * @param <T>
     */
    public static <T> void uploadFile(String url, List<File> files, @Nullable Map<String, String> map, ZXHttpListener<T> listener) {
        if (url.isEmpty()) return;
        if (httpClient == null) httpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (File file : files) {
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        UploadRequestBody uploadRequestBody = new UploadRequestBody(builder.build(), (progress, done) -> handle.post(() -> listener.onProgress(progress)));
        ZXLogUtil.loge("request:" + url);
        handle.post(() -> listener.onStart());
        httpClient.newCall(new Request.Builder()
                .url(url)
                .post(uploadRequestBody)
                .build())
                .enqueue(getCallBack(listener));
    }

    /**
     * 下载文件
     *
     * @param url
     * @param listener
     */
    public static void downloadFile(String url, File file, ZXHttpListener<File> listener) {
        if (url.isEmpty()) return;
        if (httpClient == null) httpClient = new OkHttpClient();
        handle.post(() -> listener.onStart());
        ZXLogUtil.loge("request:" + url);
        httpClient.newCall(new Request.Builder()
                .url(url)
                .get()
                .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handle.post(() -> listener.onError("下载失败"));
                        ZXLogUtil.loge(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        //得到服务器所传的文件内容
                        long read = 0;
                        InputStream is = response.body().byteStream();
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                        byte[] buffer = new byte[1024];
                        int len;
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                            while ((len = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                fos.flush();
                                read += len;
                                int progress = (int) (read * 100 / response.body().contentLength());
                                handle.post(() -> listener.onProgress(progress));
                            }
                            handle.post(() -> listener.onResult(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
    }

    private static <T> Callback getCallBack(ZXHttpListener<T> listener) {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handle.post(() -> listener.onError("请求出错"));
                ZXLogUtil.loge(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                ZXLogUtil.loge("response:" + json);
                try {
                    if (listener.mType == String.class) {
                        handle.post(() -> listener.onResult((T) json));
                    } else {
                        handle.post(() -> listener.onResult(new Gson().fromJson(json, listener.mType)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onResult(null);
                }
            }
        };
    }
}
