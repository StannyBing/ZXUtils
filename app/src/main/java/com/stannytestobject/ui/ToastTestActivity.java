package com.stannytestobject.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.stannytestobject.R;
import com.stannytestobject.ui.BaseActivity;
import com.zx.zxutils.util.ZXToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToastTestActivity extends BaseActivity {

    @BindView(R.id.coorContent)
    CoordinatorLayout coorContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test_simpleToast, R.id.btn_test_imgToast, R.id.btn_test_SnackBar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_simpleToast:
                ZXToastUtil.showToast("123456");
                break;
            case R.id.btn_test_imgToast:
                ZXToastUtil.showImgToast("图片toast", ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
                break;
            case R.id.btn_test_SnackBar:
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
