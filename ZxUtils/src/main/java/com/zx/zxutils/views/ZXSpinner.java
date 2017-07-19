package com.zx.zxutils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.zx.zxutils.R;
import com.zx.zxutils.entity.KeyValueEntity;
import com.zx.zxutils.util.ZXSystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/6/1.
 * 功能：
 */

public class ZXSpinner extends android.support.v7.widget.AppCompatSpinner {
    private List<KeyValueEntity> dataList = new ArrayList<>();
    private Context mContext;
    private Drawable bgDown, bgUp;
    private int underLineColor = 0;
    private ArrayAdapter adapter;
    private int itemView = android.R.layout.simple_spinner_dropdown_item;
    private boolean mOpenInitiated = false;

    public enum Style {
        normal,//基本类型
        radio,//类似于radiobutton类型
        blank,//有分隔线
        check,//类似于checkbox类型
        full//选中颜色
    }

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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray defaultArray = mContext.obtainStyledAttributes(new int[]{R.attr.colorAccent});
        if (underLineColor == 0) {
            underLineColor = defaultArray.getColor(0, ContextCompat.getColor(mContext, R.color.gray));
//            underLineColor = ContextCompat.getColor(mContext, R.color.gray);
        }
        paint.setColor(underLineColor);
        int startX = 0;
        int endX = getWidth();
        int lineHeight = ZXSystemUtil.dp2px(1);
        int startYLine = getHeight() - getPaddingBottom() - ZXSystemUtil.dp2px(8);
        canvas.drawRect(startX, startYLine, endX, startYLine + lineHeight, paint);
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
     * 设置spinner item的风格
     *
     * @param style
     * @return
     */
    public ZXSpinner setItemStyle(Style style) {
        switch (style) {
            case normal:
                itemView = android.R.layout.simple_spinner_dropdown_item;
                break;
            case radio:
                itemView = android.R.layout.simple_list_item_single_choice;
                break;
            case check:
                itemView = android.R.layout.simple_list_item_multiple_choice;
                break;
            case full:
                itemView = android.R.layout.simple_list_item_activated_1;
                break;
            case blank:
                itemView = android.R.layout.preference_category;
                break;
            default:
                break;
        }
        return this;
    }

    /**
     * 设置背景图片
     *
     * @param bgDrawable
     * @return
     */
    public ZXSpinner setBg(Drawable bgDrawable) {
        this.bgDown = bgDrawable;
        this.bgUp = bgDrawable;
        return this;
    }

    //设置关闭状态的图片
    public ZXSpinner setBgDown(Drawable bgDown) {
        this.bgDown = bgDown;
        return this;
    }

    //设置开启状态的图片
    public ZXSpinner setBgUp(Drawable bgUp) {
        this.bgUp = bgUp;
        return this;
    }

    public ZXSpinner setUnderineColor(int color) {
        underLineColor = color;
        return this;
    }

    /**
     * 更新
     *
     * @return
     */
    public ZXSpinner notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
        return this;
    }

    /**
     * 获取适配器
     *
     * @return
     */
    public ArrayAdapter getAdapter() {
        return adapter;
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
        if (bgDown != null) {
            setBackground(bgDown);
        }
        List<String> spinnerKey = new ArrayList<>();
        for (KeyValueEntity dataInfo : dataList) {
            spinnerKey.add(dataInfo.getKey());
        }
        adapter = new ArrayAdapter(mContext, itemView, spinnerKey);
        setAdapter(adapter);
    }

    @Override
    public boolean performClick() {
        mOpenInitiated = true;
        if (bgUp != null) {
            setBackground(bgUp);
        }
        return super.performClick();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (mOpenInitiated && hasWindowFocus) {
            mOpenInitiated = false;
            if (bgDown != null) {
                setBackground(bgDown);
            }
        }
        super.onWindowFocusChanged(hasWindowFocus);
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

}
