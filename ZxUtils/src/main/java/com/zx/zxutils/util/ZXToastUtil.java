package com.zx.zxutils.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zx.zxutils.ZXApp;

/**
 * Created by Xiangb on 2017/3/27.
 * 功能：专用于Toast及SnackBar的工具类
 */
public class ZXToastUtil {
    private static Snackbar snackbar;
    private static Toast toast;

    /**
     * 默认toast
     *
     * @param message 内容
     */
    public static void showToast(String message) {
        boolean isMain = true;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            isMain = false;
        }
        if (!isMain) {
            Looper.prepare();
        }

        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(ZXApp.getContext(), message, Toast.LENGTH_SHORT);
        toast.show();

        if (!isMain) {
            Looper.loop();
        }
    }

    /**
     * 带图片的toast，传入drawable
     *
     * @param message     内容
     * @param imgDrawable 图片
     */
    public static void showImgToast(String message, Drawable imgDrawable) {
        boolean isMain = true;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            isMain = false;
        }
        if (!isMain) {
            Looper.prepare();
        }

        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(ZXApp.getContext(), message, Toast.LENGTH_SHORT);
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(ZXApp.getContext());
        imageView.setImageDrawable(imgDrawable);
        linearLayout.addView(imageView);
        toast.show();

        if (!isMain) {
            Looper.loop();
        }
    }

    /**
     * 带图片的toast，传入bitmap
     *
     * @param message 内容
     * @param bitmap  图片
     */
    public static void showImgToast(String message, Bitmap bitmap) {
        boolean isMain = true;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            isMain = false;
        }
        if (!isMain) {
            Looper.prepare();
        }

        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(ZXApp.getContext(), message, Toast.LENGTH_SHORT);
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(ZXApp.getContext());
        imageView.setImageBitmap(bitmap);
        linearLayout.addView(imageView);
        toast.show();

        if (!isMain) {
            Looper.loop();
        }
    }

    /**
     * 显示snackbar，不带action
     *
     * @param view    任意当前layout的view
     * @param message 内容
     */
    public static void showSnackBar(View view, String message) {
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    /**
     * 显示snackbar，带action
     *
     * @param view           任意当前layout的view
     * @param message        内容
     * @param actionText     action问题
     * @param actionListener action监听器
     */
    public static void showSnackBar(View view, String message, String actionText, View.OnClickListener actionListener) {
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (actionListener == null) {
            actionListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            };
        }
        snackbar.setAction(actionText, actionListener);
        snackbar.show();
    }

}
