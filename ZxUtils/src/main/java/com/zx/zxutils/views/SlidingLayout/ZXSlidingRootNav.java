package com.zx.zxutils.views.SlidingLayout;

/**
 * Created by yarolegovich on 25.03.2017.
 */

public interface ZXSlidingRootNav {

    boolean isMenuHidden();

    boolean isMenuLocked();

    void closeMenu();

    void closeMenu(boolean animated);

    void openMenu();

    void openMenu(boolean animated);

    void setMenuLocked(boolean locked);

    ZXSlidingRootNavLayout getLayout();

}
