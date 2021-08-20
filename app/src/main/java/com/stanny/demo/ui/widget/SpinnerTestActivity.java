package com.stanny.demo.ui.widget;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.entity.KeyValueEntity;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.ZXSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpinnerTestActivity extends BaseActivity {

    @BindView(R.id.sp_test_spinner)
    ZXSpinner spTestSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_test);
        ButterKnife.bind(this);

        //下拉框spinner
        List<KeyValueEntity> datalist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datalist.add(new KeyValueEntity("测试" + i, "测试" + i));
        }
        spTestSpinner.setData(datalist)
                .showSeletedLayoutColor(true, R.color.skyblue)
                .showSelectedTextColor(true, R.color.error_color)
                .showDivider(true, ContextCompat.getColor(this, R.color.colorPrimary))
                .setItemHeightDp(30)
                .setItemTextSizeSp(8)
                .setDefaultItem("请选择...")
                .build();

        spTestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ZXToastUtil.showToast(spTestSpinner.getSelectedValue().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
