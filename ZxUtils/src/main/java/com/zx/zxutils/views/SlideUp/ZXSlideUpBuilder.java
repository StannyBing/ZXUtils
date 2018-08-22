package com.zx.zxutils.views.SlideUp;

import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.zx.zxutils.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Default constructor for {@link ZXSlideUp}</p>
 */
public final class ZXSlideUpBuilder {
    private boolean mStateRestored = false;

    View mSliderView;
    float mDensity;
    float mTouchableArea;
    float mStayArea;
    boolean mIsRTL;
    ZXSlideUp.State mStartState = ZXSlideUp.State.HIDDEN;
    List<ZXSlideUp.Listener> mListeners = new ArrayList<>();
    boolean mDebug = false;
    int mAutoSlideDuration = 300;
    int mStartGravity = Gravity.BOTTOM;
    boolean mGesturesEnabled = true;
    boolean mHideKeyboard = false;
    TimeInterpolator mInterpolator = new DecelerateInterpolator();
    View mAlsoScrollView;

    /**
     * <p>Construct a ZXSlideUp by passing the view or his child to use for the generation</p>
     */
    public ZXSlideUpBuilder(View sliderView) {
        Internal.checkNonNull(sliderView, "View can't be null");
        mSliderView = sliderView;
        mDensity = sliderView.getResources().getDisplayMetrics().density;
        mIsRTL = sliderView.getResources().getBoolean(R.bool.is_right_to_left);
    }

    /**
     * <p>Define a start state on screen</p>
     *
     * @param startState <b>(default - <b color="#EF6C00">{@link ZXSlideUp.State#HIDDEN}</b>)</b>
     */
    public ZXSlideUpBuilder withStartState(@NonNull ZXSlideUp.State startState) {
        if (!mStateRestored) {
            mStartState = startState;
        }
        return this;
    }

    /**
     * <p>Define a start gravity, <b>this parameter affects the motion vector slider</b></p>
     *
     * @param gravity <b>(default - <b color="#EF6C00">{@link Gravity#BOTTOM}</b>)</b>
     */
    public ZXSlideUpBuilder withStartGravity(@ZXSlideUp.StartVector int gravity) {
        if (!mStateRestored) {
            mStartGravity = gravity;
        }
        return this;
    }

    /**
     * <p>Define a {@link ZXSlideUp.Listener} for this ZXSlideUp</p>
     *
     * @param listeners {@link List} of listeners
     */
    public ZXSlideUpBuilder withListeners(@NonNull List<ZXSlideUp.Listener> listeners) {
        if (listeners != null) {
            mListeners.addAll(listeners);
        }
        return this;
    }

    /**
     * <p>Define a {@link ZXSlideUp.Listener} for this ZXSlideUp</p>
     *
     * @param listeners array of listeners
     */
    public ZXSlideUpBuilder withListeners(@NonNull ZXSlideUp.Listener... listeners) {
        List<ZXSlideUp.Listener> listeners_list = new ArrayList<>();
        Collections.addAll(listeners_list, listeners);
        return withListeners(listeners_list);
    }

    /**
     * <p>Turning on/off debug logging for all handled events</p>
     *
     * @param enabled <b>(default - <b color="#EF6C00">false</b>)</b>
     */
    public ZXSlideUpBuilder withLoggingEnabled(boolean enabled) {
        if (!mStateRestored) {
            mDebug = enabled;
        }
        return this;
    }

    /**
     * <p>Define duration of animation (whenever you use {@link ZXSlideUp#hide()} or {@link ZXSlideUp#show()} methods)</p>
     *
     * @param duration <b>(default - <b color="#EF6C00">300</b>)</b>
     */
    public ZXSlideUpBuilder withAutoSlideDuration(int duration) {
        if (!mStateRestored) {
            mAutoSlideDuration = duration;
        }
        return this;
    }

