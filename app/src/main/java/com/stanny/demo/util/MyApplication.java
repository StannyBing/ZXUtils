package com.stanny.demo.util;

import android.app.Application;

import com.zx.zxutils.ZXApp;

/**
 * Created by Xiangb on 2017/5/24.
 * 功能：
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ZXApp.init(this, true);
    }
}
