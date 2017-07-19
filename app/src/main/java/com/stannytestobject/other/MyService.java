package com.stannytestobject.other;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zx.zxutils.util.ZXLogUtil;
import com.zx.zxutils.util.ZXTimeUtil;

/**
 * Created by Xiangb on 2017/5/12.
 * 功能：
 */

public class MyService extends Service {

    private Handler handler = new Handler();

    @Override
    public void onCreate() {
        ZXLogUtil.loge("服务已开启onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ZXLogUtil.loge(ZXTimeUtil.getCurrentTime() + "");
                handler.postDelayed(this, 1000);
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        ZXLogUtil.loge("服务已销毁onDestory");
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ZXLogUtil.loge("服务已绑定onBind");
        return null;
    }
}
