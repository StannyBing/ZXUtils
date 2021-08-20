package com.zx.zxutils.views.SlidingLayout.util;

import android.content.Context;
import androidx.drawerlayout.widget.DrawerLayout;

import com.zx.zxutils.views.SlidingLayout.ZXSlidingRootNavLayout;


/**
 * Created by yarolegovich on 26.03.2017.
 */

public class ActionBarToggleAdapter extends DrawerLayout {

    private ZXSlidingRootNavLayout adaptee;

    public ActionBarToggleAdapter(Context context) {
        super(context);
    }

    @Override
    public void openDrawer(int gravity) {
        adaptee.openMenu();
    }

    @Override
    public void closeDrawer(int gravity) {
        adaptee.closeMenu();
    }

    @Override
    public boolean isDrawerVisible(int drawerGravity) {
        return !adaptee.isMenuHidden();
    }

    @Override
    public int getDrawerLockMode(int edgeGravity) {
        if (adaptee.isMenuLocked() && adaptee.isMenuHidden()) {
            return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        } else if (adaptee.isMenuLocked() && !adaptee.isMenuHidden()) {
            return DrawerLayout.LOCK_MODE_LOCKED_OPEN;
        } else {
            return DrawerLayout.LOCK_MODE_UNLOCKED;
        }
    }

    public void setAdaptee(ZXSlidingRootNavLayout adaptee) {
        this.adaptee = adaptee;
    }
}
