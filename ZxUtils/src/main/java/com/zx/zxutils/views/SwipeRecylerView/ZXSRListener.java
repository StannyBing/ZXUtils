package com.zx.zxutils.views.SwipeRecylerView;

/**
 * Created by Xiangb on 2017/6/13.
 * 功能：
 */

public interface ZXSRListener {
    void onItemClick(Object item, int position);//点击事件

    void onItemLongClick(Object item, int position);//长按事件

    void onRefresh();//刷新事件

    void onLoadMore();//加载更多
}
