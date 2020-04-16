package com.zx.zxutils.views.TabViewPager;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/4/1.
 * 功能：
 */
public class ZXPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> fragmentList = new ArrayList<>();
    public List<String> fragmentTitleList = new ArrayList<>();
    public List<Drawable> normalBgList = new ArrayList<>();

    public ZXPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragmentList.clear();
        fragmentTitleList.clear();
    }

    public void addFragment(Fragment fragment, String title, Drawable normalBg) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
        normalBgList.add(normalBg);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

}
