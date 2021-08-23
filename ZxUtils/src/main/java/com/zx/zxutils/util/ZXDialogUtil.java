package com.zx.zxutils.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zx.zxutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/3/24.
 * 功能：专用于dialog的工具类
 */
public class ZXDialogUtil {

    private static Handler handler = new Handler();
    //    private static ProgressDialog loadingDialog;//简单进度dialog，适用于单纯显示正在加载的情况
    //    private static Dialog dialog;//用于显示各种信息的dialog
    private static List<Dialog> dialogList = new ArrayList<>();
    private static AlertDialog loadingDialog;
    private static Context loadingContext;

    public static AlertDialog showLoadingDialog(Context context, String message) {
        return showLoadingDialog(context, message, -1);
    }

    public static AlertDialog showLoadingDialog(Context context, String message, int progress) {
        try {
            if (loadingContext != null && loadingContext.getClass().equals(context.getClass())) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    sendMessage(message, progress);
                } else {
                    showNewLoading(context, message, progress);
                }
            } else {
                showNewLoading(context, message, progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showNewLoading(context, message, progress);
        }
        return loadingDialog;
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private static void showNewLoading(Context context, String message, int progress) {
        if (!isMainThread()) {
            Looper.loop();
        }
        loadingContext = context;
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_view, null);
        LinearLayout llSimple = view.findViewById(R.id.ll_loading_simple);
        LinearLayout llProgress = view.findViewById(R.id.ll_loading_withprogress);
        TextView tvMsg = view.findViewById(R.id.tv_loading_text);
        TextView tvProgressMsg = view.findViewById(R.id.tv_loading_progresstext);
        TextView tvProgress = view.findViewById(R.id.tv_loading_progress);
        ProgressBar pbLoading = view.findViewById(R.id.pb_loading_dialog);
        pbLoading.setProgress(progress);
        tvMsg.setText(message);
        tvProgressMsg.setText(message);
        tvProgress.setText(progress + "%");
        if (progress == -1) {
            llSimple.setVisibility(View.VISIBLE);
            llProgress.setVisibility(View.GONE);
        } else {
            llSimple.setVisibility(View.GONE);
            llProgress.setVisibility(View.VISIBLE);
        }
        loadingDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
        if (!isMainThread()) {
            Looper.prepare();
        }
    }

    private static void sendMessage(String message, int progress) {
        Message msg = new Message();
        if (progress == -1) {
            msg.what = 0;
        } else {
            msg.what = 1;
        }
        msg.arg1 = progress;
        msg.obj = message;
        loadingHandle.sendMessage(msg);
    }


    private static Handler loadingHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LinearLayout llSimple = loadingDialog.findViewById(R.id.ll_loading_simple);
            LinearLayout llProgress = loadingDialog.findViewById(R.id.ll_loading_withprogress);
            TextView tvMsg = loadingDialog.findViewById(R.id.tv_loading_text);
            TextView tvProgressMsg = loadingDialog.findViewById(R.id.tv_loading_progresstext);
            TextView tvProgress = loadingDialog.findViewById(R.id.tv_loading_progress);
            ProgressBar pbLoading = loadingDialog.findViewById(R.id.pb_loading_dialog);
            if (msg.what == 0) {
                llProgress.setVisibility(View.GONE);
                llSimple.setVisibility(View.VISIBLE);
            } else {
                llProgress.setVisibility(View.VISIBLE);
                llSimple.setVisibility(View.GONE);
            }
            tvMsg.setText(msg.obj.toString());
            tvProgressMsg.setText(msg.obj.toString());
            tvProgress.setText(msg.arg1 + "%");
            pbLoading.setProgress(msg.arg1);
            super.handleMessage(msg);
        }
    };

//    /**
//     * 显示简单加载dialog
//     *
//     * @param context 上下文
//     * @param message 提示消息
//     */
//    public static AlertDialog showLoadingDialog(Context context, String message) {
//        try {
//            if (loadingDialog == null) {
//                showSimple(context, message);
//            } else {
//                //TODO
//                loadingDialog.setMessage(message);
//                loadingDialog.show();
//            }
//        } catch (Exception e) {
//            showSimple(context, message);
//        }
//        return loadingDialog;
//    }
//
//    private static void showSimple(Context context, String message) {
//        loadingDialog = null;
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_view, null);
//        TextView tvMsg = view.findViewById(R.id.tv_loading_text);
//        tvMsg.setText(message);
//        loadingDialog = new AlertDialog.Builder(context)
//                .setView(view)
//                .create();
////        loadingDialog = ProgressDialog.show(context, "", message);
////        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        loadingDialog.setCancelable(true);
//        loadingDialog.setCanceledOnTouchOutside(false);
//        loadingDialog.show();
//    }

