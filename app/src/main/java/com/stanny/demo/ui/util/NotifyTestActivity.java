package com.stanny.demo.ui.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.view.BtnBarView;
import com.zx.zxutils.util.ZXNotifyUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotifyTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnBarView
                .addBtn("基本notify")
                .addBtn("多条notify")
                .addBtn("大图notify")
                .addBtn("进度notify")
                .addBtn("自定义Notify")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://基本notify
                Intent intents = new Intent(this, NotifyTestActivity.class);
                ZXNotifyUtil.showSingleLineNotify(intents, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
                break;
            case 1://多条notify
                Intent intent = new Intent(this, NotifyTestActivity.class);
                ZXNotifyUtil.showMulLineNotify(intent, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
                break;
            case 2://大图notify
                Intent intentbi = new Intent(this, NotifyTestActivity.class);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                ZXNotifyUtil.showBigImgNotify(intentbi, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容", bitmap);
                break;
            case 3://进度notify
                Intent intentp = new Intent(this, NotifyTestActivity.class);
                ZXNotifyUtil.showProgressNotify(intentp, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容", 60);
                break;
            case 4://自定义Notify
                Intent intentc = new Intent(this, NotifyTestActivity.class);
                RemoteViews remoteViews = new RemoteViews(getPackageName(),
                        R.layout.layout_custom);
                remoteViews.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
                remoteViews.setTextViewText(R.id.title, "垃圾安装包太多");
                remoteViews.setTextViewText(R.id.text, "3 个无用安装包，清理释放的空间");
                ZXNotifyUtil.showCustomNotify(intentc, remoteViews);
                break;
        }
    }
}
