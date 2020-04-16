package com.zx.zxutils.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.zx.zxutils.ZXApp;

import java.util.UUID;

/**
 * Created by Xiangb on 2017/6/5.
 * 功能：唯一码工具类
 */

public class ZXUniqueIdUtil {

    private static ZXSharedPrefUtil prefUtil;
    public static String method;

    public static String getUniqueId() {
        prefUtil = new ZXSharedPrefUtil();
        //首先从缓存中读取
        String sharedId = prefUtil.getString("uniqueId");
        if (sharedId != null && sharedId.length() > 0) {
            method = "shared";
            return sharedId;
        }
        //第二获取androidId,缺点:一些手机可能会返回相同编码“9774d56d682e549c”或者null
        String androidId = Settings.Secure.getString(ZXApp.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!"9774d56d682e549c".equals(androidId) && androidId != null && androidId.length() > 0) {
            prefUtil.putString("uniqueId", androidId);
            method = "android";
            return androidId;
        }
        //第三选择deviceid，缺点:一些手机可能返回null,调用该方法需要权限，无法拨打电话的android 设备无法获取
        String deviceId = ((TelephonyManager) ZXApp.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId != null && deviceId.length() > 0) {
            prefUtil.putString("uniqueId", deviceId);
            method = "device";
            return deviceId;
        }
        //第四，获取Serial序列号，缺点：需要2.3版本以上
        String serialId = android.os.Build.SERIAL;
        if (serialId != null && serialId.length() > 0) {
            prefUtil.putString("uniqueId", serialId);
            method = "serial";
            return serialId;
        }
        //第五，自造uuid，缺点：这种方式只能作为紧急获取，当上面的所有方式都无效的时候使用，一旦使用该方式，应用一旦卸载，将无法登陆
        String uuid = UUID.randomUUID().toString();
        prefUtil.putString("uniqueId", uuid);
        method = "uuid";
        return uuid;
    }

}
