package com.stanny.demo.ui.widget;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.BubSeekBar.ZXSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeekBarTestActivity extends BaseActivity {

    @BindView(R.id.sb_test_1)
    ZXSeekBar sbTest1;
    @BindView(R.id.sb_test_2)
    ZXSeekBar sbTest2;
    @BindView(R.id.sb_test_3)
    ZXSeekBar sbTest3;
    @BindView(R.id.sb_test_4)
    ZXSeekBar sbTest4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar_test);
        ButterKnife.bind(this);
        sbTest1.getConfigBuilder()
                .min(0)
                .max(50)//设置范围
                .progress(35)//设置当前刻度
                .sectionCount(10)//设置分段
                .trackColor(ContextCompat.getColor(this, R.color.violet))//设置选择和未选的颜色
                .secondTrackColor(ContextCompat.getColor(this, R.color.__picker_item_selected_cover))
                .bubbleTextSize(10)
                .bubbleTextColor(ContextCompat.getColor(this, R.color.tan))
                .sectionTextPosition(ZXSeekBar.TextPosition.SIDES)
                .build();

        sbTest2.getConfigBuilder()
                .min(30)
                .max(100)//设置范围
                .progress(50)//设置当前刻度
                .sectionCount(5)//设置分段
                .trackColor(ContextCompat.getColor(this, R.color.tan))//设置选择和未选的颜色
                .secondTrackColor(ContextCompat.getColor(this, R.color.steelblue))
                .bubbleTextSize(10)
                .bubbleTextColor(ContextCompat.getColor(this, R.color.tan))
                .sectionTextPosition(ZXSeekBar.TextPosition.BOTTOM_SIDES)
                .build();

        sbTest3.getConfigBuilder()
                .min(0)
                .max(50)//设置范围
                .progress(35)//设置当前刻度
                .sectionCount(5)//设置分段
                .trackColor(ContextCompat.getColor(this, R.color.tan))//设置选择和未选的颜色
                .secondTrackColor(ContextCompat.getColor(this, R.color.steelblue))
                .bubbleTextSize(10)
                .bubbleTextColor(ContextCompat.getColor(this, R.color.tan))
                .sectionTextPosition(ZXSeekBar.TextPosition.BOTTOM_SIDES)
                .build();
        sbTest3.setOnProgressChangedListener(new ZXSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(ZXSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                ZXToastUtil.showToast(progress + "");
            }

            @Override
            public void getProgressOnActionUp(ZXSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(ZXSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });//设置刻度值的字体

        sbTest4.getConfigBuilder()
                .min(0)
                .max(50)//设置范围
                .progress(35)//设置当前刻度
                .sectionCount(5)//设置分段
                .trackColor(ContextCompat.getColor(this, R.color.tan))//设置选择和未选的颜色
                .secondTrackColor(ContextCompat.getColor(this, R.color.steelblue))
                .bubbleTextSize(10)
                .bubbleTextColor(ContextCompat.getColor(this, R.color.tan))
                .sectionTextPosition(ZXSeekBar.TextPosition.BOTTOM_SIDES)
                .build();
    }
}
