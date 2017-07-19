package com.zx.zxutils.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.zx.zxutils.R;

/**
 * Created by Xiangb on 2017/4/10.
 * 功能：带悬浮布局的View
 */
public class ZXFloatScrollView extends LinearLayout {
    private Context context;
    private LinearLayout llTop, llBottom, floatOut, floatIn;
    private ScrollView floatScroll;
    private View floatview;

    public ZXFloatScrollView(Context context) {
        super(context, null);
    }

    public ZXFloatScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_float_scrollview_layout, this, true);
        this.context = context;
        floatScroll = (ScrollView) findViewById(R.id.float_scroll);//自定义的ScrollView
        llTop = (LinearLayout) findViewById(R.id.ll_top);//浮动view上部分
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);//浮动view下部分
        floatIn = (LinearLayout) findViewById(R.id.float_in);//浮动部分内
        floatOut = (LinearLayout) findViewById(R.id.float_out);//浮动部分外
        floatScroll.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (i1 > llTop.getBottom()) {
                    if (floatIn.getChildCount() > 0) {
                        floatIn.removeAllViews();
                        floatOut.addView(floatview);
                    }
                } else {
                    if (floatOut.getChildCount() > 0) {
                        floatOut.removeAllViews();
                        floatIn.addView(floatview);
                    }
                }
            }
        });
    }

    /**
     * 设置顶部布局
     *
     * @param topView
     * @return
     */
    public ZXFloatScrollView setTopView(View topView) {
        llTop.addView(topView);
        return this;
    }

    /**
     * 设置底部布局
     *
     * @param bottomView
     * @return
     */
    public ZXFloatScrollView setBottomView(View bottomView) {
        llBottom.addView(bottomView);
        return this;
    }

    /**
     * 设置浮动布局
     *
     * @param floatView
     * @return
     */
    public ZXFloatScrollView setFloatview(View floatView) {
        this.floatview = floatView;
        floatIn.addView(floatview);
        return this;
    }

    /**
     * 获取scrollview
     *
     * @return
     */
    public ScrollView getScrollView() {
        return floatScroll;
    }

    /**
     * 获取顶部布局
     *
     * @return
     */
    public View getTopView() {
        return llTop.getChildCount() > 0 ? llTop.getChildAt(0) : null;
    }

    //获取底部布局
    public View getBottomView() {
        return llBottom.getChildCount() > 0 ? llBottom.getChildAt(0) : null;
    }

    /**
     * 获取浮动布局
     *
     * @return
     */
    public View getFloatView() {
        return floatview;
    }
}
