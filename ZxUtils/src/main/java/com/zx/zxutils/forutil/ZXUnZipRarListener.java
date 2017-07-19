package com.zx.zxutils.forutil;

/**
 * Created by Xiangb on 2017/7/14.
 * 功能：
 */

public interface ZXUnZipRarListener {

    void onStart();

    void onPregress(int progress);

    void onComplete(String outputPath);

    void onError(String message);

}
