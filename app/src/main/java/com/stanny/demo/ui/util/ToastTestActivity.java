package com.stanny.demo.ui.util;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.view.BtnBarView;
import com.zx.zxutils.util.ZXToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToastTestActivity extends BaseActivity {


    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @BindView(R.id.coorContent)
    CoordinatorLayout coorContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_test);
        ButterKnife.bind(this);

        btnBarView
                .addBtn("简单Toast")
                .addBtn("图片Toast")
                .addBtn("Snackbar")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://简单Toast
                ZXToastUtil.showToast("123456");
                break;
            case 1://图片Toast
                ZXToastUtil.showImgToast("图片toast", ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
                break;
            case 2://Snackbar
                ZXToastUtil.showSnackBar(coorContent, "snackbar", "do", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ZXToastUtil.showToast("123");
                    }
                });
                break;
        }
    }
}
