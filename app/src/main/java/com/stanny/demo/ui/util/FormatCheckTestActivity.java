package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.func.BtnBarView;
import com.zx.zxutils.util.ZXFormatCheckUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormatCheckTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnbarView.addBtn("手机号检测：13984571000")
                .addBtn("邮件检测：872349256@qq.com")
                .addBtn("身份证检测：121849249249217894")
                .addBtn("银行卡号检测：1564891564654146")
                .addBtn("全中文检测：测试1测试")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                showCheckInfo(ZXFormatCheckUtil.isPhoneNum("13984571000"));
                break;
            case 1:
                showCheckInfo(ZXFormatCheckUtil.isEmail("872349256@qq.com"));
                break;
            case 2:
                showCheckInfo(ZXFormatCheckUtil.isIdCardNum("121849249249217894"));
                break;
            case 3:
                showCheckInfo(ZXFormatCheckUtil.isBankNum("1564891564654146"));
                break;
            case 4:
                showCheckInfo(ZXFormatCheckUtil.isAllChinese("测试1测试"));
                break;


            default:
                break;
        }
    }

    private void showCheckInfo(boolean check) {
        btnbarView.printInfo("该验证" + (check ? "成立" : "不成立"));
    }
}
