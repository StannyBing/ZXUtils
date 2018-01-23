package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.view.BtnBarView;
import com.zx.zxutils.util.ZXTimeUtil;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnbarView
                .addBtn("获取当前时间，格式默认为yyyy-MM-dd HH:mm:ss")
                .addBtn("获取当前时间，格式需要传入")
                .addBtn("转换成日期描述，如三周前，上午，昨天等")
                .addBtn("日期变量转成对应的星期字符串")
                .addBtn("返回两个时间的时间差")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://获取当前时间，格式默认为yyyy-MM-dd HH:mm:ss
                btnbarView.printInfo(ZXTimeUtil.getCurrentTime());
                break;
            case 1://获取当前时间，格式需要传入
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                btnbarView.printInfo(ZXTimeUtil.getCurrentTime(simpleDateFormat));
                break;
            case 2://转换成日期描述，如三周前，上午，昨天等
                btnbarView.printInfo(ZXTimeUtil.getTimeDesc(System.currentTimeMillis() - 15649849));
                break;
            case 3://日期变量转成对应的星期字符串
                btnbarView.printInfo(ZXTimeUtil.dateToWeek(System.currentTimeMillis()));
                break;
            case 4://返回两个时间的时间差
                btnbarView.printInfo(ZXTimeUtil.getTimeDifference(System.currentTimeMillis(), System.currentTimeMillis() + 565648464));
                break;

            default:
                break;
        }
    }
}
