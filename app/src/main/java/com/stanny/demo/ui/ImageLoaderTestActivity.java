package com.stanny.demo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.stanny.demo.R;
import com.zx.zxutils.util.ZXImageLoaderUtil;
import com.zx.zxutils.util.ZXSystemUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageLoaderTestActivity extends BaseActivity {

    @BindView(R.id.iv_test_imageloader)
    ImageView ivTestImageloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test_simpleImage, R.id.btn_test_bigImage, R.id.btn_test_smallImage, R.id.btn_test_roundImage, R.id.btn_test_localImage, R.id.btn_test_fileImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_simpleImage:
                ZXImageLoaderUtil.display(ivTestImageloader, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2247692397,1189743173&fm=5");
                break;
            case R.id.btn_test_bigImage:
                ZXImageLoaderUtil.displayBigPhoto(ivTestImageloader, "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=533063898,2674893141&fm=117&gp=0.jpg");
                break;
            case R.id.btn_test_smallImage:
                ZXImageLoaderUtil.displaySmallPhoto(ivTestImageloader, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1159955992,2441657696&fm=200&gp=0.jpg");
                break;
            case R.id.btn_test_roundImage:
                ZXImageLoaderUtil.displayRound(ivTestImageloader, "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1777861864,3362426868&fm=117&gp=0.jpg");
                break;
            case R.id.btn_test_localImage:
                ZXImageLoaderUtil.display(ivTestImageloader, R.mipmap.test);
                break;
            case R.id.btn_test_fileImage:
                ZXImageLoaderUtil.display(ivTestImageloader, new File(ZXSystemUtil.getSDCardPath() + "test.png"));
                break;
        }
    }
}
