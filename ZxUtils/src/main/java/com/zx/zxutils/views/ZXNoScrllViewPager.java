package com.zx.zxutils.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Xiangb on 2018/5/14.
 * 功能：
 */

    public class ZXNoScrllViewPager extends ViewPager {

        private boolean isCanScroll = true;

        public ZXNoScrllViewPager(Context context) {
            super(context);
        }

        public ZXNoScrllViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        /**
         * 设置其是否能滑动换页
         * @param isCanScroll false 不能换页， true 可以滑动换页
         */
        public void setScanScroll(boolean isCanScroll) {
            this.isCanScroll = isCanScroll;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            return isCanScroll && super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            return isCanScroll && super.onTouchEvent(ev);

        }
}
