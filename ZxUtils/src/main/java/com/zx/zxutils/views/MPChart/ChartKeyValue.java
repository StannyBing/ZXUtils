package com.zx.zxutils.views.MPChart;

/**
 * Created by Xiangb on 2017/4/6.
 * 功能：
 */
public class ChartKeyValue {
    public String key;
    public float value;

    public ChartKeyValue(String key, float value) {
        this.key = key;
        this.value = value;
    }

    public ChartKeyValue(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
