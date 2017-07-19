package com.zx.zxutils.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.zx.zxutils.ZXApp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xiangb on 2017/5/11.
 * 功能：封装广播broadcastreceiver
 */

public class ZXBroadCastManager {

    private static ZXBroadCastManager instance;
    private Map<String, BroadcastReceiver> receiverMap;
    private static Intent intent = new Intent();
    public static final String DefaultAction = "com.zx.default_action";

    /**
     * 构造方法
     *
     */
    private ZXBroadCastManager() {
        receiverMap = new HashMap<>();
    }


    /**
     * [获取BroadcastManager实例，单例模式实现]
     *
     * @param context
     * @return
     */
    public static ZXBroadCastManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ZXBroadCastManager.class) {
                if (instance == null) {
                    instance = new ZXBroadCastManager();
                }
            }
        }
        intent = new Intent();
        intent.setAction(DefaultAction);
        return instance;
    }


    /**
     * 添加Action,做广播的初始化
     *
     * @param
     */
    public void getAction(String action, BroadcastReceiver receiver) {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            ZXApp.getContext().registerReceiver(receiver, filter);
            receiverMap.put(action, receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加Action,做广播的初始化
     *
     * @param
     */
    public void getAction(String[] actions, BroadcastReceiver receiver) {
        try {
            IntentFilter filter = new IntentFilter();
            for (String action : actions) {
                filter.addAction(action);
            }
            ZXApp.getContext().registerReceiver(receiver, filter);
            for (String action : actions) {
                receiverMap.put(action, receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置唯一码
     *
     * @param action
     * @return
     */
    public ZXBroadCastManager setAction(String action) {
        intent.setAction(action);
        return instance;
    }

    /**
     * 添加字符串参数
     *
     * @param name
     * @param info
     * @return
     */
    public ZXBroadCastManager addExtra(String name, String info) {
        intent.putExtra(name, info);
        return instance;
    }

    /**
     * 添加数字参数
     *
     * @param name
     * @param info
     * @return
     */
    public ZXBroadCastManager addExtra(String name, int info) {
        intent.putExtra(name, info);
        return instance;
    }

    /**
     * 添加double参数
     *
     * @param name
     * @param info
     * @return
     */
    public ZXBroadCastManager addExtra(String name, double info) {
        intent.putExtra(name, info);
        return instance;
    }

    /**
     * 添加float参数
     *
     * @param name
     * @param info
     * @return
     */
    public ZXBroadCastManager addExtra(String name, float info) {
        intent.putExtra(name, info);
        return instance;
    }

    /**
     * 添加实体类参数
     *
     * @param name
     * @param seriaEntity
     * @return
     */
    public ZXBroadCastManager addExtra(String name, Object seriaEntity) {
        intent.putExtra(name, ((Serializable) seriaEntity));
        return instance;
    }

    /**
     * 添加boolean参数
     *
     * @param name
     * @param info
     * @return
     */
    public ZXBroadCastManager addExtra(String name, boolean info) {
        intent.putExtra(name, info);
        return instance;
    }

    /**
     * 添加bundle参数
     *
     * @param name
     * @param bundle
     * @return
     */
    public ZXBroadCastManager addExtra(String name, Bundle bundle) {
        intent.putExtra(name, bundle);
        return instance;
    }

    /**
     * 发送
     */
    public void send() {
        try {
            ZXApp.getContext().sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁广播
     *
     * @param actions
     */
    public void destroy(String... actions) {
        try {
            if (receiverMap != null) {
                for (String action : actions) {
                    BroadcastReceiver receiver = receiverMap.get(action);
                    if (receiver != null) {
                        ZXApp.getContext().unregisterReceiver(receiver);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.d("Broadcastmanager", e.toString());
        }
    }

}
