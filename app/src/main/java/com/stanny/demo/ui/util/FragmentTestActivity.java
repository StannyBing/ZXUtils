package com.stanny.demo.ui.util;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.stanny.demo.R;
import com.stanny.demo.func.BtnBarView;
import com.stanny.demo.func.TestFragment;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.util.ZXFragmentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    private TestFragment fragment1, fragment2, fragment3, fragment4, fragment5, fragment6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        ButterKnife.bind(this);
        btnBarView
                .addBtn("新增fragment")
                .addBtn("新增多个fragment到一个framelayout(只展示某一个)")
                .addBtn("移除fragment")
                .addBtn("替换fragment")
                .addBtn("fragment出栈")
                .addBtn("隐藏fragment")
                .addBtn("显示fragment")
                .setItemClickListener(this)
                .build();
        fragment1 = TestFragment.newInstance(R.color.bisque, "fragment1");
        fragment2 = TestFragment.newInstance(R.color.chocolate, "fragment2");
        fragment3 = TestFragment.newInstance(R.color.purple, "fragment3");
        fragment4 = TestFragment.newInstance(R.color.teal, "fragment4");
        fragment5 = TestFragment.newInstance(R.color.darkkhaki, "fragment5");
        fragment6 = TestFragment.newInstance(R.color.green, "fragment6");
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                if (!fragment1.isAdded()) {
                    ZXFragmentUtil.addFragment(getSupportFragmentManager(), fragment1, R.id.fl_fragment_1);
                }
                if (!fragment2.isAdded()) {
                    ZXFragmentUtil.addFragment(getSupportFragmentManager(), fragment2, R.id.fl_fragment_2);
                }
                if (!fragment3.isAdded()) {
                    ZXFragmentUtil.addFragment(getSupportFragmentManager(), fragment3, R.id.fl_fragment_3);
                }
                break;
            case 1:
                List<Fragment> fragments = new ArrayList<>();
                if (!fragment4.isAdded()) {
                    fragments.add(fragment4);
                }
                if (!fragment5.isAdded()) {
                    fragments.add(fragment5);
                }
                if (fragments.size() > 0) {
                    ZXFragmentUtil.addFragments(getSupportFragmentManager(), fragments, R.id.fl_fragment_4, 1);
                }
                break;
            case 2:
                if (fragment2.isAdded()) {
                    ZXFragmentUtil.removeFragment(fragment2);
                }
                break;
            case 3:
                if (fragment1.isAdded()) {
                    ZXFragmentUtil.replaceFragment(fragment1, fragment6, true);
                }
                break;
            case 4:
                ZXFragmentUtil.popFragment(getSupportFragmentManager());
                break;
            case 5:
                if (fragment3.isAdded()) {
                    ZXFragmentUtil.hideFragment(fragment3);
                }
                break;
            case 6:
                if (fragment3.isAdded()) {
                    ZXFragmentUtil.showFragment(fragment3);
                }
                break;

            default:
                break;
        }
    }
}
