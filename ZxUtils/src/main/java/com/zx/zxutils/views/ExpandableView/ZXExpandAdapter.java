package com.zx.zxutils.views.ExpandableView;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.util.ZXSystemUtil;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/4.
 * 功能：
 */

public class ZXExpandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ZXExpandBean> showList;
    private Context context;
    private boolean showSelect = false;
    private boolean isMultiSelected = true;
    public MyHolder mLastViewTag = null;
    private boolean menuCanSelect = true;
    private OnExpandListener expandListener;
    private int textSizeSp = 14;
    private int heightDp = 40;

    public ZXExpandAdapter(Context context, List<ZXExpandBean> showList, boolean showSelect, boolean isMultiSelected, int textSizeSp, int heightDp, boolean menuCanSelect) {
        this.context = context;
        this.showList = showList;
        this.showSelect = showSelect;
        this.isMultiSelected = isMultiSelected;
        this.textSizeSp = textSizeSp;
        this.heightDp = heightDp;
        this.menuCanSelect = menuCanSelect;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expand_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ZXExpandBean expandBean = showList.get(position);
        myHolder.tvInfo.setText(expandBean.getItemText());
        ViewGroup.LayoutParams params = myHolder.viewIndex.getLayoutParams();
        params.width = ZXSystemUtil.dp2px(30 * expandBean.getIndex());
        myHolder.viewIndex.setLayoutParams(params);
        if (expandBean.getChildList() == null) {
            myHolder.ivArrow.setVisibility(View.INVISIBLE);
        } else if (expandBean.isShowChild()) {
            myHolder.ivArrow.setVisibility(View.VISIBLE);
            myHolder.ivArrow.setBackground(ContextCompat.getDrawable(context, R.mipmap.arrow_open));
        } else {
            myHolder.ivArrow.setVisibility(View.VISIBLE);
            myHolder.ivArrow.setBackground(ContextCompat.getDrawable(context, R.mipmap.arrow_close));
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) myHolder.down.getLayoutParams();
        layoutParams.height = ZXSystemUtil.dp2px(heightDp);
        myHolder.down.setLayoutParams(layoutParams);
        myHolder.tvInfo.setTextSize(textSizeSp);
        if (showSelect) {
            if (expandBean.isSelected()) {
                myHolder.ivSelect.setVisibility(View.VISIBLE);
                myHolder.ivSelect.setBackground(ContextCompat.getDrawable(context, R.mipmap.select));
            } else {
                myHolder.ivSelect.setVisibility(View.VISIBLE);
                myHolder.ivSelect.setBackground(ContextCompat.getDrawable(context, R.mipmap.not_select));
            }
            if (expandBean.getChildList() != null && !menuCanSelect) {
                myHolder.ivSelect.setVisibility(View.INVISIBLE);
            } else {
                myHolder.ivSelect.setVisibility(View.VISIBLE);
            }
        } else {
            myHolder.ivSelect.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvInfo;
        private View viewIndex;
        private ImageView ivArrow, ivSelect;
        private LinearLayout down, llArrow, llSelect;

        public MyHolder(View itemView) {
            super(itemView);
            tvInfo = itemView.findViewById(R.id.tv_info);
            viewIndex = itemView.findViewById(R.id.view_index);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            ivSelect = itemView.findViewById(R.id.iv_select);
            down = itemView.findViewById(R.id.down);
            llArrow = itemView.findViewById(R.id.ll_arrow);
            llSelect = itemView.findViewById(R.id.ll_select);
            llArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandListener != null) {
                        expandListener.onOpenClick(getAdapterPosition());
                    }
                }
            });
            llSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandListener != null && ivSelect.getVisibility() == View.VISIBLE) {
                        expandListener.onSelectClick(getAdapterPosition());
                    }
                }
            });
            tvInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandListener != null) {
                        expandListener.onTextClick(getAdapterPosition());
                    }
                }
            });
        }
    }


    public void setExpandListener(OnExpandListener expandListener) {
        this.expandListener = expandListener;
    }

    public interface OnExpandListener {
        void onTextClick(int position);

        void onOpenClick(int position);

        void onSelectClick(int position);
    }

}
