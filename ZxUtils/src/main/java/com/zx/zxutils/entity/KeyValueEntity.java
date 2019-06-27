package com.zx.zxutils.entity;

/**
 * Created by Xiangb on 2017/4/6.
 * 功能：
 */
public class KeyValueEntity {
    public String key;
    public Object value;
    public boolean select = false;

    public KeyValueEntity(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public KeyValueEntity() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
