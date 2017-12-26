package com.stanny.demo.ui.util;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.util.ZXDialogUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test_simpleDialog,
            R.id.btn_test_progressDialog,
            R.id.btn_test_infoDialog,
            R.id.btn_test_yesornoDialog,
            R.id.btn_test_otherDialog,
            R.id.btn_test_checkDialog,
            R.id.btn_test_listDialog,
            R.id.btn_test_viewDialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_simpleDialog:
                ZXDialogUtil.showLoadingDialog(this, "无进度条");
//                ZXDialogUtil.showLoadingDialog(this, "有进度条", 30);
                break;
            case R.id.btn_test_progressDialog:
                int num = 10;
                if (ZXDialogUtil.isLoadingDialogShow()) {
                    ZXDialogUtil.dismissLoadingDialog();
                } else {
                    for (int i = 0; i < 10; i++) {
                        ZXDialogUtil.showLoadingDialog(DialogTestActivity.this, "正在加载中", num * 10);
                        num++;
                    }

                }
                break;
            case R.id.btn_test_infoDialog:
                ZXDialogUtil.showInfoDialog(this, "提示", "确定么", null);
                break;
            case R.id.btn_test_yesornoDialog:
                ZXDialogUtil.showYesNoDialog(this, "提示", "确定么", null);
                break;
            case R.id.btn_test_otherDialog:
                ZXDialogUtil.showWithOtherBtnDialog(this, "提示", "are you sure", "查看详情", null, null);
                break;
            case R.id.btn_test_checkDialog:
                ZXDialogUtil.showCheckListDialog(this, "提示", new String[]{"11", "22", "33"}, new boolean[]{false, true, true}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        ZXDialogUtil.showInfoDialog(DialogTestActivity.this, "提示", "确定么", null);
                        Toast.makeText(DialogTestActivity.this, "第" + which + "个" + (isChecked ? "被选中" : "被取消"), Toast.LENGTH_SHORT).show();
                    }
                }, null);
                break;
            case R.id.btn_test_listDialog:
                ZXDialogUtil.showListDialog(this, "提示", "取消", new String[]{"确认", "缺不缺人", "的防辐射服", "还是会", "哈哈哈"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogTestActivity.this, "第" + which + "个", Toast.LENGTH_SHORT).show();
                    }
                }, null);
                break;
            case R.id.btn_test_viewDialog:
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
