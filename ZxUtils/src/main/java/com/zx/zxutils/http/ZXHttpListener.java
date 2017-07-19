package com.zx.zxutils.http;

/**
 * Created by Xiangb on 2017/5/24.
 * 功能：基础网络请求监听
 */

public interface ZXHttpListener {

    void OnHttpStart(int apiType);

    void OnHttpError(int apiType, String errorMsg);

    void OnHttpSuccess(int apiType, ZXBaseResult baseResult);

    void OnHttpProgress(int apiType, int progress);

}
