package com.stanny.demo.model;


import com.stanny.demo.adapter.ExpandableItemAdapter;
import com.zx.zxutils.other.QuickAdapter.entity.MultiItemEntity;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Person implements MultiItemEntity {
    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public String name;
    public int age;

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_PERSON;
    }
}