package com.zx.zxutils.views.TableView;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.zx.zxutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/4/7.
 * 功能：表格view
 */
public class ZXTableView extends LinearLayout {

    private Context context;
    private RecyclerView rvTable;
    private List<ZXTableKeyValues> tableList = new ArrayList<>();
    private TableAdapter tableAdapter;

    public ZXTableView(Context context) {
        super(context, null);
    }

    public ZXTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTableView(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void initTableView(Context context) {
        this.context = context;
        //在构造函数中将xml中定义的布局解析出来
        LayoutInflater.from(context).inflate(R.layout.view_table_view_layout, this, true);
        rvTable = (RecyclerView) findViewById(R.id.rv_table);
        rvTable.setLayoutManager(new LinearLayoutManager(context));
        tableAdapter = new TableAdapter(context, tableList);
        rvTable.setAdapter(tableAdapter);
    }

    //设置数据
    public ZXTableView setTableList(List<ZXTableKeyValues> tableList) {
        this.tableList.clear();
        this.tableList.addAll(tableList);
        int titleCount = 0;
        double valueSum = 0;
        for (int i = 0; i < tableList.size(); i++) {
            titleCount = titleCount < tableList.get(i).getTitleSum() ? tableList.get(i).getTitleSum() : titleCount;//获取最大title长度
            valueSum += tableList.get(i).getValue();//获取值的总数
        }
        if (valueSum == 0) {
            valueSum = 1;
        }
        tableAdapter.titleCount = titleCount;
        tableAdapter.valueSum = valueSum;

        if (tableAdapter.showTitleView) {
            ZXTableKeyValues titleValue = new ZXTableKeyValues();
            titleValue.setTitle(true);
            this.tableList.add(0, titleValue);
        }
        tableAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 设置事件监听
     *
     * @param tableListener
     * @return
     */
    public ZXTableView setTableListener(ZXTableListener tableListener) {
        tableAdapter.setTableListener(tableListener);
        return this;
    }

    /**
     * 设置界面管理器
     *
     * @param manager
     * @return
     */
    public ZXTableView setLayoutManager(RecyclerView.LayoutManager manager) {
        rvTable.setLayoutManager(manager);
        return this;
    }

    /**
     * 显示返回按钮
     *
     * @param show
     * @return
     */
    public ZXTableView showBackIcon(boolean show) {
        tableAdapter.showBackIcon = show;
        tableAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 返回按钮是否为显示
     *
     * @return
     */
    public boolean isShowBackIcon() {
        return tableAdapter.showBackIcon;
    }

    /**
     * 设置title背景颜色
     *
     * @param color
     * @return
     */
    public ZXTableView setTitleBg(int color) {
        tableAdapter.titleBgColor = color;
        tableAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 是否展示title栏
     *
     * @param show
     * @return
     */
    public ZXTableView showTitleView(boolean show) {
        tableAdapter.showTitleView = show;
        return this;
    }

    /**
     * 设置key和一个value
     *
     * @param key
     * @param value
     * @return
     */
    public ZXTableView setTitleInfo(String key, String value) {
        tableAdapter.key = key;
        tableAdapter.value1 = value;
        tableAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 设置key和两个value值
     *
     * @param key
     * @param value1
     * @param value2
     * @return
     */
    public ZXTableView setTitleInfo(String key, String value1, String value2) {
        tableAdapter.key = key;
        tableAdapter.value1 = value1;
        tableAdapter.value2 = value2;
        tableAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 设置key和三个value值
     *
     * @param key
     * @param value1
     * @param value2
     * @param value3
     * @return
     */
    public ZXTableView setTitleInfo(String key, String value1, String value2, String value3) {
        tableAdapter.key = key;
        tableAdapter.value1 = value1;
        tableAdapter.value2 = value2;
        tableAdapter.value3 = value3;
        tableAdapter.notifyDataSetChanged();
        return this;
    }


    /**
     * 是否展示占比栏
     *
     * @param show
     * @return
     */
    public ZXTableView showPercent(boolean show) {
        tableAdapter.showPercent = show;
        tableAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 获取recylerview
     *
     * @return
     */
    public RecyclerView getRecylerView() {
        return rvTable;
    }

    /**
     * 获得适配器
     *
     * @return
     */
    public TableAdapter getAdapter() {
        return tableAdapter;
    }

    /**
     * 获取数据列表
     *
     * @return
     */
    public List<ZXTableKeyValues> getTableList() {
        if (tableAdapter.showTitleView) {
            return tableList.subList(1, tableList.size());
        } else {
            return tableList;
        }
    }
}
