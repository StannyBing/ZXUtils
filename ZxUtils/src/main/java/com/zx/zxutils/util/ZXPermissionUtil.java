package com.zx.zxutils.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;

import com.zx.zxutils.ZXApp;

/**
 * Created by Xiangb on 2017/3/31.
 * 功能：权限申请工具类
 */
public class ZXPermissionUtil {

    private static String[] requestPermissionArray;
    private static String singlePermission = "";
    private static boolean isRequest;
    private static int requestCode = 0;

    /**
     * 启动app设置授权界面
     *
     * @param context
     */
    public static void startSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 检查单个权限
     *
     * @param permission
     * @return
     */
    public static boolean checkSinglePermission(String permission) {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 申请单个权限
     *
     * @param activity
     */
    public static void requestSinglePermission(Activity activity) {
        if (singlePermission.length() == 0) {
            return;
        }
        String[] tempPermission = new String[1];
        tempPermission[0] = singlePermission;
        ActivityCompat.requestPermissions(activity, tempPermission, requestCode);
    }

    /**
     * 根据传入的权限列表进行检查
     *
     * @param permissionArray
     * @return
     */
    public static boolean checkPermissionsByArray(String[] permissionArray) {
        requestPermissionArray = permissionArray;
        isRequest = true;
        for (String permission : permissionArray) {
            if (ActivityCompat.checkSelfPermission(ZXApp.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {//未注册
                isRequest = false;
            }
        }
        return isRequest;
    }

    /**
     * 根据传入的权限进行请求
     */
    public static void requestPermissionsByArray(Activity activity) {
        if (requestPermissionArray == null) {
            return;
        }
        ActivityCompat.requestPermissions(activity, requestPermissionArray, requestCode);
    }

    public final static String[] ContactsPermissions = new String[]{Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS};

    /**
     * 通讯录权限
     *
     * @return
     */
    public static boolean checkContactsPermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestContactsPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, ContactsPermissions, requestCode);
    }

    public final static String[] PhonePermissions = new String[]{Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.ADD_VOICEMAIL};

    /**
     * 通话权限
     *
     * @return
     */
    public static boolean checkPhonePermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.USE_SIP) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.ADD_VOICEMAIL) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestPhonePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, PhonePermissions, requestCode);
    }

    public final static String[] CalendarPermissions = new String[]{Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};

    /**
     * 日历权限
     *
     * @return
     */
    public static boolean checkCalendarPermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestCalendarPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, CalendarPermissions, requestCode);
    }

    public final static String[] CameraPermissions = new String[]{Manifest.permission.CAMERA};

    /**
     * 相机权限
     *
     * @return
     */
    public static boolean checkCameraPermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestCameraPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, CameraPermissions, requestCode);
    }

    public final static String[] BodySensorsPermissions = new String[]{Manifest.permission.BODY_SENSORS};

    /**
     * 身体传感器权限
     *
     * @return
     */
    public static boolean checkBodySensorsPermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestBodySensorsPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, BodySensorsPermissions, requestCode);
    }

    public final static String[] LocationPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    /**
     * 定位权限
     *
     * @return
     */
    public static boolean checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestLocationPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, LocationPermissions, requestCode);
    }

    public final static String[] MicrophonePermissions = new String[]{Manifest.permission.RECORD_AUDIO};

    /**
     * 录音权限
     *
     * @return
     */
    public static boolean checkMicrophonePermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestMicrophonePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, MicrophonePermissions, requestCode);
    }

    public final static String[] SmsPermissions = new String[]{Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH, Manifest.permission.RECEIVE_MMS, Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS};

    /**
     * 短信权限
     *
     * @return
     */
    public static boolean checkSmsPermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.RECEIVE_WAP_PUSH) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.RECEIVE_MMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestSmsPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, SmsPermissions, requestCode);
    }

    public final static String[] StoragePermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 存储权限
     *
     * @return
     */
    public static boolean checkStoragePermissions() {
        if (ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(ZXApp.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {// 没有权限
            return false;
        } else {
            return true;
        }
    }

    public static void requestStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, StoragePermissions, requestCode);
    }

}
