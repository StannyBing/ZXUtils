package com.zx.zxutils.util;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Xiangb on 2019/9/20.
 * 功能：
 */
public class ZXFloatViewUtil {

    private boolean handleTouched = false;
    private float dx = 0f;
    private float dy = 0f;
    private int moveToX = 0;
    private int moveToY = 0;
    private int distY = 0;
    private int distX = 0;
    private Rect inScreenCoordinates;
    private int MOVE_THRESHOLD = 0;

    public static ZXFloatViewUtil getInstance() {
        return new ZXFloatViewUtil();
    }

    public void setViewFloat(View view) {
        setViewFloat(view, -1, -1);
    }

    public void setViewFloatX(View view, int dragX) {
        setViewFloat(view, dragX, -1);
    }

    public void setViewFloatY(View view, int dragY) {
        setViewFloat(view, -1, dragY);
    }

    public void setViewFloat(final View view, final int dragX, final int dragY) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                boolean performClick = false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (handleTouched) {
                            moveToX = (int) (event.getRawX() - dx);
                            moveToY = (int) (event.getRawY() - dy);

                            distX = moveToX - params.topMargin;
                            distY = moveToY - params.leftMargin;

                            if (Math.abs(distY) > MOVE_THRESHOLD || Math.abs(distX) > MOVE_THRESHOLD) {
                                moveToY -= Integer.signum(distY) * Math.min(MOVE_THRESHOLD, Math.abs(distY));
                                moveToX -= Integer.signum(distX) * Math.min(MOVE_THRESHOLD, Math.abs(distX));

                                inScreenCoordinates = keepInScreen(view, moveToY, moveToX);
                                v.setY(inScreenCoordinates.top);
                                v.setX(inScreenCoordinates.left);
                            }
                            performClick = false;
                        } else {
                            performClick = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (handleTouched) {
                            view.invalidate();
                            performClick = false;
                        } else {
                            performClick = true;
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        view.bringToFront();

                        boolean handleX = false;
                        boolean handleY = false;
                        if (dragX != -1) {
                            handleX = event.getX() <= dragX; //按钮位置
                        } else {
                            handleX = true;
                        }
                        if (dragY != -1) {
                            handleY = event.getY() <= dragY; //按钮位置
                        } else {
                            handleY = true;
                        }
                        handleTouched = handleX && handleY;
                        dy = event.getRawY() - v.getY();
                        dx = event.getRawX() - v.getX();

                        //change handle color on tap
                        if (handleTouched) {
                            view.invalidate();
                            performClick = false;
                        } else {
                            performClick = true;
                        }
                        break;
                }
                return !performClick;
            }
        });
    }

    private Rect keepInScreen(View view, int topMargin, int leftMargin) {

        int top = topMargin;
        int left = leftMargin;

        if (top <= 0) {
            top = 0;
        } else if (top + view.getHeight() > ((View) (view.getParent())).getHeight()) {
            top = ((View) (view.getParent())).getHeight() - view.getHeight();
        }

        if (left <= 0) {
            left = 0;
        } else if (left + view.getWidth() > ((View) (view.getParent())).getWidth()) {
            left = ((View) (view.getParent())).getWidth() - view.getWidth();
        }

        return new Rect(left, top, left + view.getWidth(), top + view.getHeight());
    }
}
