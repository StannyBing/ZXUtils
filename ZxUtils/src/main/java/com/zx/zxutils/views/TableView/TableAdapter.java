package com.zx.zxutils.views.TableView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.util.ZXSystemUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Xiangb on 2017/4/12.
 * 功能：
 */

public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ZXTableKeyValues> tableList;
    public int titleCount = 0;
    public double valueSum = 0;
    public int titleBgColor;
    public boolean showTitleView = true;
    public boolean showPercent = true;
    public boolean showBackIcon = false;
    public String key = "类型";
    public String value1 = "数量";
    public String value2 = "数量2";
    public String value3 = "数量3";

    private ZXTableListener tableListener;

    public TableAdapter(Context context, List<ZXTableKeyValues> tableList) {
        this.context = context;
        this.tableList = tableList;
        titleBgColor = ZXSystemUtil.transColor( R.color.cadetblue);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_table_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder myHolder = (Holder) holder;
        ZXTableKeyValues tableEntity = tableList.get(position);
        setViewVisible(myHolder, position);
        setColor(myHolder, tableEntity);

        DecimalFormat df = new DecimalFormat("######0.00");
        if (tableEntity.isTitle()) {
            myHolder.tvKey.setText(key);
            myHolder.tvItem1.setText(value1);
            myHolder.tvItem2.setText(value2);
            myHolder.tvItem3.setText(value3);
            myHolder.tvPercent.setText("占比");
        } else {
            myHolder.tvKey.setText(tableEntity.getKey());
            myHolder.tvItem1.setText(df.format(tableEntity.getValue()) + "");
            myHolder.tvItem2.setText(df.format(tableEntity.getValue2()) + "");
            myHolder.tvItem3.setText(df.format(tableEntity.getValue3()) + "");

            if (valueSum == 0) {
                myHolder.tvPercent.setText("0%");
            } else {
                myHolder.tvPercent.setText(df.format(tableEntity.getValue() * 100 / valueSum) + "%");
            }
        }
    }

    /**
     * 设置事件监听
     *
     * @param tableListener
     */
    public void setTableListener(ZXTableListener tableListener) {
        this.tableListener = tableListener;
    }

    /**
     * 设置控件可见性
     *
     * @param myHolder
     * @param position
     */
    private void setViewVisible(Holder myHolder, int position) {
        if (titleCount == 4) {//value为3个时
            myHolder.space1.setVisibility(View.VISIBLE);
            myHolder.space2.setVisibility(View.VISIBLE);
            myHolder.tvItem2.setVisibility(View.VISIBLE);
            myHolder.tvItem3.setVisibility(View.VISIBLE);
        } else if (titleCount == 3) {//value为2个时
            myHolder.space1.setVisibility(View.VISIBLE);
            myHolder.tvItem2.setVisibility(View.VISIBLE);
        }
        if (showPercent) {
            myHolder.tvPercent.setVisibility(View.VISIBLE);
        } else {
            myHolder.tvPercent.setVisibility(View.GONE);
        }
        if (showBackIcon && position == 0) {
            myHolder.ivBack.setVisibility(View.VISIBLE);
        } else {
            myHolder.ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * 修改颜色
     *
     * @param myHolder
     * @param tableEntity
     */
    private void setColor(Holder myHolder, ZXTableKeyValues tableEntity) {
        if (tableEntity.isTitle()) {
            myHolder.llTable.setBackgroundColor(titleBgColor);
            myHolder.tvKey.setTextColor(ZXSystemUtil.transColor(R.color.whitesmoke));
            myHolder.tvItem1.setTextColor(ZXSystemUtil.transColor(R.color.whitesmoke));
            myHolder.tvItem2.setTextColor(ZXSystemUtil.transColor(R.color.whitesmoke));
            myHolder.tvItem3.setTextColor(ZXSystemUtil.transColor( R.color.whitesmoke));
            myHolder.tvPercent.setTextColor(ZXSystemUtil.transColor( R.color.whitesmoke));
        } else {
            myHolder.llTable.setBackgroundColor(ZXSystemUtil.transColor( R.color.whitesmoke));
            myHolder.tvKey.setTextColor(ZXSystemUtil.transColor(R.color.gray));
            myHolder.tvItem1.setTextColor(ZXSystemUtil.transColor(R.color.gray));
            myHolder.tvItem2.setTextColor(ZXSystemUtil.transColor(R.color.gray));
            myHolder.tvItem3.setTextColor(ZXSystemUtil.transColor(R.color.gray));
            myHolder.tvPercent.setTextColor(ZXSystemUtil.transColor(R.color.gray));
        }
    }

    @Override
    public int getItemCount() {

        return tableList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        public View space1, space2;
        public TextView tvKey, tvItem1, tvItem2, tvItem3, tvPercent;
        public ImageView ivBack;
        public LinearLayout llTable;

        public Holder(View parent) {
            super(parent);
            space1 = parent.findViewById(R.id.space_table_1);
            space2 = parent.findViewById(R.id.space_table_2);
            ivBack = (ImageView) parent.findViewById(R.id.iv_table_back);
            llTable = (LinearLayout) parent.findViewById(R.id.ll_table);
            tvKey = (TextView) parent.findViewById(R.id.tv_table_item1);
            tvItem1 = (TextView) parent.findViewById(R.id.tv_table_item2);
            tvItem2 = (TextView) parent.findViewById(R.id.tv_table_item3);
            tvItem3 = (TextView) parent.findViewById(R.id.tv_table_item4);
            tvPercent = (TextView) parent.findViewById(R.id.tv_table_itempercent);

            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tableListener != null) {
                        tableListener.OnBack();
                    }
                }
            });
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (tableListener != null && !(showTitleView && getAdapterPosition() == 0)) {
                            tableListener.OnItemClick(view, showTitleView ? getAdapterPosition() - 1 : getAdapterPosition());
                        }
                    } catch (Exception e) {//手速太快会导致出错，原因可能是界面还没初始化成功
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
