package com.zx.zxutils.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.zx.zxutils.ZXApp;

/**
 * Created by Xiangb on 2017/3/29.
 * 功能：通知栏工具
 */
public class ZXNotifyUtil {

    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder cBuilder;
    private static Notification.Builder nBuilder;
    private static Notification notification;

    /**
     * 打开只有单排内容的notification
     *
     * @param intent  信使
     * @param icon    图标
     * @param title   标题
     * @param content 内容
     */
    public static void showSingleLineNotify(Intent intent, int icon, String title, String content) {
        initManager();
        setCompatBuilder(intent, icon, title, content, true, true, false);
        sendNotify();
    }

    /**
     * 打开多排内容的notification
     *
     * @param intent  信使
     * @param icon    图标
     * @param title   标题
     * @param content 内容
     */
    @SuppressLint("NewApi")
    public static void showMulLineNotify(Intent intent, int icon, String title, String content) {
        final int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            showSingleLineNotify(intent, icon, title, content);
        } else {
            initManager();
            setBuilder(intent, icon, title, true, true, false);
            nBuilder.setContentTitle(title);
            nBuilder.setContentText(content);
            nBuilder.setPriority(Notification.PRIORITY_HIGH);
            notification = new Notification.BigTextStyle(nBuilder).bigText(content).build();
            // 发送该通知
            notificationManager.notify(0, notification);
        }
//        setCompatBuilder(context, intent, icon, title, content);
//        sendNotify();
    }

    /**
     * 打开带大图片的notification
     *
     * @param intent  信使
     * @param icon    图标
     * @param title   标题
     * @param content 内容
     * @param bigImg  大图片
     */
    public static void showBigImgNotify(Intent intent, int icon, String title, String content, Bitmap bigImg) {
        initManager();
        setCompatBuilder(intent, icon, title, content, true, true, false);
        NotificationCompat.BigPictureStyle picStyle = new NotificationCompat.BigPictureStyle();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inSampleSize = 2;
        picStyle.bigPicture(bigImg);
        picStyle.bigLargeIcon(bigImg);
        cBuilder.setContentText(content);
        cBuilder.setStyle(picStyle);
        sendNotify();
    }

    /**
     * 进度条样式的notify
     *
     * @param intent   信使
     * @param icon     图标
     * @param title    标题
     * @param content  内容
     * @param progress 进度
     */
    public static void showProgressNotify(Intent intent, int icon, String title, String content, int progress) {
        initManager();
        setCompatBuilder(intent, icon, title, content, false, false, false);
        cBuilder.setAutoCancel(false);
        // 参数：1.最大进度， 2.当前进度， 3.是否有准确的进度显示
        cBuilder.setProgress(100, progress, false);
        if (progress == 100) {
            // 进度满了后，设置提示信息
            cBuilder.setContentText("加载完成").setProgress(0, 0, false);
        }
        sendNotify();
    }

    /**
     * 自定义view的notify
     *
     * @param intent     信使
     * @param customView 自定义界面
     */
    public static void showCustomNotify(Intent intent, RemoteViews customView) {
        initManager();
        setCompatBuilder(intent, android.R.mipmap.sym_def_app_icon, "", "", true, true, false);
        notification = cBuilder.build();
        notification.contentView = customView;
        // 发送该通知
        notificationManager.notify(0, notification);
    }

    /**
     * 发送通知
     */
    private static void sendNotify() {
        notification = cBuilder.build();
        // 发送该通知
        notificationManager.notify(0, notification);
    }

    /**
     * 清除通知
     */
    public static void clearNotify() {
        // 取消通知
        notificationManager.cancelAll();
    }

    /**
     * 初始化manager
     *
     */
    private static void initManager() {
        // 获取系统服务来初始化对象
        notificationManager = (NotificationManager) ZXApp.getContext()
                .getSystemService(Activity.NOTIFICATION_SERVICE);
        cBuilder = new NotificationCompat.Builder(ZXApp.getContext());
    }

    /**
     * 设置在顶部通知栏中的各种信息
     *
     * @param intent
     * @param icon
     * @param title
     * @param content
     */
    private static void setCompatBuilder(Intent intent, int icon, String title, String content, boolean sound, boolean vibrate, boolean lights) {
//        // 如果当前Activity启动在前台，则不开启新的Activity。
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        // 当设置下面PendingIntent.FLAG_UPDATE_CURRENT这个参数的时候，常常使得点击通知栏没效果，你需要给notification设置一个独一无二的requestCode
//        // 将Intent封装进PendingIntent中，点击通知的消息后，就会启动对应的程序
//        PendingIntent pIntent = PendingIntent.getActivity(mContext,
//                requestCode, intent, FLAG);

        cBuilder.setContentIntent(PendingIntent.getActivity(ZXApp.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        ;// 该通知要启动的Intent
        cBuilder.setSmallIcon(icon);// 设置顶部状态栏的小图标
        cBuilder.setTicker(title);// 在顶部状态栏中的提示信息

        cBuilder.setContentTitle(title);// 设置通知中心的标题
        cBuilder.setContentText(content);// 设置通知中心中的内容
        cBuilder.setWhen(System.currentTimeMillis());

		/*
         * 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失,
		 * 不设置的话点击消息后也不清除，但可以滑动删除
		 */
        cBuilder.setAutoCancel(true);
        // 将Ongoing设为true 那么notification将不能滑动删除
        // notifyBuilder.setOngoing(true);
        /*
         * 从Android4.1开始，可以通过以下方法，设置notification的优先级，
		 * 优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
		 */
        cBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        /*
         * Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认。
		 * Notification.DEFAULT_SOUND：系统默认铃声。
		 * Notification.DEFAULT_VIBRATE：系统默认震动。
		 * Notification.DEFAULT_LIGHTS：系统默认闪光。
		 * notifyBuilder.setDefaults(Notification.DEFAULT_ALL);
		 */
        int defaults = 0;

        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        cBuilder.setDefaults(defaults);
    }


    // 设置builder的信息，在用大文本时会用到这个
    private static void setBuilder(Intent intent, int icon, String title, boolean sound, boolean vibrate, boolean lights) {
        nBuilder = new Notification.Builder(ZXApp.getContext());
        // 如果当前Activity启动在前台，则不开启新的Activity。
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pIntent = PendingIntent.getActivity(mContext,
//                requestCode, intent, FLAG);
        nBuilder.setContentIntent(PendingIntent.getActivity(ZXApp.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        nBuilder.setSmallIcon(icon);

        /*
         * 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失,
		 * 不设置的话点击消息后也不清除，但可以滑动删除
		 */
        nBuilder.setAutoCancel(true);

        nBuilder.setTicker(title);
        nBuilder.setWhen(System.currentTimeMillis());

        int defaults = 0;

        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        nBuilder.setDefaults(defaults);
    }

}