//    /**
//     * 显示带进度条的加载dialog
//     *
//     * @param context  上下文
//     * @param message  提示消息
//     * @param progress 进度
//     */
//    public static AlertDialog showLoadingDialog(Context context, String message, int progress) {
//        try {
//            if (loadingDialog == null) {
//                showProgress(context, message, progress);
//            } else {
//                loadingDialog.setMessage(message);
////                loadingDialog.setProgress(progress);
//                loadingDialog.show();
//            }
//        } catch (Exception e) {
//            showProgress(context, message, progress);
//        }
//        return loadingDialog;
//    }

//    private static void showProgress(Context context, String message, int progress) {
//        loadingDialog = null;
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_progress_view, null);
//        TextView tvMsg = view.findViewById(R.id.tv_loading_text);
//        TextView tvProgress = view.findViewById(R.id.tv_loading_progress);
//        tvProgress.setText(progress + "%");
//        tvMsg.setText(message);
//        loadingDialog = new AlertDialog.Builder(context)
//                .setView(view)
//                .create();
//        loadingDialog = new ProgressDialog(context);
//        loadingDialog.setMessage(message);
//        loadingDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        loadingDialog.setCancelable(true);
//        loadingDialog.setCanceledOnTouchOutside(false);
//        loadingDialog.setProgress(progress);
//        loadingDialog.show();
//    }

    /**
     * 判断加载dialog是否为打开状态
     *
     * @return
     */
    public static boolean isLoadingDialogShow() {
        if (loadingDialog != null) {
            return loadingDialog.isShowing();
        } else {
            return false;
        }
    }

    /**
     * 关闭加载dialog
     */
    public static void dismissLoadingDialog() {
        if (handler != null) {
            handler.post(new Runnable() {
                public void run() {
                    if (null != loadingDialog && loadingDialog.isShowing()) {
                        try {
                            loadingDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public static Dialog showInfoDialog(Context context, String title, String message) {
        return showInfoDialog(context, title, message, null, false);
    }

    public static Dialog showInfoDialog(Context context, String title, String message, boolean canceledOnTouchOutSide) {
        return showInfoDialog(context, title, message, null, canceledOnTouchOutSide);
    }

    public static Dialog showInfoDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener listener) {
        return showInfoDialog(context, title, message, listener, false);
    }

    /**
     * 用于展示单纯信息的dialog
     *
     * @param context  上下文
     * @param title    标题
     * @param message  内容
     * @param listener 确定监听
     */
    public static Dialog showInfoDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener listener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) buider.setTitle(title);
        buider.setMessage(message);
        buider.setPositiveButton("确定", listener);
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置宽度
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        p.gravity = Gravity.CENTER;//设置位置
        //p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 有两个按钮，用于判断的dialog，可定义按钮文字及监听事件
     *
     * @param context     上下文
     * @param title       标题
     * @param message     内容
     * @param yesBtnText  确定按钮内容
     * @param noBtnText   取消按钮内容
     * @param yesListener 确定按钮监听器
     * @param noListener  取消按钮监听器
     */
    public static Dialog showYesNoDialog(Context context, String title, String message, String yesBtnText, String noBtnText, @Nullable DialogInterface.OnClickListener yesListener, @Nullable DialogInterface.OnClickListener noListener) {
        return showYesNoDialog(context, title, message, yesBtnText, noBtnText, yesListener, noListener, false);
    }

    public static Dialog showYesNoDialog(Context context, String title, String message, String yesBtnText, String noBtnText, @Nullable DialogInterface.OnClickListener yesListener, @Nullable DialogInterface.OnClickListener noListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) buider.setTitle(title);
        buider.setMessage(message);
        buider.setPositiveButton(yesBtnText, yesListener);
        buider.setNegativeButton(noBtnText, noListener);
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置宽度
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        p.gravity = Gravity.CENTER;//设置位置
        //p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 有两个按钮，用于判断的dialog，只能定义确定按钮监听事件
     *
     * @param context     上下文
     * @param title       标题
     * @param message     内容
     * @param yesListener 确定按钮点击事件
     */
    public static Dialog showYesNoDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener yesListener) {
        return showYesNoDialog(context, title, message, "确定", "取消", yesListener, null, false);
    }

    public static Dialog showYesNoDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        return showYesNoDialog(context, title, message, "确定", "取消", yesListener, null, canceledOnTouchOutSide);
    }

    /**
     * 带一个中性按钮，不提供确认和取消按钮的文字修改及取消按钮的点击事件（因为太多参数呢），如需要自行创建或采用showCustomDialog
     *
     * @param context          上下文
     * @param title            标题
     * @param message          内容
     * @param otherBtnText     中性按钮文字
     * @param yesListener      确定按钮点击事件
     * @param otherBtnListener 中性按钮点击事件
     */
    public static Dialog showWithOtherBtnDialog(Context context, String title, String message, String otherBtnText, @Nullable DialogInterface.OnClickListener yesListener, @Nullable DialogInterface.OnClickListener otherBtnListener) {
        return showWithOtherBtnDialog(context, title, message, otherBtnText, yesListener, otherBtnListener, false);
    }

    public static Dialog showWithOtherBtnDialog(Context context, String title, String message, String otherBtnText, @Nullable DialogInterface.OnClickListener yesListener, @Nullable DialogInterface.OnClickListener otherBtnListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) buider.setTitle(title);
        buider.setMessage(message);
        buider.setPositiveButton("确定", yesListener);
        buider.setNegativeButton("取消", null);
        buider.setNeutralButton(otherBtnText, otherBtnListener);
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置宽度
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        p.gravity = Gravity.CENTER;//设置位置
        //p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 带item勾选列表的dialog，不提供按钮文字及取消按钮监听事件的修改
     *
     * @param context             上下文
     * @param title               标题
     * @param itemName            checkitem的名字
     * @param itemCheckStatus     checkitem的选中状态
     * @param choiceClickListener 选择事件
     * @param yesListener         确认的点击事件
     */
    public static Dialog showCheckListDialog(Context context, String title, String[] itemName, boolean[] itemCheckStatus, @Nullable DialogInterface.OnMultiChoiceClickListener choiceClickListener, @Nullable DialogInterface.OnClickListener yesListener) {
        return showCheckListDialog(context, title, itemName, itemCheckStatus, choiceClickListener, yesListener, false);
    }

    public static Dialog showCheckListDialog(Context context, String title, String[] itemName, boolean[] itemCheckStatus, @Nullable DialogInterface.OnMultiChoiceClickListener choiceClickListener, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) buider.setTitle(title);
        buider.setMultiChoiceItems(itemName, itemCheckStatus, choiceClickListener);
        buider.setPositiveButton("确定", yesListener);
        buider.setNegativeButton("取消", null);
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置宽度
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        p.gravity = Gravity.CENTER;//设置位置
        //p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 带list的勾选事件，不提供确认取消按钮的文字
     *
     * @param context           上下文
     * @param title             标题
     * @param itemName          item项
     * @param itemClickListener item点击事件
     * @param yesListener       确定按钮点击事件
     */
    public static Dialog showListDialog(Context context, String title, String yesBtnText, String[] itemName, @Nullable DialogInterface.OnClickListener itemClickListener, @Nullable DialogInterface.OnClickListener yesListener) {
        return showListDialog(context, title, yesBtnText, itemName, itemClickListener, yesListener, false);
    }

    public static Dialog showListDialog(final Context context, String title, String yesBtnText, final String[] itemName, @Nullable final DialogInterface.OnClickListener itemClickListener, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) buider.setTitle(title);
        buider.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return itemName.length;
            }

            @Override
            public Object getItem(int i) {
                return itemName[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View v = LayoutInflater.from(context).inflate(R.layout.item_listdialog, null);
                ((TextView) v.findViewById(R.id.tv_text)).setText(itemName[i]);
                return v;
            }
        }, itemClickListener);
//        buider.setItems(itemName, itemClickListener);
        if (yesListener != null) {
            buider.setPositiveButton(yesBtnText, yesListener);
//            buider.setNegativeButton("取消", null);
        } else {
            buider.setPositiveButton(yesBtnText, null);
        }
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置宽度
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        p.gravity = Gravity.CENTER;//设置位置
        //p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 带list的勾选事件，不提供确认取消按钮的文字
     *
     * @param context           上下文
     * @param title             标题
     * @param itemName          item项
     * @param itemClickListener item点击事件
     * @param yesListener       确定按钮点击事件
     */
    public static Dialog showListDialog(Context context, String title, String yesBtnText, List<String> itemName, @Nullable DialogInterface.OnClickListener itemClickListener, @Nullable DialogInterface.OnClickListener yesListener) {
        return showListDialog(context, title, yesBtnText, itemName.toArray(new String[0]), itemClickListener, yesListener);
    }

    public static Dialog showListDialog(Context context, String title, String yesBtnText, List<String> itemName, @Nullable DialogInterface.OnClickListener itemClickListener, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        return showListDialog(context, title, yesBtnText, itemName.toArray(new String[0]), itemClickListener, yesListener, canceledOnTouchOutSide);
    }

    /**
     * 带list的勾选事件，不提供按钮
     *
     * @param context           上下文
     * @param title             标题
     * @param itemName          item项
     * @param itemClickListener item点击事件
     */
    public static Dialog showListDialog(Context context, String title, String yesBtnText, String[] itemName, @Nullable DialogInterface.OnClickListener itemClickListener) {
        return showListDialog(context, title, yesBtnText, itemName, itemClickListener, null);
    }

    public static Dialog showListDialog(Context context, String title, String yesBtnText, String[] itemName, @Nullable DialogInterface.OnClickListener itemClickListener, boolean canceledOnTouchOutSide) {
        return showListDialog(context, title, yesBtnText, itemName, itemClickListener, null, canceledOnTouchOutSide);
    }

    /**
     * 带list的勾选事件，不提供按钮
     *
     * @param context           上下文
     * @param title             标题
     * @param itemName          item项
     * @param itemClickListener item点击事件
     */
    public static Dialog showListDialog(Context context, String title, String yesBtnText, List<String> itemName, @Nullable DialogInterface.OnClickListener itemClickListener) {
        return showListDialog(context, title, yesBtnText, itemName.toArray(new String[0]), itemClickListener);
    }

    public static Dialog showListDialog(Context context, String title, String yesBtnText, List<String> itemName, @Nullable DialogInterface.OnClickListener itemClickListener, boolean canceledOnTouchOutSide) {
        return showListDialog(context, title, yesBtnText, itemName.toArray(new String[0]), itemClickListener, canceledOnTouchOutSide);
    }

    /**
     * 带一个view的dialog，view内部事件处理需要在添加前进行设置
     *
     * @param context     上下文
     * @param title       标题
     * @param view        填入的view
     * @param yesListener 确定按钮的点击事件
     */
    public static Dialog showCustomViewDialog(Context context, String title, View view, @Nullable DialogInterface.OnClickListener yesListener) {
        return showCustomViewDialog(context, title, view, yesListener, null, false);
    }

    public static Dialog showCustomViewDialog(Context context, String title, View view, @Nullable DialogInterface.OnClickListener yesListener, boolean canceledOnTouchOutSide) {
        return showCustomViewDialog(context, title, view, yesListener, null, canceledOnTouchOutSide);
    }

    /**
     * 带一个view的dialog，view内部事件处理需要在添加前进行设置
     *
     * @param context     上下文
     * @param title       标题
     * @param view        填入的view
     * @param yesListener 确定按钮的点击事件
     */
    public static Dialog showCustomViewDialog(Context context, String title, View view, @Nullable DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener) {
        return showCustomViewDialog(context, title, view, yesListener, noListener, false);
    }

    @SuppressLint("RestrictedApi")
    public static Dialog showCustomViewDialog(Context context, String title, View view, @Nullable DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener, boolean canceledOnTouchOutSide) {
        AlertDialog.Builder buider = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) buider.setTitle(title);
        buider.setView(view, 20, 10, 20, 10);
        if (yesListener != null) {
            buider.setPositiveButton("确定", yesListener);
        }
        if (noListener != null) {
            buider.setNegativeButton("取消", noListener);
        }
        Dialog dialog = buider.show();
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutSide);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置宽度
        p.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        p.gravity = Gravity.CENTER;//设置位置
        //p.alpha = 0.8f;//设置透明度
        dialogWindow.setAttributes(p);
        dialogList.add(dialog);
        return dialog;
    }

    /**
     * 关闭dialog
     */
    public static void dismissDialog() {
        try {
            if (dialogList.size() > 0) {
                dialogList.get(dialogList.size() - 1).dismiss();
                dialogList.remove(dialogList.size() - 1);
            }
        } catch (Exception e) {
            dialogList.clear();
            e.printStackTrace();
        }
//        if (dialog != null) {
//            dialog.dismiss();
//        }
    }

}
