package com.zx.zxutils.views.ExpandableView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zx.zxutils.other.ZXExpandItemClickListener;
import com.zx.zxutils.other.ZXItemClickSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Xiangb on 2017/12/4.
 * 功能：
 */

public class ZXExpandRecyclerHelper {

    private Context context;
    private RecyclerView recyclerView;
    private ZXExpandAdapter adapter;
    private boolean isMultiSelected = true;
    private List<ZXExpandBean> dataList = new ArrayList<>();
    private List<ZXExpandBean> showList = new ArrayList<>();
    private ZXExpandItemClickListener itemClickListener;
    private boolean showMenuSelectView = false;

    public static ZXExpandRecyclerHelper getInstance(Context context) {
        ZXExpandRecyclerHelper zxExpandRecyclerHelper = new ZXExpandRecyclerHelper(context);
        return zxExpandRecyclerHelper;
    }

    public ZXExpandRecyclerHelper(Context context) {
        this.context = context;
    }

    public ZXExpandRecyclerHelper withRecycler(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        ZXItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(final RecyclerView recyclerView, int position, View view) {
                        try {
                            String id = showList.get(position).getId();
                            if (id != null && id.length() > 0) {
                                setShowById(dataList, id);

                                if (showList.get(position).getChildList() == null) {
                                    showList.get(position).setSelected(!showList.get(position).isSelected());
                                    if (itemClickListener != null) {
                                        itemClickListener.onItemClick(showList.get(position));//数据点击事件
                                    }
                                } else {
                                    if (itemClickListener != null) {
                                        itemClickListener.onMenuClick(showList.get(position));//菜单点击事件
                                    }
                                }

                                showList.clear();
                                refresh(dataList);


                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        return this;
    }

    //刷新
    private void refresh(List<ZXExpandBean> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            showList.add(dataList.get(i));
            if (dataList.get(i).getChildList() != null && dataList.get(i).getChildList().size() > 0 && dataList.get(i).isShowChild()) {
                refresh(dataList.get(i).getChildList());
            }
        }
    }

    //根据id寻找位置
    private void setShowById(List<ZXExpandBean> dataList, String id) {
        for (int i = 0; i < dataList.size(); i++) {
            if (id.equals(dataList.get(i).getId())) {
                dataList.get(i).setShowChild(!dataList.get(i).isShowChild());
                //如果关闭item，就关闭它的所有子集
                if (!dataList.get(i).isShowChild()) {
                    closeChild(dataList.get(i));
                }
                return;
            }
            if (dataList.get(i).getChildList() != null && dataList.get(i).getChildList().size() > 0) {
                setShowById(dataList.get(i).getChildList(), id);
            }
        }
    }

    //关闭子集
    private void closeChild(ZXExpandBean zxExpandBean) {
        if (zxExpandBean.getChildList() == null) {
            return;
        }
        for (int i = 0; i < zxExpandBean.getChildList().size(); i++) {
            zxExpandBean.getChildList().get(i).setShowChild(false);
            if (zxExpandBean.getChildList().get(i).getChildList() != null && zxExpandBean.getChildList().get(i).getChildList().size() > 0) {
                closeChild(zxExpandBean.getChildList().get(i));
            }
        }
    }

    //设置数据
    public ZXExpandRecyclerHelper setData(List<ZXExpandBean> dataList) {
        this.dataList = dataList;
        setIndex(this.dataList, 0);
        refresh(dataList);
        return this;
    }

    //循环设置层级
    private void setIndex(List<ZXExpandBean> dataList, int index) {
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setIndex(index);
            String uuid = UUID.randomUUID().toString();
            dataList.get(i).setId(uuid);
            dataList.get(i).setShowChild(false);
            if (dataList.get(i).getChildList() != null && dataList.get(i).getChildList().size() > 0) {
                int item = index + 1;
                setIndex(dataList.get(i).getChildList(), item);
            }
        }
    }

    //是否可以多选
    public ZXExpandRecyclerHelper multiSelected(boolean isMultiSelected) {
        this.isMultiSelected = isMultiSelected;
        return this;
    }

    //设置item的点击事件（非菜单）
    public ZXExpandRecyclerHelper setItemClickListener(ZXExpandItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

//    //是否显示菜单的选择栏
//    public ZXExpandRecyclerHelper showMenuSelectView(boolean showMenuSelectView) {
//        this.showMenuSelectView = showMenuSelectView;
//        return this;
//    }

    //构建
    public void build() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ZXExpandAdapter(context, showList, isMultiSelected);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

}
