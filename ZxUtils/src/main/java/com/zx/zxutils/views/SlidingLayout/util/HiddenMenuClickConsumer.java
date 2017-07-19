package com.zx.zxutils.views.SlidingLayout.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.zx.zxutils.views.SlidingLayout.ZXSlidingRootNavLayout;


/**
 * Created by yarolegovich on 26.03.2017.
 */

public class HiddenMenuClickConsumer extends View {

    private ZXSlidingRootNavLayout menuHost;

    public HiddenMenuClickConsumer(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return menuHost.isMenuHidden();
    }

    public void setMenuHost(ZXSlidingRootNavLayout layout) {
        this.menuHost = layout;
    }
}
