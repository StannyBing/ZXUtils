package com.zx.zxutils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * description:不会滚动的gridview
 * Created by xsf
 * on 2016.04.15:04
 */
public class ZXNoScrollGridView extends GridView {
    public ZXNoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ZXNoScrollGridView(Context context) {
        super(context);
    }
    public ZXNoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
