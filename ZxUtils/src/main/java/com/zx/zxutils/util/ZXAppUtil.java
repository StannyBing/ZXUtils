package com.zx.zxutils.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zx.zxutils.ZXApp;

import java.io.File;
import java.util.List;

/**
 * Created by Xiangb on 2017/4/28.
 * 功能：App相关工具类
 */

public class ZXAppUtil {

    private static PackageManager packageManager = null;
    private static ApplicationInfo applicationInfo = null;
    private static PackageInfo packageInfo = null;

    /**
     * 安装App(支持6.0)
     *
     * @param filePath 文件路径
     */
    public static void installApp(Context context, String filePath) {
        installApp(context, ZXFileUtil.getFileByPath(filePath));
    }

    /**
     * 安装App（支持6.0）
     *
     * @param context 上下文
     * @param file    文件
     */
    public static void installApp(Context context, File file) {
        if (!ZXFileUtil.isFileExists(file)) {
            return;
        } else {
            context.startActivity(ZXIntentUtil.getInstallAppIntent(file));
        }
    }

    /**
     * 安装App（支持6.0）
     *
     * @param activity    activity
     * @param filePath    文件路径
     * @param requestCode 请求值
     */
    public static void installApp(Activity activity, String filePath, int requestCode) {
        installApp(activity, ZXFileUtil.getFileByPath(filePath), requestCode);
    }

    /**
     * 安装App(支持6.0)
     *
     * @param activity    activity
     * @param file        文件
     * @param requestCode 请求值
     */
    public static void installApp(Activity activity, File file, int requestCode) {
        if (!ZXFileUtil.isFileExists(file)) return;
        activity.startActivityForResult(ZXIntentUtil.getInstallAppIntent(file), requestCode);
    }

    /**
     * 卸载App
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void uninstallApp(Context context, String packageName) {
        if (ZXStringUtil.isEmpty(packageName)) {
            return;
        } else {
            context.startActivity(ZXIntentUtil.getUninstallAppIntent(packageName));
        }
    }

    /**
     * 卸载App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void uninstallApp(Activity activity, String packageName, int requestCode) {
        if (ZXStringUtil.isEmpty(packageName)) {
            return;
        }
        activity.startActivityForResult(ZXIntentUtil.getUninstallAppIntent(packageName), requestCode);
    }

    /**
     * 功能:获取PackageManager对象
     *
     * @return
     */
    private static PackageManager getPackageManager() {
        if (packageManager == null) {
            packageManager = ZXApp.getContext().getApplicationContext().getPackageManager();
        }
        return packageManager;
    }

    /**
     * 功能:获取App程序名
     *
     * @return
     */
    public static String getAppName() {
        String applicationName = getPackageManager().getApplicationLabel(
                getApplicationInfo()).toString();
        return applicationName;
    }

    /**
     * 功能:获取App包名
     *
     * @return
     */
    public static String getPackageName() {
        String packageName = getPackageInfo().packageName;
        return packageName;
    }

    /**
     * 功能:获取ApplicationInfo对象
     *
     * @return mTelephonyManager
     */
    private static ApplicationInfo getApplicationInfo() {
        if (applicationInfo == null) {
            try {
                applicationInfo = getPackageManager().getApplicationInfo(
                        ZXApp.getContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                applicationInfo = null;
            }
        }
        return applicationInfo;
    }

    /**
     * 功能:获取PackageInfo对象
     *
     * @return
     */
    private static PackageInfo getPackageInfo() {
        if (packageInfo == null) {
            try {
                packageInfo = getPackageManager()
                        .getPackageInfo(ZXApp.getContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                packageInfo = null;
            }
        }
        return packageInfo;
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static String getAppVersionName() {
        return getAppVersionName(ZXApp.getContext().getPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(String packageName) {
        if (ZXStringUtil.isEmpty(packageName)) {
            return null;
        }
        try {
            PackageManager pm = ZXApp.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断App是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        ActivityManager manager = (ActivityManager) ZXApp.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return info.processName.equals(ZXApp.getContext().getPackageName());
            }
        }
        return false;
    }

    /**
     * 判断App是否处于前台
     * <p>当不是查看当前App，且SDK大于21时，
     * 需添加权限 {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>}</p>
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground(String packageName) {
        return !ZXStringUtil.isEmpty(packageName) && packageName.equals(ZXProcessUtil.getForegroundProcessName(ZXApp.getContext()));
    }

    /**
     * 清除App所有数据
     *
     * @param dirPaths 目录路径
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除App所有数据
     *
     * @param dirs 目录
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(File... dirs) {
        boolean isSuccess = cleanInternalCache();
        isSuccess &= cleanInternalDbs();
        isSuccess &= cleanInternalSP();
        isSuccess &= cleanInternalFiles();
        for (File dir : dirs) {
            isSuccess &= ZXFileUtil.deleteFiles(dir);
        }
        return isSuccess;
    }

    /**
     * 清除内部缓存
     * <p>/data/data/com.xxx.xxx/cache</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalCache() {
        return ZXFileUtil.deleteFiles(ZXApp.getContext().getCacheDir());
    }

    /**
     * 清除内部文件
     * <p>/data/data/com.xxx.xxx/files</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalFiles() {
        return ZXFileUtil.deleteFiles(ZXApp.getContext().getFilesDir());
    }

    /**
     * 清除内部数据库
     * <p>/data/data/com.xxx.xxx/databases</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbs() {
        return ZXFileUtil.deleteFiles(ZXApp.getContext().getFilesDir().getParent() + File.separator + "databases");
    }

    /**
     * 根据名称清除数据库
     * <p>/data/data/com.xxx.xxx/databases/dbName</p>
     *
     * @param dbName 数据库名称
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalDbByName(String dbName) {
        return ZXApp.getContext().deleteDatabase(dbName);
    }

    /**
     * 清除内部SP
     * <p>/data/data/com.xxx.xxx/shared_prefs</p>
     *
     * @return {@code true}: 清除成功<br>{@code false}: 清除失败
     */
    public static boolean cleanInternalSP() {
        return ZXFileUtil.deleteFiles(ZXApp.getContext().getFilesDir().getParent() + File.separator + "shared_prefs");
    }

}
