package com.zx.zxutils.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.zx.zxutils.ZXApp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

;

/**
 * Created by Xiangb on 2017/5/2.
 * 功能：服务相关工具类
 */

public class ZXServiceUtil {

    /**
     * 获取所有运行的服务
     *
     * @return 服务名集合
     */
    public static Set getAllRunningService() {
        ActivityManager activityManager = (ActivityManager) ZXApp.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = activityManager.getRunningServices(0x7FFFFFFF);
        Set<String> names = new HashSet<>();
        if (infos == null || infos.size() == 0) return null;
        for (RunningServiceInfo info : infos) {
            names.add(info.service.getClassName());
        }
        return names;
    }

    /**
     * 启动服务
     *
     * @param className 完整包名的服务类名
     */
    public static void startService(String className) {
        try {
            startService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务
     *
     * @param cls     服务类
     */
    public static void startService(Class<?> cls) {
        Intent intent = new Intent(ZXApp.getContext(), cls);
        ZXApp.getContext().startService(intent);
    }

    /**
     * 停止服务
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(String className) {
        try {
            return stopService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止服务
     *
     * @param cls     服务类
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService( Class<?> cls) {
        Intent intent = new Intent(ZXApp.getContext(), cls);
        return ZXApp.getContext().stopService(intent);
    }

    /**
     * 绑定服务
     *
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项
     *                  <ul>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(String className, ServiceConnection conn, int flags) {
        try {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务
     *
     * @param cls     服务类
     * @param conn    服务连接对象
     * @param flags   绑定选项
     *                <ul>
     *                <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                </ul>
     */
    public static void bindService(Class<?> cls, ServiceConnection conn, int flags) {
        Intent intent = new Intent(ZXApp.getContext(), cls);
        ZXApp.getContext().bindService(intent, conn, flags);
    }

    /**
     * 解绑服务
     *
     * @param conn    服务连接对象
     */
    public static void unbindService(ServiceConnection conn) {
        ZXApp.getContext().unbindService(conn);
    }

    /**
     * 判断服务是否运行
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isServiceRunning(String className) {
        ActivityManager activityManager = (ActivityManager) ZXApp.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = activityManager.getRunningServices(0x7FFFFFFF);
        if (infos == null || infos.size() == 0) return false;
        for (RunningServiceInfo info : infos) {
            if (className.equals(info.service.getClassName())) return true;
        }
        return false;
    }

}
