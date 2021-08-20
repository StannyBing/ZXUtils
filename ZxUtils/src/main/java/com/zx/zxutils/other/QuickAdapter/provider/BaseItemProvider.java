package com.zx.zxutils.other.QuickAdapter.provider;

import android.content.Context;
import androidx.annotation.NonNull;

import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder;

import java.util.List;

/**
 * https://github.com/chaychan
 * @author ChayChan
 * @description: The base class of ItemProvider
 * @date 2018/3/21  10:41
 */

public abstract class BaseItemProvider<T,V extends ZXBaseHolder> {

    public Context mContext;
    public List<T> mData;

    //子类须重写该方法返回viewType
    //Rewrite this method to return viewType
    public abstract int viewType();

    //子类须重写该方法返回layout
    //Rewrite this method to return layout
    public abstract int layout();

    public abstract void convert(@NonNull V helper, T data, int position);

    public void convertPayloads(@NonNull V helper, T data, int position, @NonNull List<Object> payloads){}

    //子类若想实现条目点击事件则重写该方法
    //Subclasses override this method if you want to implement an item click event
    public void onClick(V helper, T data, int position){}

    //子类若想实现条目长按事件则重写该方法
    //Subclasses override this method if you want to implement an item long press event
    public boolean onLongClick(V helper, T data, int position){return false;}
}
