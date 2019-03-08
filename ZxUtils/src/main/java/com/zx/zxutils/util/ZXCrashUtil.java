package com.zx.zxutils.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Xiangb on 2017/4/28.
 * 功能：崩溃相关工具类
 */

public final class ZXCrashUtil {
    private CrashHandler mCrashHandler;

    private static ZXCrashUtil mInstance;

    private ZXCrashUtil() {

    }

    private static ZXCrashUtil getInstance() {
        if (mInstance == null) {
            synchronized (ZXCrashUtil.class) {
                if (mInstance == null) {
                    mInstance = new ZXCrashUtil();
                }
            }
        }

        return mInstance;
    }

    public static void init(boolean openCrashCatch, CrashHandler crashHandler) {
        if (openCrashCatch) {
            getInstance().setCrashHandler(crashHandler);
        }
    }

    private void setCrashHandler(CrashHandler crashHandler) {

        mCrashHandler = crashHandler;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if (mCrashHandler != null) {//捕获异常处理
                            mCrashHandler.uncaughtException(Looper.getMainLooper().getThread(), e);
                        }
                    }
                }
            }
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (mCrashHandler != null) {//捕获异常处理
                    mCrashHandler.uncaughtException(t, e);
                }
            }
        });

    }

    public interface CrashHandler {
        void uncaughtException(Thread t, Throwable e);
    }
}
