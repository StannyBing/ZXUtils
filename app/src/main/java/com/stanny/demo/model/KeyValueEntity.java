package com.stanny.demo.model;

import java.io.Serializable;

/**
 * Created by Xiangb on 2017/4/6.
 * 功能：
 */
public class KeyValueEntity implements Serializable {
    public String key;
    public String value;

    public KeyValueEntity(String key, String value) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
