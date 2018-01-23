package com.stanny.demo.ui.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.view.BtnBarView;
import com.zx.zxutils.util.ZXBitmapUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BitmapTestActivity extends BaseActivity {

    @BindView(R.id.iv_test_bitmap)
    ImageView ivTestBitmap;
    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    Drawable drawable;
    Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_test);
        ButterKnife.bind(this);
        btnBarView.addBtn("圆形")
                .addBtn("水印")
                .addBtn("黑白")
                .addBtn("圆角")
                .addBtn("灰度")
                .addBtn("合并")
                .addBtn("旋转")
                .setItemClickListener(this)
                .build();
    }


    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://圆形
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getCircleBitmap(bit));
                break;
            case 1://水印
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                Drawable drawable1 = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                Bitmap bit1 = ZXBitmapUtil.drawableToBitmap(drawable1);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getMarkBitmap(bit, bit1));
                break;
            case 2://黑白
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getBlackWhiteBitmap(bit));
                break;
            case 3://圆角
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getRoundBitmap(bit, 50));
                break;
            case 4://灰度
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.getGreyBitmap(bit));
                break;
            case 5://合并
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                Drawable drawable2 = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                Bitmap bit2 = ZXBitmapUtil.drawableToBitmap(drawable2);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.combineBitmap(bit, bit2));
                break;
            case 6://旋转
                drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                bit = ZXBitmapUtil.drawableToBitmap(drawable);
                ivTestBitmap.setImageBitmap(ZXBitmapUtil.rotateBitmap(bit, 70));
                break;
            default:
                break;
        }
    }
}
