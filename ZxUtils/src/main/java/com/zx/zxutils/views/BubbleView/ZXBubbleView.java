package com.zx.zxutils.views.BubbleView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.zx.zxutils.util.ZXScreenUtil;

/**
 * Create By Xiangb On 2017/6/30
 * 功能：气泡弹出框view
 */
public class ZXBubbleView extends PopupWindow {

    private BubbleRelativeLayout bubbleView;
    private Context context;

    public ZXBubbleView(Context context) {
        this.context = context;
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        setOutsideTouchable(false);
        setClippingEnabled(false);

        ColorDrawable dw = new ColorDrawable(0);
        setBackgroundDrawable(dw);
    }

    public ZXBubbleView setBubbleView(int resourceId) {
        View bubbleView = LayoutInflater.from(context).inflate(resourceId, null);
        setBubbleView(bubbleView);
        return this;
    }

    public ZXBubbleView setBubbleView(View view) {
        bubbleView = new BubbleRelativeLayout(context);
        bubbleView.setBackgroundColor(Color.TRANSPARENT);
        bubbleView.addView(view);
        setContentView(bubbleView);
        return this;
    }

    public void setParam(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void show(View parent) {
        show(parent, Gravity.TOP, 0);
    }

    public void show(View parent, int gravity) {
        show(parent, gravity, 0);
    }

    /**
     * 显示弹窗
     *
     * @param parent
     * @param gravity
     * @param bubbleOffset 气泡尖角位置偏移量。默认位于中间
     */
    public void show(View parent, int gravity, float bubbleOffset) {
        float defaultOffSet = 0;
        BubbleRelativeLayout.BubbleLegOrientation orientation = BubbleRelativeLayout.BubbleLegOrientation.LEFT;
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        if (!this.isShowing()) {
            switch (gravity) {
                case Gravity.BOTTOM:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.TOP;
                    if (compareWithScreenWidthTopOrBottom(location[0] - 30) < location[0] - 30) {//说明已经超出屏幕外了，弹出框进行了移动
                        defaultOffSet = parent.getWidth() / 2 + (location[0] - compareWithScreenWidthTopOrBottom(location[0] - 30));
                    } else {//说明弹出框的x坐标就是location[0]-30
                        defaultOffSet = parent.getWidth() / 2 + 30;
                    }
                    break;
                case Gravity.TOP:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.BOTTOM;
                    if (compareWithScreenWidthTopOrBottom(location[0] - 30) < location[0] - 30) {//说明已经超出屏幕外了，弹出框进行了移动
                        defaultOffSet = parent.getWidth() / 2 + (location[0] - compareWithScreenWidthTopOrBottom(location[0] - 30));
                    } else {//说明弹出框的x坐标就是location[0]-30
                        defaultOffSet = parent.getWidth() / 2 + 30;
                    }
                    break;
                case Gravity.RIGHT:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.LEFT;
                    defaultOffSet = parent.getHeight();
                    break;
                case Gravity.LEFT:
                    orientation = BubbleRelativeLayout.BubbleLegOrientation.RIGHT;
                    defaultOffSet = parent.getHeight();
                    break;
                default:
                    break;
            }
            if (bubbleOffset == 0) {
                bubbleView.setBubbleParams(orientation, defaultOffSet); // 设置气泡布局方向及尖角偏移
            } else {
                bubbleView.setBubbleParams(orientation, bubbleOffset); // 设置气泡布局方向及尖角偏移
            }

            switch (gravity) {
                case Gravity.BOTTOM:
                    showAtLocation(parent, Gravity.NO_GRAVITY, compareWithScreenWidthTopOrBottom(location[0] - 30), compareWithScrenHeight(location[1] + parent.getHeight()));
                    break;
                case Gravity.TOP:
                    showAtLocation(parent, Gravity.NO_GRAVITY, compareWithScreenWidthTopOrBottom(location[0] - 30), compareWithScrenHeight(location[1] - getMeasureHeight()));
                    break;
                case Gravity.RIGHT:
                    showAtLocation(parent, Gravity.NO_GRAVITY, compareWithScreenWidthLeftOrRight(location[0] + parent.getWidth(), 1), compareWithScrenHeight(location[1] - (parent.getHeight() / 2)));
                    break;
                case Gravity.LEFT:
                    showAtLocation(parent, Gravity.NO_GRAVITY, compareWithScreenWidthLeftOrRight(location[0] - getMeasuredWidth(), 0), compareWithScrenHeight(location[1] - (parent.getHeight() / 2)));
                    break;
                default:
                    break;
            }
        } else {
            this.dismiss();
        }
    }

    private int compareWithScrenHeight(int locationy) {
        return locationy < 60 ? 60 : locationy;
    }

    /**
     * 用于处理上下的view出现右边超出屏幕的问题
     *
     * @param locationx
     * @return
     */
    private int compareWithScreenWidthTopOrBottom(int locationx) {
        if (locationx + getMeasuredWidth() > ZXScreenUtil.getScreenWidth()) {//超出屏幕外了（右边）
            return ZXScreenUtil.getScreenWidth() - getMeasuredWidth();//此时刚好到屏幕右侧
        } else {
            return locationx < -20 ? -20 : locationx;
        }
    }

    /**
     * 用于处理左右的view出现左右超出屏幕的问题（重新定义popupwindow的宽度）
     *
     * @param locationx
     * @param type
     * @return
     */
    private int compareWithScreenWidthLeftOrRight(int locationx, int type) {
        if (type == 0) {//left
            if (locationx < 0) {//左侧超出屏幕外了
                setWidth(locationx + getMeasuredWidth());
                return 0;
            }
        } else {//right
            if (locationx + getMeasuredWidth() > ZXScreenUtil.getScreenWidth()) {//右侧超出屏幕外了
                setWidth(ZXScreenUtil.getScreenWidth() - locationx);
            }
        }
        return locationx;
    }

    /**
     * 测量高度
     *
     * @return
     */
    private int getMeasureHeight() {
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popHeight = getContentView().getMeasuredHeight();
        return popHeight;
    }

    /**
     * 测量宽度
     *
     * @return
     */
    private int getMeasuredWidth() {
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popWidth = getContentView().getMeasuredWidth();
        return popWidth;
    }
}
