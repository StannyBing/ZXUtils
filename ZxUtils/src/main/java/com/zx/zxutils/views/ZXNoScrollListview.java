package com.zx.zxutils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 不会滚动的listview
 */

public class ZXNoScrollListview extends ListView {
    public ZXNoScrollListview(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public ZXNoScrollListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}