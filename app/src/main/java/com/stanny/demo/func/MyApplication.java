package com.stanny.demo.func;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
}
