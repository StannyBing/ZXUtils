package com.stannytestobject.activity.zxutilstest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.stannytestobject.R;
import com.zx.zxutils.util.ZXNotifyUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotifyTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test_simpleNotify, R.id.btn_test_mulNotify, R.id.btn_test_imageNotify, R.id.btn_test_progressNotify, R.id.btn_test_selfNotify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_simpleNotify:
                Intent intents = new Intent(this, NotifyTestActivity.class);
                ZXNotifyUtil.showSingleLineNotify(intents, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
                break;
            case R.id.btn_test_mulNotify:
                Intent intent = new Intent(this, NotifyTestActivity.class);
                ZXNotifyUtil.showMulLineNotify(intent, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
                break;
            case R.id.btn_test_imageNotify:
                Intent intentbi = new Intent(this, NotifyTestActivity.class);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                ZXNotifyUtil.showBigImgNotify(intentbi, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容", bitmap);
                break;
            case R.id.btn_test_progressNotify:
                Intent intentp = new Intent(this, NotifyTestActivity.class);
                ZXNotifyUtil.showProgressNotify(intentp, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容", 60);
                break;
            case R.id.btn_test_selfNotify:
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
