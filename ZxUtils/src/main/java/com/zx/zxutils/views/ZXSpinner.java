package com.zx.zxutils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.entity.KeyValueEntity;
import com.zx.zxutils.util.ZXSystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/6/1.
 * 功能：
 */

public class ZXSpinner extends androidx.appcompat.widget.AppCompatSpinner {
    private List<KeyValueEntity> dataList = new ArrayList<>();
    private Context mContext;
    private MySpinnerAdapter mAdapter;
    private TypedArray defaultArray;
    private boolean showDivider = false;//是否展示分隔线
    private boolean showSelectedTextColor = false;//是否显示选中item的字体颜色
    private boolean showSelectedLayoutColor = false;//是否显示选中layout的颜色
    private boolean showUnderLine = true;//是否显示underline
    private boolean hasDefaultItem = false;
    private int underLineColor = 0;//下划线颜色
    private int dividerColor = 0;//分隔线颜色
    private int selectedTextColor = 0;//选中的字体颜色
    private int selectedLyoutColor = 0;//选中的layout的颜色
    private int defaultPosition = -1;//默认item
    private int itemHeightDp = 0;//item高度
    private int itemTextSizeSp = 0;//字体大小

    public ZXSpinner(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ZXSpinner(Context context, int mode) {
        super(context, mode);
        mContext = context;
        init();
    }

    public ZXSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        defaultArray = mContext.obtainStyledAttributes(new int[]{R.attr.colorAccent});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showUnderLine) {
            if (underLineColor == 0) {
                underLineColor = defaultArray.getColor(0, ZXSystemUtil.transColor(R.color.gray));
            }
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(underLineColor);
            int startX = 0;
            int endX = getWidth();
            int lineHeight = ZXSystemUtil.dp2px(1);
            int startYLine = getHeight() - getPaddingBottom() - ZXSystemUtil.dp2px(8);
            canvas.drawRect(startX, startYLine, endX, startYLine + lineHeight, paint);
        }
        setDropDownWidth(getWidth());
        setDropDownVerticalOffset(getHeight());
    }

    @Override
    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener listener) {
        OnItemSelectedListener mListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (hasDefaultItem) {
                    listener.onItemSelected(adapterView, view, i - 1, l);
                } else {
                    listener.onItemSelected(adapterView, view, i, l);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                listener.onNothingSelected(adapterView);
            }
        };
        super.setOnItemSelectedListener(mListener);
    }

    /**
     * 设置data
     *
     * @param dataList
     * @return
     */
    public ZXSpinner setData(List<KeyValueEntity> dataList) {
        this.dataList = dataList;
        return this;
    }

    /**
     * 添加data
     *
     * @param dataInfo
     * @return
     */
    public ZXSpinner addData(KeyValueEntity dataInfo) {
        dataList.add(dataInfo);
        return this;
    }

    /**
     * 是否展示下划线
     *
     * @param showDivider
     * @return
     */
    public ZXSpinner showDivider(boolean showDivider) {
        dividerColor = R.color.gray_cc;
        showDivider(showDivider, dividerColor);
        return this;
    }

    /**
     * 是否展示下划线
     *
     * @param showDivider
     * @return
     */
    public ZXSpinner showDivider(boolean showDivider, int dividerColor) {
        this.showDivider = showDivider;
        this.dividerColor = ZXSystemUtil.transColor(dividerColor);
        return this;
    }

    /**
     * 设置下划线颜色
     *
     * @return
     */
    public ZXSpinner showUnderineColor(boolean showUnderLine) {
        this.showUnderLine = showUnderLine;
        underLineColor = defaultArray.getColor(0, ZXSystemUtil.transColor(R.color.gray));
        return this;
    }

    /**
     * 设置下划线颜色
     *
     * @return
     */
    public ZXSpinner showUnderineColor(boolean showUnderLine, int color) {
        this.showUnderLine = showUnderLine;
        underLineColor = ZXSystemUtil.transColor(color);
        return this;
    }

    /**
     * 是否显示选中字体颜色
     *
     * @param showSelectedTextColor
     * @return
     */
    public ZXSpinner showSelectedTextColor(boolean showSelectedTextColor) {
        this.showSelectedTextColor = showSelectedTextColor;
        selectedTextColor = defaultArray.getColor(0, ZXSystemUtil.transColor(R.color.gray));
        this.selectedLyoutColor = 0;
        this.showSelectedLayoutColor = false;
        return this;
    }

    /**
     * 是否显示选中字体颜色
     *
     * @param showSelectedTextColor
     * @param selectedTextColor
     * @return
     */
    public ZXSpinner showSelectedTextColor(boolean showSelectedTextColor, int selectedTextColor) {
        this.showSelectedTextColor = showSelectedTextColor;
        this.selectedTextColor = ZXSystemUtil.transColor(selectedTextColor);
        this.selectedLyoutColor = 0;
        this.showSelectedLayoutColor = false;
        return this;
    }

    /**
     * 是否显示选中item的layout颜色
     *
     * @param showSelectedLayoutColor
     * @return
     */
    public ZXSpinner showSeletedLayoutColor(boolean showSelectedLayoutColor) {
        this.showSelectedLayoutColor = showSelectedLayoutColor;
        selectedLyoutColor = defaultArray.getColor(0, ZXSystemUtil.transColor(R.color.gray));
        this.selectedTextColor = ZXSystemUtil.transColor(R.color.white);
        this.showSelectedTextColor = false;
        return this;
    }

    /**
     * 是否显示选中item的layout颜色
     *
     * @param showSelectedLayoutColor
     * @param seletedLayoutColor
     * @return
     */
    public ZXSpinner showSeletedLayoutColor(boolean showSelectedLayoutColor, int seletedLayoutColor) {
        this.showSelectedLayoutColor = showSelectedLayoutColor;
        this.selectedLyoutColor = ZXSystemUtil.transColor(seletedLayoutColor);
        this.selectedTextColor = ZXSystemUtil.transColor(R.color.white);
        this.showSelectedTextColor = false;
        return this;
    }

    /**
     * 默认选中位置
     *
     * @param position
     * @return
     */
    public ZXSpinner setDefaultItem(int position) {
        defaultPosition = position;
        return this;
    }

    /**
     * 添加默认选中
     *
     * @param defaultItem
     * @return
     */
    public ZXSpinner setDefaultItem(String defaultItem) {
        hasDefaultItem = true;
        dataList.add(0, new KeyValueEntity(defaultItem, ""));
        return this;
    }

    /**
     * 设置item高度
     *
     * @return
     */
    public ZXSpinner setItemHeightDp(int heightDp) {
        itemHeightDp = heightDp;
        return this;
    }

    /**
     * 设置item的字体大小
     *
     * @param textSizeSp
     * @return
     */
    public ZXSpinner setItemTextSizeSp(int textSizeSp) {
        itemTextSizeSp = textSizeSp;
        return this;
    }

    /**
     * 更新
     *
     * @return
     */
    public ZXSpinner notifyDataSetChanged() {
        List<KeyValueEntity> tempList = new ArrayList<>();
        tempList.addAll(dataList);
        dataList.clear();
        dataList.addAll(tempList);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 获取适配器
     *
     * @return
     */
    public MySpinnerAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 获取参数
     *
     * @return
     */
    public List<KeyValueEntity> getDataList() {
        return dataList;
    }

    /**
     * 开始构造
     */
    public void build() {
        mAdapter = new MySpinnerAdapter(dataList);
        setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        if (defaultPosition >= 0 && defaultPosition < dataList.size()) {
            setSelection(defaultPosition);
        }
    }

    /**
     * 获取value
     *
     * @return
     */
    public Object getSelectedValue() {
        return getSelectedValue(getSelectedItemPosition());
    }

    /**
     * 获取value
     *
     * @return
     */
    public Object getSelectedValue(int position) {
        return dataList.get(position).getValue();
    }

    /**
     * 获取key
     *
     * @return
     */
    public String getSelectedKey() {
        return getSelectedkey(getSelectedItemPosition());
    }

    /**
     * 获取key
     *
     * @param position
     * @return
     */
    public String getSelectedkey(int position) {
        return dataList.get(position).getKey();
    }

    public KeyValueEntity getSelectedEntity() {
        return getSelectedEntity(getSelectedItemPosition());
    }

    public KeyValueEntity getSelectedEntity(int position) {
        return dataList.get(position);
    }

    private class MySpinnerAdapter extends BaseAdapter {

        private List<KeyValueEntity> dataList;

        public MySpinnerAdapter(List<KeyValueEntity> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        /**
         * item的view
         *
         * @param position
         * @param itemView
         * @param parent
         * @return
         */
        @Override
        public View getDropDownView(int position, View itemView, ViewGroup parent) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_normal, null);
            TextView tvItem = itemView.findViewById(R.id.tv_spinner_item);
            View dividerItem = itemView.findViewById(R.id.divider_spinner_item);
            tvItem.setText(dataList.get(position).getKey());
            try {
                if (showDivider) {
                    dividerItem.setVisibility(VISIBLE);
                    dividerItem.setBackgroundColor(dividerColor);
                } else {
                    dividerItem.setVisibility(GONE);
                }
                if (showSelectedLayoutColor && position == getSelectedItemPosition()) {
                    tvItem.setTextColor(selectedTextColor);
                    itemView.setBackgroundColor(selectedLyoutColor);
                } else {
//                mHolder.tvItem.setTextColor(ContextCompat.getColor(mContext, R.color.default_text_color));
//                itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.whitesmoke));
                }
                if (showSelectedTextColor && position == getSelectedItemPosition()) {
                    tvItem.setTextColor(selectedTextColor);
                } else {
//                mHolder.tvItem.setTextColor(ContextCompat.getColor(mContext, R.color.default_text_color));
                }
                if (itemHeightDp != 0) {
                    LayoutParams params = tvItem.getLayoutParams();
                    params.height = ZXSystemUtil.dp2px(itemHeightDp);
                    tvItem.setLayoutParams(params);
                }
                if (itemTextSizeSp != 0) {
                    tvItem.setTextSize(itemTextSizeSp);
                }
            } catch (Exception e) {
                new Throwable("请检查传入颜色值是否正确，格式为R.color.**");
            }
            return itemView;
        }

        /**
         * spinner的view
         *
         * @param i
         * @param itemView
         * @param viewGroup
         * @return
         */
        @Override
        public View getView(int i, View itemView, ViewGroup viewGroup) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_normal, null);
            TextView textView = (TextView) itemView.findViewById(R.id.tv_spinner_item);
            textView.setText(dataList.get(i).getKey());
            if (itemTextSizeSp != 0) {
                textView.setTextSize(itemTextSizeSp);
            }
            return itemView;
        }
    }

}
