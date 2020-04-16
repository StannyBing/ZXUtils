package com.zx.zxutils.views.BottomSheet;

import android.graphics.drawable.Drawable;

/**
 * Created by Xiangb on 2018/1/19.
 * 功能：
 */

public class SheetData {

    private String name;
    private String detail;
    private Drawable img;

    private boolean selected;

    public SheetData(String name) {
        this.name = name;
    }

    public SheetData(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public SheetData(String name, Drawable img) {
        this.name = name;
        this.img = img;
    }

    public SheetData(String name, String detail, Drawable img) {
        this.name = name;
        this.detail = detail;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
