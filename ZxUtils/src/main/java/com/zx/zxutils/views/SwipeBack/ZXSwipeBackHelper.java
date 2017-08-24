package com.zx.zxutils.views.SwipeBack;

import android.app.Activity;

import java.util.Stack;

/**
 * 滑动的全局管理类
 */
public class ZXSwipeBackHelper {

    private static final Stack<SwipeBackPage> mPageStack = new Stack<>();

    private static SwipeBackPage findHelperByActivity(Activity activity) {
        for (SwipeBackPage swipeBackPage : mPageStack) {
            if (swipeBackPage.mActivity == activity) return swipeBackPage;
        }
        return null;
    }

    public static SwipeBackPage getCurrentPage(Activity activity) {
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null) {
            throw new RuntimeException("You Should call ZXSwipeBackHelper.onCreate(activity) first");
        }
        return page;
    }

    public static SwipeBackPage onCreate(Activity activity) {
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null) {
            page = mPageStack.push(new SwipeBackPage(activity));
        }
        page.onCreate();
        onPostCreate(activity);
        getCurrentPage(activity).setSwipeEdgePercent(0.2f);
        return getCurrentPage(activity);
    }

    public static void onPostCreate(Activity activity) {
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null) {
            throw new RuntimeException("You Should call ZXSwipeBackHelper.onCreate(activity) first");
        }
        page.onPostCreate();
    }

    public static void onDestroy(Activity activity) {
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null) {
            throw new RuntimeException("You Should call ZXSwipeBackHelper.onCreate(activity) first");
        }
        mPageStack.remove(page);
        page.mActivity = null;
    }

    public static void finish(Activity activity) {
        SwipeBackPage page;
        if ((page = findHelperByActivity(activity)) == null) {
            throw new RuntimeException("You Should call ZXSwipeBackHelper.onCreate(activity) first");
        }
        page.scrollToFinishActivity();
    }

    static SwipeBackPage getPrePage(SwipeBackPage activity) {
        int index = mPageStack.indexOf(activity);
        if (index > 0) return mPageStack.get(index - 1);
        else return null;
    }

}
