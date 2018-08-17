package com.stanny.demo.ui.util;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.view.BtnBarView;
import com.zx.zxutils.util.ZXDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnBarView.addBtn("打开普通loaddialog")
                .addBtn("打开进度loaddialog")
                .addBtn("打开信息loaddialog")
                .addBtn("打开确定loaddialog")
                .addBtn("打开第三按钮loaddialog")
                .addBtn("打开选择列表loaddialog")
                .addBtn("打开列表loaddialog")
                .addBtn("打开自定义view的loaddialog")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://打开普通loaddialog
                ZXDialogUtil.showLoadingDialog(this, "无进度条");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ZXDialogUtil.showLoadingDialog(DialogTestActivity.this, "正在加载中", 30);
                    }
                },2000);
//                ZXDialogUtil.showLoadingDialog(this, "有进度条", 30);
                break;
            case 1://打开进度loaddialog
                int num = 1;
                if (ZXDialogUtil.isLoadingDialogShow()) {
                    ZXDialogUtil.dismissLoadingDialog();
                } else {
                    for (int i = 0; i < 10; i++) {
                        ZXDialogUtil.showLoadingDialog(DialogTestActivity.this, "正在加载中", num * 10);
                        num++;
                    }

                }
                break;
            case 2://打开信息loaddialog
                ZXDialogUtil.showInfoDialog(this, "", "确定么", null);
                break;
            case 3://打开确定loaddialog
                ZXDialogUtil.showYesNoDialog(this, "提示", "确定么", null);
                break;
            case 4://打开第三按钮loaddialog
                ZXDialogUtil.showWithOtherBtnDialog(this, "提示", "are you sure", "查看详情", null, null);
                break;
            case 5://打开选择列表loaddialog
                ZXDialogUtil.showCheckListDialog(this, "提示", new String[]{"11", "22", "33"}, new boolean[]{false, true, true}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        ZXDialogUtil.showInfoDialog(DialogTestActivity.this, "提示", "确定么", null);
                        Toast.makeText(DialogTestActivity.this, "第" + which + "个" + (isChecked ? "被选中" : "被取消"), Toast.LENGTH_SHORT).show();
                    }
                }, null);
                break;
            case 6://打开列表loaddialog
                ZXDialogUtil.showListDialog(this, "提示", "取消", new String[]{"确认", "缺不缺人", "的防辐射服", "还是会", "哈哈哈"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogTestActivity.this, "第" + which + "个", Toast.LENGTH_SHORT).show();
                    }
                }, null);
                break;
            case 7://打开自定义view的loaddialog
                ImageView iv = new ImageView(this);
                iv.setBackground(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DialogTestActivity.this, "点击viewl", Toast.LENGTH_SHORT).show();
                    }
                });
                ZXDialogUtil.showCustomViewDialog(this, "提示", iv, null);
                break;
        }
    }
}
