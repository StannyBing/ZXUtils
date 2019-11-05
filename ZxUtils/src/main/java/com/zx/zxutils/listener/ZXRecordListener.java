package com.zx.zxutils.listener;

import java.io.File;

/**
 * Created by Xiangb on 2017/7/31.
 * 功能：
 */

public interface ZXRecordListener {
    String onInitPath();

    void onSuccess(File file);
}
