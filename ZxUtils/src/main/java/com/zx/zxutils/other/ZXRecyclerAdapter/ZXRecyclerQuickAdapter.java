package com.zx.zxutils.other.ZXRecyclerAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.other.QuickAdapter.ZXBaseHolder;
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter;
import com.zx.zxutils.views.SwipeRecylerView.LoadMoreListener;
import com.zx.zxutils.views.SwipeRecylerView.NotifyListener;

import java.util.List;

/**
 * Created by Xiangb on 2018/7/27.
 * 功能：
 */
public abstract class ZXRecyclerQuickAdapter<T extends Object, K extends ZXBaseHolder> extends ZXQuickAdapter<T, K> {

    private LoadMoreListener mLoadMoreListener;
    private NotifyListener notifyListener;

    private TextView loadText;
    private ProgressBar loadProgress;
    public int pageSize = 10;
    public boolean hasLoadMore = true;

    public ZXRecyclerQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public ZXRecyclerQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    public ZXRecyclerQuickAdapter(int layoutResId) {
        super(layoutResId);
    }


    public void withFooter(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle_foot_view, null, false);
        loadText = view.findViewById(R.id.load_tv);
        loadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.LoadMore();
                }
            }
        });
        loadProgress = view.findViewById(R.id.load_progress);
        addFooterView(view);
    }

    @Override
    protected void convert(K helper, T item) {
        if (notifyListener != null) {
            notifyListener.onNotifyEnd();
        }
        if (!hasLoadMore) {
            removeAllFooterView();
        }
        quickConvert(helper, item);
    }

//    @Override
//    public void onBindViewHolder(@NonNull K holder, int position) {
//        if (!hasLoadMore) {
//            removeAllFooterView();
//        }
//    }

    public abstract void quickConvert(K helper, T item);

    public void setNotifyListener(NotifyListener notifyListener) {
        this.notifyListener = notifyListener;
    }

    public void setOnLoadMoreListener(LoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    public void doLoading() {
        loadProgress.setVisibility(View.VISIBLE);
        loadText.setText("正在加载中。。");
    }

    public void setStatus(String infoMsg) {
        loadText.setVisibility(View.VISIBLE);
        loadProgress.setVisibility(View.GONE);
        loadText.setText(infoMsg);
    }

    public void setStatus(int mPageNum, int mTotalNum) {
        loadText.setVisibility(View.VISIBLE);
        if (mTotalNum == 0) {
            loadProgress.setVisibility(View.GONE);
            loadText.setText("没有数据");
        } else if (mPageNum * pageSize < mTotalNum) {
            loadProgress.setVisibility(View.GONE);
            int pageTotal = 0;
            if (mTotalNum % pageSize == 0) {
                pageTotal = mTotalNum / pageSize;
            } else {
                pageTotal = mTotalNum / pageSize + 1;
            }
            loadText.setText("点击加载更多，第" + mPageNum + "页，共" + pageTotal + "页");
        } else if (mPageNum == 1 && mTotalNum < pageSize) {
            loadProgress.setVisibility(View.GONE);
            loadText.setVisibility(View.GONE);
        } else {
            loadProgress.setVisibility(View.GONE);
            loadText.setText("已加载完");
        }
    }


}
