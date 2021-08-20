package com.zx.zxutils.other.ZXRecyclerAdapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.views.SwipeRecylerView.LoadMoreListener;
import com.zx.zxutils.views.SwipeRecylerView.NotifyListener;

import java.util.List;

/**
 * Created by Xiangb on 2016/9/21.
 * 功能：封装的Recycler适配器。用于待FooterView的情况
 */
@Deprecated
//该类已过期，请使用ZXRecyclerQuickAdapter
public abstract class ZXRecycleSimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LoadMoreListener mLoadMoreListener;
    public FooterViewHolder footerViewHolder;
    private NotifyListener notifyListener;
    //全局变量
    private static final int ITEM_TYPE_NORMAL = 1;
    private static final int ITEM_TYPE_FOOTER = 2;

    public boolean hasLoadMore = true;

    public int pageSize = 10;//每页数量

    private List<?> dataList;

    public Context mContext;

    public List<?> getDataList() {
        return dataList;
    }

    public abstract RecyclerView.ViewHolder onItemHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(RecyclerView.ViewHolder holder, int position);

    public abstract List onItemList();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == ITEM_TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_foot_view, parent, false);
            return new FooterViewHolder(view);
        } else {
            RecyclerView.ViewHolder holder = onItemHolder(parent, viewType);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            footerViewHolder = (FooterViewHolder) holder;
            if (notifyListener != null) {
                notifyListener.onNotifyEnd();
            }
        } else {
            dataList = onItemList();
            onBindHolder(holder, position);
        }
    }

    public void setOnLoadMoreListener(LoadMoreListener listener) {
        this.mLoadMoreListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasLoadMore && position + 1 == getItemCount()) {
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {

        dataList = onItemList();
        int count = 0;
        if (dataList != null) {
            if (hasLoadMore) {
                count = dataList.size() + 1;
            } else {
                count = dataList.size();
            }
        }
        return count;
    }

    public void setNotifyListener(NotifyListener notifyListener) {
        this.notifyListener = notifyListener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private TextView loadText;
        private ProgressBar loadProgress;

        public FooterViewHolder(View parent) {
            super(parent);
            loadText = (TextView) parent.findViewById(R.id.load_tv);
            loadProgress = (ProgressBar) parent.findViewById(R.id.load_progress);
            loadText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLoadMoreListener != null) {
                        mLoadMoreListener.LoadMore();
                    }
                }
            });
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
            }else {
                loadProgress.setVisibility(View.GONE);
                loadText.setText("已加载完");
            }
        }
    }
}
