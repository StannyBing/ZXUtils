package com.zx.zxutils.views.SwipeRecylerView;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zx.zxutils.R;
import com.zx.zxutils.other.QuickAdapter.ZXQuickAdapter;
import com.zx.zxutils.other.ZXItemClickSupport;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecycleAdapter;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecycleSimpleAdapter;
import com.zx.zxutils.other.ZXRecyclerAdapter.ZXRecyclerQuickAdapter;

import java.util.List;


/**
 * Created by Xiangb on 2017/6/13.
 * 功能：
 */

public class ZXSwipeRecyler extends LinearLayout {
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ZXSRListener zxsrListener;
    private ZXRecycleAdapter mAdapter;
    private ZXRecycleSimpleAdapter simpleAdapter;
    private ZXRecyclerQuickAdapter quickAdapter;
    private boolean autoLoadMore = false;
    private int pageNum = 1, totalNum = 0, pageSize = 10;


    public ZXSwipeRecyler(Context context) {
        super(context);
        init(context);
    }

    public ZXSwipeRecyler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        //在构造函数中将xml中定义的布局解析出来
        LayoutInflater.from(context).inflate(R.layout.view_swipe_recyler, this, true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_normal_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_normal_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ZXItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                if (mAdapter != null) {
                    if (zxsrListener != null && position < mAdapter.getDataList().size()) {
                        zxsrListener.onItemClick(mAdapter.getDataList().get(position), position);
                    }
                } else if (simpleAdapter != null) {
                    if (zxsrListener != null && position < simpleAdapter.getDataList().size()) {
                        zxsrListener.onItemClick(simpleAdapter.getDataList().get(position), position);
                    }
                } else if (quickAdapter != null) {
                    if (zxsrListener != null && position < quickAdapter.getData().size()) {
                        zxsrListener.onItemClick(quickAdapter.getData().get(position), position);
                    }
                }
            }
        }).setOnItemLongClickListener(new ZXItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View view) {
                if (mAdapter != null) {
                    if (zxsrListener != null && position < mAdapter.getDataList().size()) {
                        zxsrListener.onItemLongClick(mAdapter.getDataList().get(position), position);
                    }
                } else if (simpleAdapter != null) {
                    if (zxsrListener != null && position < simpleAdapter.getDataList().size()) {
                        zxsrListener.onItemLongClick(simpleAdapter.getDataList().get(position), position);
                    }
                } else if (quickAdapter != null) {
                    if (zxsrListener != null && position < quickAdapter.getData().size()) {
                        zxsrListener.onItemLongClick(quickAdapter.getData().get(position), position);
                    }
                }
                return true;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (zxsrListener != null) {
                    if (autoLoadMore) {
                        pageNum = 1;
                    } else {
                        if (pageNum > 1) {
                            pageNum--;
                        }
                    }
                    zxsrListener.onRefresh();
                }
            }
        });
    }

    /**
     * 设置布局管理器
     */
    public ZXSwipeRecyler setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        return this;
    }

    //添加分隔线
    public ZXSwipeRecyler addDivider() {
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        return this;
    }

    /**
     * 设置适配器
     *
     * @param adapter
     * @return
     */
    public ZXSwipeRecyler setAdapter(ZXRecycleAdapter adapter) {
        this.mAdapter = adapter;
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new LoadMoreListener() {
            @Override
            public void LoadMore() {
                if (zxsrListener != null) {
                    if (pageNum * pageSize < totalNum) {
                        if (mAdapter != null && mAdapter.footerViewHolder != null) {
                            mAdapter.footerViewHolder.doLoading();
                        }
                        pageNum++;
                        zxsrListener.onLoadMore();
                    }
                }
            }
        });
        adapter.setNotifyListener(new NotifyListener() {
            @Override
            public void onNotifyEnd() {
                if (mAdapter != null && mAdapter.footerViewHolder != null) {
                    mAdapter.footerViewHolder.setStatus(pageNum, totalNum);
                } else if (simpleAdapter != null && simpleAdapter.footerViewHolder != null) {
                    simpleAdapter.footerViewHolder.setStatus(pageNum, totalNum);
                } else if (quickAdapter != null) {
                    if (!autoLoadMore) {
                        quickAdapter.setStatus(pageNum, totalNum);
                    }
                }
            }
        });
        return this;
    }

    /**
     * 设置适配器
     *
     * @param adapter
     * @return
     */
    public ZXSwipeRecyler setAdapter(ZXRecycleSimpleAdapter adapter) {
        this.simpleAdapter = adapter;
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new LoadMoreListener() {
            @Override
            public void LoadMore() {
                if (zxsrListener != null) {
                    if (pageNum * pageSize < totalNum) {
                        if (simpleAdapter != null && simpleAdapter.footerViewHolder != null) {
                            simpleAdapter.footerViewHolder.doLoading();
                        }
                        pageNum++;
                        zxsrListener.onLoadMore();
                    }
                }
            }
        });
        adapter.setNotifyListener(new NotifyListener() {
            @Override
            public void onNotifyEnd() {
                if (mAdapter != null && mAdapter.footerViewHolder != null) {
                    mAdapter.footerViewHolder.setStatus(pageNum, totalNum);
                } else if (simpleAdapter != null && simpleAdapter.footerViewHolder != null) {
                    simpleAdapter.footerViewHolder.setStatus(pageNum, totalNum);
                } else if (quickAdapter != null) {
                    if (!autoLoadMore) {
                        quickAdapter.setStatus(pageNum, totalNum);
                    }
                }
            }
        });
        return this;
    }

    public ZXSwipeRecyler setAdapter(ZXRecyclerQuickAdapter adapter) {
        quickAdapter = adapter;
        recyclerView.setAdapter(quickAdapter);
        quickAdapter.setNotifyListener(new NotifyListener() {
            @Override
            public void onNotifyEnd() {
                if (mAdapter != null && mAdapter.footerViewHolder != null) {
                    mAdapter.footerViewHolder.setStatus(pageNum, totalNum);
                } else if (simpleAdapter != null && simpleAdapter.footerViewHolder != null) {
                    simpleAdapter.footerViewHolder.setStatus(pageNum, totalNum);
                } else if (quickAdapter != null) {
                    if (!autoLoadMore) {
                        quickAdapter.setStatus(pageNum, totalNum);
                    }
                }
            }
        });
        if (!autoLoadMore) {
            quickAdapter.withFooter(context);
        }
        if (autoLoadMore) {
            quickAdapter.setOnLoadMoreListener(new ZXQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (zxsrListener != null) {
                        if (pageNum * pageSize < totalNum) {
                            if (quickAdapter != null && !autoLoadMore) {
                                quickAdapter.doLoading();
                            }
                            pageNum++;
                            zxsrListener.onLoadMore();
                        } else {
                            quickAdapter.loadMoreEnd();
                        }
                    }
                }
            }, recyclerView);
        } else {
            quickAdapter.setOnLoadMoreListener(new LoadMoreListener() {
                @Override
                public void LoadMore() {
                    if (zxsrListener != null) {
                        if (pageNum * pageSize < totalNum) {
                            if (quickAdapter != null && !autoLoadMore) {
                                quickAdapter.doLoading();
                            }
                            pageNum++;
                            zxsrListener.onLoadMore();
                        }
                    }
                }
            });
        }
        return this;
    }

    /**
     * 获取适配器
     *
     * @return
     */
    public ZXRecycleAdapter getAdapter() {
        return mAdapter;
    }

    public ZXRecycleSimpleAdapter getSimpleAdapter() {
        return simpleAdapter;
    }

    public ZXRecyclerQuickAdapter getQuickAdapter() {
        return quickAdapter;
    }

    /**
     * 获得recylerview对象
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * 获得swiperefresh对象
     *
     * @return
     */
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    /**
     * 设置每页数量
     *
     * @param pageSize
     * @return
     */
    public ZXSwipeRecyler setPageSize(int pageSize) {
        this.pageSize = pageSize;
        if (mAdapter != null) {
            mAdapter.pageSize = pageSize;
        } else if (simpleAdapter != null) {
            simpleAdapter.pageSize = pageSize;
        } else if (quickAdapter != null) {
            quickAdapter.pageSize = pageSize;
        }
        return this;
    }

    /**
     * 设置相关监听
     *
     * @param zxsrListener
     * @return
     */
    public ZXSwipeRecyler setSRListener(ZXSRListener zxsrListener) {
        this.zxsrListener = zxsrListener;
        return this;
    }

    /**
     * 设置自动加载更多
     *
     * @return
     */
    public ZXSwipeRecyler autoLoadMore() {
        autoLoadMore = true;
        if (quickAdapter != null) {
            quickAdapter.setOnLoadMoreListener(new ZXQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (zxsrListener != null) {
                        if (pageNum * pageSize < totalNum) {
                            if (quickAdapter != null && !autoLoadMore) {
                                quickAdapter.doLoading();
                            }
                            pageNum++;
                            zxsrListener.onLoadMore();
                        } else {
                            quickAdapter.loadMoreEnd();
                        }
                    }
                }
            }, recyclerView);
        }
        return this;
    }

    /**
     * 数据更新
     */
    public void refreshData(List data, int totalNum) {
        if (quickAdapter != null) {
            if (autoLoadMore) {
                if (pageNum == 1) {
                    quickAdapter.dataBeans.clear();
                    quickAdapter.getData().clear();
                }
//                quickAdapter.dataBeans.addAll(data);
                quickAdapter.addData(data);
                if (pageNum < totalNum / pageSize + 1) {
                    quickAdapter.loadMoreComplete();
                } else {
                    quickAdapter.loadMoreEnd();
                }
                if (totalNum == 0) {
                    if (quickAdapter.loadText != null) {
                        quickAdapter.loadText.setVisibility(View.VISIBLE);
                        quickAdapter.loadText.setText("暂无数据");
                    }
                } else {
                    quickAdapter.loadText.setVisibility(View.GONE);
                }
            } else {
                quickAdapter.dataBeans.clear();
                quickAdapter.getData().clear();
                quickAdapter.dataBeans.addAll(data);
                quickAdapter.setNewData(data);
                recyclerView.scrollToPosition(0);
            }
            setLoadInfo(totalNum);
            stopRefresh();
        }
    }

    /**
     * 设置是否带有加载信息
     *
     * @param hasLoadInfo
     * @return
     */
    public ZXSwipeRecyler showLoadInfo(boolean hasLoadInfo) {
        if (mAdapter != null) {
            mAdapter.hasLoadMore = hasLoadInfo;
        } else if (simpleAdapter != null) {
            simpleAdapter.hasLoadMore = hasLoadInfo;
        } else if (quickAdapter != null) {
            quickAdapter.hasLoadMore = hasLoadInfo;
        }
        return this;
    }

    public void setPagetNum(int pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 数据更新
     */
    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        } else if (simpleAdapter != null) {
            simpleAdapter.notifyDataSetChanged();
        } else if (quickAdapter != null) {
            quickAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 关闭刷新状态
     */
    public void stopRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 设置加载信息
     *
     * @param totalNum
     */
    public void setLoadInfo(int totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * 清除状态信息
     */
    public void clearStatus() {
        totalNum = 0;
        pageNum = 1;
        stopRefresh();
    }

    /**
     * 设置加载信息
     *
     * @param infoMsg
     */
    private void setLoadInfo(String infoMsg) {
        totalNum = pageSize * pageNum + 1;
        if (mAdapter != null && mAdapter.footerViewHolder != null) {
            mAdapter.footerViewHolder.setStatus(infoMsg);
        } else if (simpleAdapter != null && simpleAdapter.footerViewHolder != null) {
            simpleAdapter.footerViewHolder.setStatus(infoMsg);
        } else if (quickAdapter != null) {
            if (!autoLoadMore) {
                quickAdapter.setStatus(infoMsg);
            }
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public int getPageSize() {
        return pageSize;
    }
}
