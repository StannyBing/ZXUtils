package com.stanny.demo.ui.widget;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.BottomSheet.SheetData;
import com.zx.zxutils.views.BottomSheet.ZXBottomSheet;

public class BottomSheetActivity extends BaseActivity {

    private ZXBottomSheet lsitSheet, gridSheet, customSheet;

    private BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        btnBarView = findViewById(R.id.btnbar_view);
        btnBarView.addBtn("简单列表")
                .addBtn("Grid查看")
                .addBtn("自定义")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                if (lsitSheet == null) {
                    lsitSheet = ZXBottomSheet.initList(this)
                            .addItem("名名字字", "详名字情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名详情字", "详名字情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字名字", "详情名字详情详情详情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字详情", "详情详情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字名字", "详情名字", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名名字字", "详详情情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字名名字详情字", "详名字情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字详情", "详情详情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字", "详情详情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
//                        .setTitle("标题")
                            .showCloseView(true)
                            .setOnItemClickListener(new ZXBottomSheet.OnSheetItemClickListener() {
                                @Override
                                public void onSheetItemClick(SheetData sheetData, int position) {
                                    ZXToastUtil.showToast("点击了：" + position + "," + sheetData.getName());
                                }
                            })
                            .build();
                }
                lsitSheet.show();
                break;
            case 1:
                if (gridSheet == null) {
                    gridSheet = ZXBottomSheet.initGrid(this)
                            .addItem("名名字字", "详名字情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名详情字", "详名字情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字名字", "详情名字详情详情详情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字详情", "详情详情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字名字", "详情名字", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名名字字", "详详情情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字名名字详情字", "详名字情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字详情", "详情详情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
                            .addItem("名字", "详情详情", ContextCompat.getDrawable(this, R.mipmap.ic_launcher))
//                        .setTitle("标题")
                            .showCloseView(true)
                            .showCheckMark(true)
                            .build();
                }
                gridSheet.show();
                break;
            case 2:
                if (customSheet == null) {
                    View view1 = LayoutInflater.from(this).inflate(R.layout.menu_left_drawer, null, false);
                    customSheet = ZXBottomSheet.initCustom(this, view1).build();
                }
                customSheet.show();
                break;
            default:
                break;
        }
    }
}
