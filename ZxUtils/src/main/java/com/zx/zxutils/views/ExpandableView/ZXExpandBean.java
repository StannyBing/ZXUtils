package com.zx.zxutils.views.ExpandableView;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/4.
 * 功能：
 */

public class ZXExpandBean {

    private int index;
    private List<ZXExpandBean> childList;
    private boolean showChild = true;
    private boolean selected = false;
    private String id;
    private Object customData;

    public ZXExpandBean(Object customData) {
        this.customData = customData;
    }

    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(Object customData) {
        this.customData = customData;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isShowChild() {
        return showChild;
    }

    public void setShowChild(boolean showChild) {
        this.showChild = showChild;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<ZXExpandBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ZXExpandBean> childList) {
        this.childList = childList;
    }
}
