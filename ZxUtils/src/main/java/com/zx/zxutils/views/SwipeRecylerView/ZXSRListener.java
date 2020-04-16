package com.zx.zxutils.views.SwipeRecylerView;

/**
 * Created by Xiangb on 2017/6/13.
 * 功能：
 */

public interface ZXSRListener<T> {
    void onItemClick(T item, int position);//点击事件

    void onItemLongClick(T item, int position);//长按事件

    void onRefresh();//刷新事件

    void onLoadMore();//加载更多
}
