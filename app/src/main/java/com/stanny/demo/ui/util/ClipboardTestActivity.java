package com.stanny.demo.ui.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXClipboardUtil;
import com.zx.zxutils.util.ZXTimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClipboardTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;
    @BindView(R.id.et_clipboard_test)
    EditText etClipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipboard_test);
        ButterKnife.bind(this);
        btnBarView.addBtn("获取剪贴板的文字")
                .addBtn("复制文本到剪贴板")
                .addBtn("复制Uri到剪贴板")
                .addBtn("获取剪贴板的Uri")
                .addBtn("复制Intent到剪贴板")
                .addBtn("获取剪贴板的Intent")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                btnBarView.printInfo(ZXClipboardUtil.getText());
                break;
            case 1:
                ZXClipboardUtil.copyText("复制文本：" + ZXTimeUtil.getCurrentTime());
                btnBarView.printInfo("复制成功，请尝试粘贴");
                break;
            case 2:
                ZXClipboardUtil.copyUri(Uri.parse("www.baidu.com"));
                btnBarView.printInfo("已复制内容为：" + "www.baidu.com" + "的Uri");
                break;
            case 3:
                btnBarView.printInfo("获取到内容为：" + ZXClipboardUtil.getUri().getPath() + "的Uri");
                break;
            case 4:
                Intent intent = new Intent();
                intent.putExtra("test", "intent测试");
                ZXClipboardUtil.copyIntent(intent);
                btnBarView.printInfo("已复制内容为：" + intent.getStringExtra("test") + "的Intent");
                break;
            case 5:
                btnBarView.printInfo("获取到内容为：" + ZXClipboardUtil.getIntent().getStringExtra("test") + "的Intent");
                break;
            default:
                break;
        }
    }
}
