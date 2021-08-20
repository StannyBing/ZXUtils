package com.zx.zxutils.views.SlidingLayout.util;

import androidx.drawerlayout.widget.DrawerLayout;
import android.view.View;

import com.zx.zxutils.views.SlidingLayout.callback.DragListener;
import com.zx.zxutils.views.SlidingLayout.callback.DragStateListener;


/**
 * Created by yarolegovich on 26.03.2017.
 */

public class DrawerListenerAdapter implements DragListener, DragStateListener {

    private DrawerLayout.DrawerListener adaptee;
    private View drawer;
    private View spaceView;

    public DrawerListenerAdapter(DrawerLayout.DrawerListener adaptee, View drawer, View spaceView) {
        this.adaptee = adaptee;
        this.drawer = drawer;
        this.spaceView = spaceView;
    }

    @Override
    public void onDrag(float progress) {
        if (adaptee != null) adaptee.onDrawerSlide(drawer, progress);
    }

    @Override
    public void onDragStart() {
        if (adaptee != null) adaptee.onDrawerStateChanged(DrawerLayout.STATE_DRAGGING);
    }

    @Override
    public void onDragEnd(boolean isMenuOpened) {
        if (isMenuOpened) {
            if (adaptee != null) adaptee.onDrawerOpened(drawer);
            spaceView.setVisibility(View.VISIBLE);
        } else {
            if (adaptee != null) adaptee.onDrawerClosed(drawer);
            spaceView.setVisibility(View.GONE);
        }
        if (adaptee != null) adaptee.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
    }
}
