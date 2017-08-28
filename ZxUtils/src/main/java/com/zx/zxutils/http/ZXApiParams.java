package com.zx.zxutils.http;

import com.zx.zxutils.http.ZXHttpApi.HTTP_MOTHOD;
import com.zx.zxutils.http.common.util.KeyValue;
import com.zx.zxutils.http.http.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangb on 2017/5/25.
 * 功能：网络请求param
 */

public class ZXApiParams {

    private String apiUrl;//网络请求地址
    private HTTP_MOTHOD method = HTTP_MOTHOD.POST;//网络请求类型
    private RequestParams params;
    private String savePath;//文件保存地址
    private boolean autoRename = true;//是否自动重命名下载的文件
    private HashMap<String, File> fileMap = new HashMap<>();

    public String getSavePath() {
        return savePath;
    }

    /**
     * 文件下载时，需要设置，设置文件下载保存地址
     *
     * @param savePath
     * @return
     */
    public ZXApiParams setSavePath(Object savePath) {
        if (params != null) {
            this.savePath = savePath.toString();
            params.setSaveFilePath(this.savePath);
        }
        return this;
    }

    public boolean isAutoRename() {
        return autoRename;
    }

    /**
     * 文件下载时，需要设置，设置是否自动命名文件
     *
     * @param autoRename
     * @return
     */
    public ZXApiParams setAutoRename(Object autoRename) {
        if (params != null) {
            try {
                this.autoRename = (boolean) autoRename;
            } catch (Exception e) {
                e.printStackTrace();
                this.autoRename = true;
            }
            params.setAutoRename(this.autoRename);
        }
        return this;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * 设置请求地址，注意必须要先setApiUrl，因为在此时构造的RequestParams
     *
     * @param apiUrl
     */
    public ZXApiParams setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        params = new RequestParams(apiUrl);
        fileMap.clear();
        return this;
    }

    public HTTP_MOTHOD getApiMethod() {
        return method;
    }

    /**
     * 设置请求方法
     *
     * @param method
     */
    public ZXApiParams setApiMethod(HTTP_MOTHOD method) {
        this.method = method;
        return this;
    }

    /**
     * 添加param
     *
     * @param key   key
     * @param value value
     */
    public ZXApiParams addParam(String key, Object value) {
        if (params != null) {
            if (value == null) {
                params.addBodyParameter(key, "");
            } else if (value instanceof File) {
                fileMap.put(key, (File) value);
//                params.addBodyParameter(key, (File) value);
            } else {
                params.addBodyParameter(key, value.toString());
            }
        }
        return this;
    }

    /**
     * 添加文件集合
     *
     * @param fileMap
     * @return
     */
    public ZXApiParams addFileMap(Map<String, File> fileMap) {
        if (fileMap != null) {
            fileMap.putAll(fileMap);
        }
        return this;
    }

    /**
     * 添加数据集合
     *
     * @param dataMap
     * @return
     */
    public ZXApiParams addDataMap(Map<String, String> dataMap) {
        if (params != null && dataMap != null) {
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    /**
     * 获取params
     */
    public List<KeyValue> getParams() {
        if (params != null) {
            return params.getBodyParams();
        } else {
            return null;
        }
    }

    /**
     * 清空param
     */
    public void clearParam() {
        if (params != null) {
            params.clearParams();
        }
    }

    public HashMap<String, File> getFileMap() {
        return fileMap;
    }

    /**
     * 开发不必使用
     *
     * @return
     */
    public RequestParams _requestParams() {
        return params;
    }

}
