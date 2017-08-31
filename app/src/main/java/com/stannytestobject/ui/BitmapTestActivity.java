package com.stannytestobject.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.stannytestobject.R;
import com.stannytestobject.ui.BaseActivity;
import com.zx.zxutils.util.ZXBitmapUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BitmapTestActivity extends BaseActivity {

    @BindView(R.id.iv_test_bitmap)
    ImageView ivTestBitmap;

    Drawable drawable;
    Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test_circleBitmap, R.id.btn_test_markBitmap, R.id.btn_test_blackBitmap, R.id.btn_test_roundBitmap, R.id.btn_test_greyBitmap, R.id.btn_test_conbineBitmap, R.id.btn_test_rotateBitmap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_circleBitmap:
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getCircleBitmap(bit));
                break;
            case R.id.btn_test_markBitmap:
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                Drawable drawable1 = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                Bitmap bit1 = ZXBitmapUtil.drawableToBitmap(drawable1);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getMarkBitmap(bit, bit1));
                break;
            case R.id.btn_test_blackBitmap:
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getBlackWhiteBitmap(bit));
                break;
            case R.id.btn_test_roundBitmap:
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getRoundBitmap(bit, 50));
                break;
            case R.id.btn_test_greyBitmap:
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getGreyBitmap(bit));
                break;
            case R.id.btn_test_conbineBitmap:
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                Drawable drawable2 = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                Bitmap bit2 = ZXBitmapUtil.drawableToBitmap(drawable2);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.combineBitmap(bit, bit2));
                break;
            case R.id.btn_test_rotateBitmap:
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.rotateBitmap(bit, 70));
                break;
            default:
                break;
        }
    }
}
