package com.stanny.demo.ui;

import android.content.Intent;
import android.os.Bundle;

import com.stanny.demo.R;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.PhotoPicker.listener.OnDeleteListener;
import com.zx.zxutils.views.PhotoPicker.widget.ZXPhotoPickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.stanny.demo.R.id.mprv_photo1;

public class PhotoPickerTestActivity extends BaseActivity {

    @BindView(mprv_photo1)
    ZXPhotoPickerView mprvPhoto1;
    @BindView(R.id.mprv_photo2)
    ZXPhotoPickerView mprvPhoto2;
    @BindView(R.id.mprv_photo3)
    ZXPhotoPickerView mprvPhoto3;

    private ArrayList<String> photoList1 = new ArrayList<>();
    private ArrayList<String> photoList2 = new ArrayList<>();
    private ArrayList<String> photoList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker_test);
        ButterKnife.bind(this);

        photoList1.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3563933861,850005699&fm=117&gp=0.jpg");
        photoList1.add("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=40913571,471421714&fm=58");
        photoList1.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1735278598,1624226652&fm=117&gp=0.jpg");
        photoList1.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=468291096,4071036468&fm=117&gp=0.jpg");
        photoList1.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3390905199,613944351&fm=117&gp=0.jpg");
        photoList1.add("http://img.blog.csdn.net/20170804175543366?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvU3Rhbm55X0Jpbmc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center");

        photoList2.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1735278598,1624226652&fm=117&gp=0.jpg");
        photoList2.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=468291096,4071036468&fm=117&gp=0.jpg");
        photoList2.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3390905199,613944351&fm=117&gp=0.jpg");

        photoList3.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=468291096,4071036468&fm=117&gp=0.jpg");
        photoList3.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3390905199,613944351&fm=117&gp=0.jpg");

        mprvPhoto1.init(this, ZXPhotoPickerView.ACTION_ONLY_SHOW, photoList1);

        mprvPhoto2.init(this, ZXPhotoPickerView.ACTION_SELECT, photoList2);

        mprvPhoto3.init(this, ZXPhotoPickerView.ACTION_DELETE, photoList3, new OnDeleteListener() {
            @Override
            public void OnDetele(int positon) {
                ZXToastUtil.showToast("删除了" + photoList3.get(positon));
                photoList3.remove(positon);
                mprvPhoto3.photoAdapter.refresh(photoList3, false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mprvPhoto1.onActivityResult(requestCode, resultCode, data);
//        mprvPhoto2.onActivityResult(requestCode, resultCode, data);
//        mprvPhoto3.onActivityResult(requestCode, resultCode, data);
    }
}
