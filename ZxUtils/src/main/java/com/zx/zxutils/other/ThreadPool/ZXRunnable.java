package com.zx.zxutils.other.ThreadPool;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Xiangb on 2017/4/14.
 * 功能：
 */
public abstract class ZXRunnable implements Runnable, Comparable<ZXRunnable> {
    private int priorityNum;


    @IntDef({IMPORTANT, NORMAL, FREE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MultiPicAction {
    }

    public static final int IMPORTANT = 3;//重要的
    public static final int NORMAL = 2;//平常的
    public static final int FREE = 1;//自由的，空闲的

    public ZXRunnable(@MultiPicAction int priority) {
        priorityNum = priority;
    }

    public ZXRunnable() {
        priorityNum = FREE;
    }

    @Override
    public int compareTo(ZXRunnable another) {
        int my = this.getPriority();
        int other = another.getPriority();
        return my < other ? 1 : my > other ? -1 : 0;
    }

    @Override
    public void run() {
        IRun();
    }

    public abstract void IRun();

    public int getPriority() {
        return priorityNum;
    }
}
