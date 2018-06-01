package com.zx.zxutils.views.BottomSheet;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.other.ZXItemClickSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2018/1/19.
 * 功能：底部弹窗
 */

public class ZXBottomSheet {

    private Context context;
    private List<SheetData> dataList;
    private View contentView;
    private String title;
    private boolean showClose = false;
    private boolean showCheckMark = false;
    private int checkIndex = -1;
    private Type type;
    private MyBottomSheet bottomSheetDialog;
    private SheetAdapter sheetAdapter;
    private OnSheetItemClickListener onSheetItemClickListener;

    public enum Type {
        LIST_TYPE,
        GRID_TYPE,
        CUSTOM_TYPE
    }

    private ZXBottomSheet(Context context, View customView) {
        this.context = context;
        this.type = Type.CUSTOM_TYPE;
        dataList = new ArrayList<>();
        contentView = customView;
    }

    private ZXBottomSheet(Context context, Type type) {
        this.context = context;
        this.type = type;
        dataList = new ArrayList<>();
        contentView = LayoutInflater.from(context).inflate(R.layout.view_bottom_sheet_list, null);
    }

    public static ZXBottomSheet initList(Context context) {
        return new ZXBottomSheet(context, Type.LIST_TYPE);
    }

    public static ZXBottomSheet initGrid(Context context) {
        return new ZXBottomSheet(context, Type.GRID_TYPE);
    }

    public static ZXBottomSheet initCustom(Context context, View customView) {
        return new ZXBottomSheet(context, customView);
    }

    public ZXBottomSheet addItem(SheetData sheetData) {
        dataList.add(sheetData);
        return this;
    }

    public ZXBottomSheet addItem(String name) {
        dataList.add(new SheetData(name));
        return this;
    }

    public ZXBottomSheet addItem(String name, String detail) {
        dataList.add(new SheetData(name, detail));
        return this;
    }

    public ZXBottomSheet addItem(String name, Drawable img) {
        dataList.add(new SheetData(name, img));
        return this;
    }

    public ZXBottomSheet addItem(String name, String detail, Drawable img) {
        dataList.add(new SheetData(name, detail, img));
        return this;
    }

    public ZXBottomSheet addItems(List<SheetData> sheetDataList) {
        dataList.addAll(sheetDataList);
        return this;
    }

    public ZXBottomSheet clearItems() {
        dataList.clear();
        return this;
    }

    public ZXBottomSheet setTitle(String title) {
        this.title = title;
        return this;
    }

    public ZXBottomSheet showCloseView(boolean showClose) {
        this.showClose = showClose;
        return this;
    }

    public ZXBottomSheet showCheckMark(boolean showCheckMark) {
        this.showCheckMark = showCheckMark;
        return this;
    }

    public ZXBottomSheet setCheckIndex(int checkIndex) {
        this.checkIndex = checkIndex;
        return this;
    }

    public ZXBottomSheet build() {
        if (bottomSheetDialog == null) {
            bottomSheetDialog = new MyBottomSheet(context);
            bottomSheetDialog.setCancelable(true);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            if (type != Type.CUSTOM_TYPE) {
                TextView tvTitle = contentView.findViewById(R.id.tv_title_sheet);
                TextView tvClose = contentView.findViewById(R.id.tv_close_sheet);
                RecyclerView rvList = contentView.findViewById(R.id.rv_sheet);
                if (title != null) {
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText(title);
                } else {
                    tvTitle.setVisibility(View.GONE);
                }
                tvClose.setVisibility(showClose ? View.VISIBLE : View.GONE);
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hide();
                    }
                });
                if (type == Type.LIST_TYPE) {
                    rvList.setLayoutManager(new LinearLayoutManager(context));
                } else if (type == Type.GRID_TYPE) {
                    rvList.setLayoutManager(new GridLayoutManager(context, 4));
                }
                if (showCheckMark && checkIndex > 0 && dataList.size() > checkIndex) {
                    dataList.get(checkIndex).setSelected(true);
                }
                sheetAdapter = new SheetAdapter(dataList, type);
                rvList.setAdapter(sheetAdapter);
                ZXItemClickSupport.addTo(rvList)
                        .setOnItemClickListener(new ZXItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                                if (onSheetItemClickListener != null) {
                                    onSheetItemClickListener.onSheetItemClick(dataList.get(position), position);
                                }
                                if (showCheckMark) {
                                    for (int i = 0; i < dataList.size(); i++) {
                                        if (i == position) {
                                            dataList.get(i).setSelected(true);
                                        } else {
                                            dataList.get(i).setSelected(false);
                                        }
                                    }
                                    sheetAdapter.notifyDataSetChanged();
                                }
                            }
                        });
            }
            bottomSheetDialog.setContentView(contentView);
        }
        return this;
    }

    public ZXBottomSheet show() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
        }
        return this;
    }

    public ZXBottomSheet setOnItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
        this.onSheetItemClickListener = onSheetItemClickListener;
        return this;
    }

    public void notifyDataSetChanged() {
        if (sheetAdapter != null) {
            sheetAdapter.notifyDataSetChanged();
        }
    }

    public ZXBottomSheet dismiss() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
        return this;
    }

    public ZXBottomSheet hide() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.hide();
        }
        return this;
    }

    public BottomSheetDialog getBottomSheetDialog() {
        return bottomSheetDialog;
    }

    public interface OnSheetItemClickListener {
        void onSheetItemClick(SheetData sheetData, int position);
    }
}
