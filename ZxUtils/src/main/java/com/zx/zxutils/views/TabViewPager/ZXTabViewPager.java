package com.zx.zxutils.views.TabViewPager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.zx.zxutils.R;

import java.util.ArrayList;
import java.util.List;

import static com.zx.zxutils.views.TabViewPager.ZXTabViewPager.TabGravity.GRAVITY_BOTTOM;
import static com.zx.zxutils.views.TabViewPager.ZXTabViewPager.TabGravity.GRAVITY_TOP;

/**
 * Created by Xiangb on 2017/4/1.
 * 功能：tablayout自定义view
 */
public class ZXTabViewPager extends RelativeLayout {
    public ZXPagerAdapter myPagerAdapter;
    private TabLayout tabLayoutTop, tabLayoutBottom, tabLayout;
    private ViewPager viewPager;
//    public static final int GRAVITY_TOP = 0;
//    public static final int GRAVITY_BOTTOM = 1;
    private List<Integer> iconList = new ArrayList<>();

    public enum TabGravity {
        GRAVITY_BOTTOM, GRAVITY_TOP
    }

    public ZXTabViewPager(Context context) {
        super(context, null);
    }

    public ZXTabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        //在构造函数中将xml中定义的布局解析出来
        LayoutInflater.from(context).inflate(R.layout.view_tab_viewpager_layout, this, true);
        tabLayoutTop = (TabLayout) findViewById(R.id._tb_zx_layoutTop);
        tabLayoutBottom = (TabLayout) findViewById(R.id._tb_zx_layoutBottom);
        viewPager = (ViewPager) findViewById(R.id._vp_zx_pager);
        iconList.clear();
        tabLayout = tabLayoutTop;
    }

    /**
     * 设置FragmentManager
     * 如果在Activity中使用，传入getSupportFragmentManager
     * 如果在fragment中使用，传入getChildFragmentManager
     *
     * @param manager
     * @return
     */
    public ZXTabViewPager setManager(FragmentManager manager) {
        myPagerAdapter = new ZXPagerAdapter(manager);
        return this;
    }

    /**
     * 添加tab
     *
     * @param fragment
     * @param title
     * @return
     */
    public ZXTabViewPager addTab(Fragment fragment, String title) {
        myPagerAdapter.addFragment(fragment, title);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return this;
    }

    /**
     * 设置tablaout的位置，注意必须要在最开始就进行设置，默认在上方
     *
     * @param gravity
     * @return
     */
    public ZXTabViewPager setTabLayoutGravity(TabGravity gravity) {
        if (gravity == GRAVITY_TOP) {
            tabLayout = tabLayoutTop;
            tabLayoutTop.setVisibility(VISIBLE);
            tabLayoutBottom.setVisibility(GONE);
        } else if (gravity == GRAVITY_BOTTOM) {
            tabLayout = tabLayoutBottom;
            tabLayoutBottom.setVisibility(VISIBLE);
            tabLayoutTop.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 获取到tablayout，便于进一步设置
     *
     * @return
     */
    public TabLayout getTabLayout() {
        return tabLayout;
    }

    /**
     * 获取到viewpager，便于进一步设置
     *
     * @return
     */
    public ViewPager getViewPager() {
        return viewPager;
    }

    /**
     * 设置tab的颜色
     * 注意：虽然传入参数为int类型，但是不能传入R.color.write这种R路径
     * 需要ContextCompat.getColor(),或者getResources().getColor()
     *
     * @param normalColor   正常字体颜色
     * @param selectedColor 选中字体颜色
     * @return
     */
    public ZXTabViewPager setTitleColor(int normalColor, int selectedColor) {
        tabLayout.setTabTextColors(normalColor, selectedColor);
        return this;
    }

    /**
     * 动态设置tab的文字
     *
     * @param positon 位置
     * @param tabText 文字
     */
    public void setTabText(int positon, String tabText) {
        tabLayout.getTabAt(positon).setText(tabText);
    }

    /**
     * 设置tab选中项下划线的颜色
     *
     * @param indicatorColor 颜色值
     * @return
     */
    public ZXTabViewPager setIndicatorColor(int indicatorColor) {
        tabLayout.setSelectedTabIndicatorColor(indicatorColor);
        return this;
    }

    /**
     * 设置tab选中项下划线的高度（px）
     *
     * @param indicatorHeightPx 高度像素值
     * @return
     */
    public ZXTabViewPager setIndicatorHeight(int indicatorHeightPx) {
        tabLayout.setSelectedTabIndicatorHeight(indicatorHeightPx);
        return this;
    }

    /**
     * 设置当前选中界面
     *
     * @param position
     * @return
     */
    public ZXTabViewPager setSelectOn(int position) {
        if (position < 0) {
            viewPager.setCurrentItem(0);
        } else if (position > tabLayout.getTabCount() - 1) {
            viewPager.setCurrentItem(tabLayout.getTabCount() - 1);
        } else {
            viewPager.setCurrentItem(position);
        }
        return this;
    }

    /**
     * 获取当前选中的position
     *
     * @return
     */
    public int getSelectedPosition() {
        return tabLayout.getSelectedTabPosition();
    }

    /**
     * tablayout是否可滑动，默认可滑动
     * boolean scrollable 是否可滑动
     *
     * @return
     */
    public ZXTabViewPager setTabScrollable(boolean scrollable) {
        if (scrollable) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        return this;
    }

    /**
     * 获取该position对应的fragment
     *
     * @param position
     * @return
     */
    public Fragment getFragment(int position) {
        return myPagerAdapter.getItem(position);
    }


}
