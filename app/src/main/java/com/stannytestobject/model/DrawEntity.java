package com.stannytestobject.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Xiangb on 2017/5/9.
 * 功能：
 */

public class DrawEntity {
    private Drawable icon;
    private String title;
    private boolean checked = false;

    public DrawEntity(Drawable icon, String title, boolean checked) {
        this.icon = icon;
        this.title = title;
        this.checked = checked;
    }

    public DrawEntity(Drawable icon, String title) {
        this.icon = icon;
        this.title = title;
        this.checked = false;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