    /**
     * <p>Define touchable area <b>(in px)</b> for interaction</p>
     *
     * @param area <b>(default - <b color="#EF6C00">300dp</b>)</b>
     */
    public ZXSlideUpBuilder withTouchableAreaPx(float area) {
        if (!mStateRestored) {
            mTouchableArea = area;
        }
        return this;
    }

    /**
     * <p>Define touchable area <b>(in dp)</b> for interaction</p>
     *
     * @param area <b>(default - <b color="#EF6C00">300dp</b>)</b>
     */
    public ZXSlideUpBuilder withTouchableAreaDp(float area) {
        if (!mStateRestored) {
            mTouchableArea = area * mDensity;
        }
        return this;
    }

//    public ZXSlideUpBuilder withStayViewPx(float stayPx) {
//        if (!mStateRestored) {
//            mStayArea = stayPx;
//        }
//        return this;
//    }
//
//    public ZXSlideUpBuilder withStayViewDp(float stayDp) {
//        if (!mStateRestored) {
//            mStayArea = stayDp * mDensity;
//        }
//        return this;
//    }

    /**
     * <p>Turning on/off sliding on touch event</p>
     *
     * @param enabled <b>(default - <b color="#EF6C00">true</b>)</b>
     */
    public ZXSlideUpBuilder withGesturesEnabled(boolean enabled) {
        mGesturesEnabled = enabled;
        return this;
    }

    /**
     * <p>Define behavior of soft input</p>
     *
     * @param hide <b>(default - <b color="#EF6C00">false</b>)</b>
     */
    public ZXSlideUpBuilder withHideSoftInputWhenDisplayed(boolean hide) {
        if (!mStateRestored) {
            mHideKeyboard = hide;
        }
        return this;
    }

    /**
     * <p>Define interpolator for animation (whenever you use {@link ZXSlideUp#hide()} or {@link ZXSlideUp#show()} methods)</p>
     *
     * @param interpolator <b>(default - <b color="#EF6C00">Decelerate interpolator</b>)</b>
     */
    public ZXSlideUpBuilder withInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
        return this;
    }

    /**
     * @param savedState parameters will be restored from this bundle, if it contains them
     */
    public ZXSlideUpBuilder withSavedState(@Nullable Bundle savedState) {
        restoreParams(savedState);
        return this;
    }


    /**
     * <p>Provide a {@link View} that will also trigger slide events on the {@link ZXSlideUp}.</p>
     *
     * @param alsoScrollView the other view that will trigger the slide events
     */
    public ZXSlideUpBuilder withSlideFromOtherView(@Nullable View alsoScrollView) {
        mAlsoScrollView = alsoScrollView;
        return this;
    }

    /**
     * <p>Build the ZXSlideUp and add behavior to view</p>
     */
    public ZXSlideUp build() {
        return new ZXSlideUp(this);
    }

    /**
     * <p>Trying restore saved state</p>
     */
    private void restoreParams(@Nullable Bundle savedState) {
        if (savedState == null) return;
        mStateRestored = savedState.getBoolean(ZXSlideUp.KEY_STATE_SAVED, false);
        if (savedState.getSerializable(ZXSlideUp.KEY_STATE) != null) {
            mStartState = (ZXSlideUp.State) savedState.getSerializable(ZXSlideUp.KEY_STATE);
        }
        mStartGravity = savedState.getInt(ZXSlideUp.KEY_START_GRAVITY, mStartGravity);
        mDebug = savedState.getBoolean(ZXSlideUp.KEY_DEBUG, mDebug);
        mTouchableArea = savedState.getFloat(ZXSlideUp.KEY_TOUCHABLE_AREA, mTouchableArea) * mDensity;
        mStayArea = savedState.getFloat(ZXSlideUp.KEY_STAY_AREA, mStayArea)*mDensity;
        mAutoSlideDuration = savedState.getInt(ZXSlideUp.KEY_AUTO_SLIDE_DURATION, mAutoSlideDuration);
        mHideKeyboard = savedState.getBoolean(ZXSlideUp.KEY_HIDE_SOFT_INPUT, mHideKeyboard);
    }
}