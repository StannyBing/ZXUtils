package com.zx.zxutils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Xiangb on 2017/6/28.
 * 功能：使用前需要在当前的application中进行初始化
 */

public class ZXApp {

    private static boolean isDebug = false;
    private static Context zxContext;

    /**
     * 初始化
     *
     * @param context
     * @param isDebug
     */
    public static void init(Application context, boolean isDebug) {
        zxContext = context;
        ZXApp.isDebug = isDebug;
    }

    /**
     * 返回是否为debug状态
     *
     * @return
     */
    public static boolean isDebug() {
        return isDebug;
    }

    /**
     * 获取context对象
     *
     * @return
     */
    public static Context getContext() {
        if (zxContext != null) {
            return zxContext;
        } else {
            throw new RuntimeException("Please confirm you are registered in Application ( ZXApp.init( this ,false )) ");
        }
    }

}
