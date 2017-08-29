package com.stannytestobject.activity.zxutilstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stannytestobject.R;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.views.BubSeekBar.ZXSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeekBarTestActivity extends AppCompatActivity {

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
        sbTest1.setRange(0, 50)//设置范围
                .setProgress(35)//设置当前刻度
                .setSectionMark(10, true)//设置分段
                .setTrackColor(R.color.violet, R.color.__picker_item_selected_cover)//设置选择和未选的颜色
                .setText(10, R.color.tan, 0);//设置刻度值的字体

        sbTest2.setRange(30, 100)//设置范围
                .setProgress(50)//设置当前刻度
                .setSectionMark(5, true)//设置分段
                .setTrackColor(R.color.tan, R.color.steelblue)//设置选择和未选的颜色
                .setText(10, R.color.tan, ZXSeekBar.TextPosition.BOTTOM_SIDES);//设置刻度值的字体

        sbTest3.setRange(0, 50)//设置范围
                .setProgress(35)//设置当前刻度
                .setSectionMark(10, false)//设置分段
                .setTrackColor(R.color.colorPrimaryDark, R.color.__picker_text_120)//设置选择和未选的颜色
                .setText(10, R.color.tan, ZXSeekBar.TextPosition.SIDES)
                .setOnProgressChangedListener(new ZXSeekBar.OnProgressChangedListener() {
                    @Override
                    public void onProgressChanged(int progress, float progressFloat) {
                        ZXToastUtil.showToast(progress + "");
                    }

                    @Override
                    public void getProgressOnActionUp(int progress, float progressFloat) {

                    }

                    @Override
                    public void getProgressOnFinally(int progress, float progressFloat) {

                    }
                });//设置刻度值的字体

        sbTest4.setRange(0, 50)//设置范围
                .setProgress(35)//设置当前刻度
                .setSectionMark(10, true)//设置分段
                .setTrackColor(R.color.violet, R.color.__picker_item_selected_cover)//设置选择和未选的颜色
                .setText(20, R.color.__picker_pager_bg, ZXSeekBar.TextPosition.BELOW_SECTION_MARK);//设置刻度值的字体
    }
}
