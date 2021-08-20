package com.zx.zxutils.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.ContactsContract;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zx.zxutils.ZXApp;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by Xiangb on 2017/3/30.
 * 功能：系统相关工具类
 */
public class ZXSystemUtil {

    private static PackageManager packageManager = null;
    private static ApplicationInfo applicationInfo = null;
    private static PackageInfo packageInfo = null;
    private static NetworkInfo.State wifiState = null;
    private static NetworkInfo.State mobileState = null;
    private static TelephonyManager mTelephonyManager = null;
    private static final int NETSTATUS_WIFI = 0;
    private static final int NETSTATUS_MOBILE = 1;
    private static final int NETSTATUS_NONE = 2;

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        if (isSDCardExist()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator;
        } else {
            return null;
        }
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardLastSize() {
        if (isSDCardExist()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath 路径
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getSystemPath() {
        return Environment.getDataDirectory().getPath() + File.separator;
    }

    /**
     * 获取app内部存储路径
     *
     * @param context
     * @return
     */
    public static String getAppDataPath(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 功能:获取PackageManager对象
     *
     * @return
     */
    private static PackageManager getPackageManager() {
        if (packageManager == null) {
            packageManager = ZXApp.getContext().getPackageManager();
        }
        return packageManager;
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
     * 功能:获取版本名字
     *
     * @return
     */
    public static String getVersionName() {
        String versionName = getPackageInfo().versionName;
        return versionName;
    }

    /**
     * 功能:获取程序版本号
     *
     * @return
     */
    public static int getVersionCode() {
        int versionCode = getPackageInfo().versionCode;
        return versionCode;
    }

    /**
     * 功能:获取程序的签名
     *
     * @return
     */
    public static String getAppSignature() {
        String appSignature = getPackageInfo().signatures[0].toCharsString();
        return appSignature;
    }


    /**
     * 获取当前的网络状态
     *
     * @return
     */
    public static int getNetWorkState() {
        ConnectivityManager manager = (ConnectivityManager) ZXApp.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null) { // connected to the internet
            if (manager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
                wifiState = manager.getActiveNetworkInfo().getState();
            } else if (manager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE) {
                mobileState = manager.getActiveNetworkInfo().getState();
            }
        }
//		wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//		mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED == mobileState) {
            return NETSTATUS_MOBILE;
        } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState
                && NetworkInfo.State.CONNECTED != mobileState) {
            return NETSTATUS_NONE;
        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            return NETSTATUS_WIFI;
        }
        return NETSTATUS_NONE;
    }

    private static TelephonyManager getTelephonyManager() {
        if (mTelephonyManager == null) {
            try {
                mTelephonyManager = (TelephonyManager) ZXApp.getContext()
                        .getSystemService(Context.TELEPHONY_SERVICE);
            } catch (Exception e) {
                mTelephonyManager = null;
            }
        }
        return mTelephonyManager;
    }

    /**
     * 获取设备IMEI号码
     *
     * @return
     */
    public static String getIMEI() {
        return getTelephonyManager().getDeviceId();
    }

    /**
     * dp转px
     *
     * @param dpVal dp值
     * @return
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, ZXApp.getContext().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal sp值
     * @return
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, ZXApp.getContext().getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal px值
     * @return
     */
    public static float px2dp(float pxVal) {
        final float scale = ZXApp.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal px值
     * @return
     */
    public static float px2sp(float pxVal) {
        return (pxVal / ZXApp.getContext().getResources().getDisplayMetrics().scaledDensity);
    }

    public static int transColor(int color) {
        try {
            if (color > 0) {
                return ContextCompat.getColor(ZXApp.getContext(), color);
            } else {
                return color;
            }
        } catch (Exception e) {
            return color;
        }
    }

    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     */
    public static void openKeybord(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) ZXApp.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param activity 上下文
     */
    public static void closeKeybord(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * 调用系统发短信界面
     *
     * @param phoneNumber 手机号码
     * @param smsContent  短信内容
     */
    public static void sendMessage(Context context, String phoneNumber, String smsContent) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return;
        }
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsContent);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }

    /**
     * 调用系统拨号界面
     *
     * @param phoneNumber
     */
    public static void callTo(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    /**
     * 获取手机型号
     *
     * @return String
     */
    public static String getMobileModel() {
        try {
            String model = android.os.Build.MODEL; // 手机型号
            return model;
        } catch (Exception e) {
            return "未知";
        }
    }

    /**
     * 获取手机品牌
     *
     * @return String
     */
    public static String getMobileBrand() {
        try {
            String brand = android.os.Build.BRAND; // android系统版本号
            return brand;
        } catch (Exception e) {
            return "未知";
        }
    }

    /**
     * 获取所有联系人的姓名和电话号码，需要READ_CONTACTS权限
     *
     * @return Cursor。姓名：CommonDataKinds.Phone.DISPLAY_NAME；号码：CommonDataKinds.Phone.NUMBER
     */
    public static Cursor getContactsNameAndNumber() {
        return ZXApp.getContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    /**
     * 获取手机号码
     *
     * @return 手机号码，手机号码不一定能获取到
     */
    public static String getMobilePhoneNumber() {
        return ((TelephonyManager) ZXApp.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }

    /**
     * 拨打电话
     *
     * @param photoNum
     * @return
     */
    public static boolean dialTo(Context context, String photoNum) {
        if (ZXStringUtil.isEmpty(photoNum)) {
            return false;
        } else {
            context.startActivity(ZXIntentUtil.getDialIntent(photoNum));
            return true;
        }
    }

    public static boolean messageTo(Context context, String photoNum, String messageDetail) {
        if (ZXStringUtil.isEmpty(photoNum)) {
            return false;
        } else {
            context.startActivity(ZXIntentUtil.getSendSmsIntent(photoNum, messageDetail));
            return true;
        }
    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }


    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        return Settings.Secure.getString(ZXApp.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    public static String getMacAddress() {
        String macAddress = getMacAddressByWifiInfo();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        return "please open wifi";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    private static String getMacAddressByWifiInfo() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifi = (WifiManager) ZXApp.getContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) return info.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    private static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备厂商
     * <p>如Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     * <p>如MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 判断设备是否是手机
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) ZXApp.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 判断sim卡是否准备好
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSimCardReady() {
        TelephonyManager tm = (TelephonyManager) ZXApp.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }
}
