package com.zx.zxutils.views.TableView;

import java.io.Serializable;

/**
 * Created by Xiangb on 2017/4/12.
 * 功能：表格专用实体类
 */

public class ZXTableKeyValues implements Serializable {

    private boolean isTitle = false;
    private String key = "";
    private double value = 0;
    private double value2 = 0;
    private double value3 = 0;
    private int titleSum;

    public ZXTableKeyValues() {

    }

    //单个value
    public ZXTableKeyValues(String key, double value) {
        this.key = key;
        this.value = value;
        titleSum = 2;
    }

    //两个value
    public ZXTableKeyValues(String key, double value, double value2) {
        this.key = key;
        this.value = value;
        this.value2 = value2;
        titleSum = 3;
    }

    //三个value
    public ZXTableKeyValues(String key, double value, double value2, double value3) {
        this.key = key;
        this.value = value;
        this.value2 = value2;
        this.value3 = value3;
        titleSum = 4;
    }

    public int getTitleSum() {
        return titleSum;
    }

    public void setTitleSum(int titleSum) {
        this.titleSum = titleSum;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public double getValue3() {
        return value3;
    }

    public void setValue3(double value3) {
        this.value3 = value3;
    }
}
