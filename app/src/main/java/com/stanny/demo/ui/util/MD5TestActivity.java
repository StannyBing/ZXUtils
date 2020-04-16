package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXMD5Util;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 *
 * Create By Xiangb On 2019/1/10
 * 功能：MD5测试
 *
 */
public class MD5TestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5_test);
        ButterKnife.bind(this);
        btnBarView.addBtn("MD5加密")
                .addBtn("base64加密")
                .addBtn("base64解密")
                .addBtn("SHA1加密")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                btnBarView.printInfo("ZXMD5Util->" + ZXMD5Util.getMD5("ZXMD5Util"));
                break;
            case 1:
                btnBarView.printInfo("ZXMD5Util->" + new String(ZXMD5Util.base64Encode("ZXMD5Util")));
                break;
            case 2:
                btnBarView.printInfo(new String(ZXMD5Util.base64Encode("ZXMD5Util")) + "->" + new String(ZXMD5Util.base64Decode(ZXMD5Util.base64Encode("ZXMD5Util"))));
                break;
            case 3:
                btnBarView.printInfo("ZXMD5Util->" + ZXMD5Util.encryptSHA1ToString("ZXMD5Util"));
                break;
            default:
                break;
        }
    }
}
