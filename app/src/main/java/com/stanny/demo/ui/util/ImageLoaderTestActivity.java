package com.stanny.demo.ui.util;

import android.os.Bundle;
import android.widget.ImageView;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXImageLoaderUtil;
import com.zx.zxutils.util.ZXSystemUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageLoaderTestActivity extends BaseActivity {

    @BindView(R.id.iv_test_imageloader)
    ImageView ivTestImageloader;
    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_test);
        ButterKnife.bind(this);

        btnBarView
                .addBtn("一般")
                .addBtn("大图")
                .addBtn("小图")
                .addBtn("圆图")
                .addBtn("资源")
                .addBtn("文件")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://一般
                ZXImageLoaderUtil.display(ivTestImageloader, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2247692397,1189743173&fm=5");
                break;
            case 1://大图
                ZXImageLoaderUtil.displayBigPhoto(ivTestImageloader, "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=533063898,2674893141&fm=117&gp=0.jpg");
                break;
            case 2://小图
                ZXImageLoaderUtil.displaySmallPhoto(ivTestImageloader, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1159955992,2441657696&fm=200&gp=0.jpg");
                break;
            case 3://圆图
                ZXImageLoaderUtil.displayRound(ivTestImageloader, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1777861864,3362426868&fm=117&gp=0.jpg");
                break;
            case 4://资源
                ZXImageLoaderUtil.display(ivTestImageloader, R.mipmap.test);
                break;
            case 5://文件
                ZXImageLoaderUtil.display(ivTestImageloader, new File(ZXSystemUtil.getSDCardPath() + "test.png"));
                break;
        }
    }
}
