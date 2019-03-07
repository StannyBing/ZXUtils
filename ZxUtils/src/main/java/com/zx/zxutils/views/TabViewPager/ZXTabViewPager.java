package com.zx.zxutils.views.TabViewPager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.util.ZXSystemUtil;
import com.zx.zxutils.views.ZXNoScrllViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.zx.zxutils.views.TabViewPager.ZXTabViewPager.TabGravity.GRAVITY_BOTTOM;
import static com.zx.zxutils.views.TabViewPager.ZXTabViewPager.TabGravity.GRAVITY_TOP;

/**
 * Created by Xiangb on 2017/4/1.
 * 功能：tablayout自定义view
 */
public class ZXTabViewPager extends RelativeLayout {
    private ZXPagerAdapter myPagerAdapter;
    private TabLayout tabLayoutTop, tabLayoutBottom, tabLayout;
    private TextView tvDividerTop, tvDividerBottom, tvDivider;
    private ZXNoScrllViewPager viewPager;
    private Context context;
    private int normalTextColor, selectTextColor;
    private int tabTextSizeSp = 10;
    private int tabTextSelectSizeSp = 10;
    private int tabImageSizeDp = 22;
    private int tabImageSelectSizeDp = 22;
    //    public static final int GRAVITY_TOP = 0;
//    public static final int GRAVITY_BOTTOM = 1;
    private List<Integer> iconList = new ArrayList<>();

    public enum TabGravity {
        GRAVITY_BOTTOM, GRAVITY_TOP
    }

    public ZXTabViewPager(Context context) {
        this(context, null);
    }

    public ZXTabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        //在构造函数中将xml中定义的布局解析出来
        LayoutInflater.from(context).inflate(R.layout.view_tab_viewpager_layout, this, true);
        tabLayoutTop = findViewById(R.id._tb_zx_layoutTop);
        tabLayoutBottom = findViewById(R.id._tb_zx_layoutBottom);
        tvDividerTop = findViewById(R.id._tv_zx_dividerTop);
        tvDividerBottom = findViewById(R.id._tv_zx_dividerBottom);
        viewPager = findViewById(R.id._vp_zx_pager);
        iconList.clear();
        tabLayout = tabLayoutTop;
        tvDivider = tvDividerTop;
        this.context = context;
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
        myPagerAdapter.addFragment(fragment, title, null);
        return this;
    }

    /**
     * 添加tab 带图片,也可以传入selecter文件
     *
     * @param fragment
     * @param title
     * @return
     */
    public ZXTabViewPager addTab(Fragment fragment, String title, int itemBg) {
        Drawable item_Bg = ContextCompat.getDrawable(context, itemBg);
        myPagerAdapter.addFragment(fragment, title, item_Bg);
        return this;
    }


    /**
     * 设置viewpager是否可以滑动
     *
     * @param canScroll
     * @return
     */
    public ZXTabViewPager setViewpagerCanScroll(boolean canScroll) {
        viewPager.setScanScroll(canScroll);
        return this;
    }

    /**
     * 设置tablayout的背景颜色
     *
     * @return
     */
    public ZXTabViewPager setTablayoutBackgroundColor(int bgColor) {
        tabLayout.setBackgroundColor(bgColor);
        return this;
    }

    /**
     * 设置Tablayout的高度
     *
     * @param heightDp
     * @return
     */
    public ZXTabViewPager setTablayoutHeight(int heightDp) {
        ViewGroup.LayoutParams p = tabLayout.getLayoutParams();
        p.height = ZXSystemUtil.dp2px(heightDp);
        tabLayout.setLayoutParams(p);
        return this;
    }

    /**
     * 设置tab的字体大小
     *
     * @param sizeSP
     * @return
     */
    public ZXTabViewPager setTabTextSize(int sizeSP) {
        tabTextSizeSp = sizeSP;
        tabTextSelectSizeSp = sizeSP;
        return this;
    }

    /**
     * 设置tab的字体大小(包含选中字体大小)
     *
     * @param sizeSP
     * @return
     */
    public ZXTabViewPager setTabTextSize(int sizeSP, int sizeSelectSp) {
        tabTextSizeSp = sizeSP;
        tabTextSelectSizeSp = sizeSelectSp;
        return this;
    }

    /**
     * 设置tab的图片里的大小（前提要设置显示了图片）
     *
     * @param imageSizeDp
     * @return
     */
    public ZXTabViewPager setTabImageSize(int imageSizeDp) {
        tabImageSizeDp = imageSizeDp;
        return this;
    }

    /**
     * 设置tab的图片里的大小（前提要设置显示了图片）
     *
     * @param imageSizeDp
     * @param imageSelcetSizeDp
     * @return
     */
    public ZXTabViewPager setTabImageSize(int imageSizeDp, int imageSelcetSizeDp) {
        tabImageSizeDp = imageSizeDp;
        tabImageSelectSizeDp = imageSelcetSizeDp;
        return this;
    }

    /**
     * 设置tab的数量显示
     *
     * @param positon
     * @param tabNum
     * @return
     */
    public ZXTabViewPager setTabTitleNum(int positon, int tabNum) {
        try {
            TextView tvNum = tabLayout.getTabAt(positon).getCustomView().findViewById(R.id.tv_item_tab_num);
            if (tabNum > 0) {
                tvNum.setVisibility(VISIBLE);
            }else {
                tvNum.setVisibility(GONE);
            }
            tvNum.setText(tabNum + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置tab的数量显示
     *
     * @param positon
     * @param tabNumString
     * @return
     */
    public ZXTabViewPager setTabTitleNum(int positon, String tabNumString) {
        try {
            TextView tvNum = tabLayout.getTabAt(positon).getCustomView().findViewById(R.id.tv_item_tab_num);
            if (tabNumString.length() > 0) {
                tvNum.setVisibility(VISIBLE);
            }else {
                tvNum.setVisibility(GONE);
            }
            tvNum.setText(tabNumString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 完成构建
     */
    public void build() {
        viewPager.setOffscreenPageLimit(myPagerAdapter.fragmentList.size());
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if (myPagerAdapter.normalBgList.get(0) != null) {
            for (int i = 0; i < myPagerAdapter.getCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(R.layout.item_tablayout);
                tab.getCustomView().findViewById(R.id.iv_item_tab).setBackground(myPagerAdapter.normalBgList.get(i));
                ViewGroup.LayoutParams params = tab.getCustomView().findViewById(R.id.iv_item_tab).getLayoutParams();
                params.height = ZXSystemUtil.dp2px(tabImageSizeDp);
                params.width = ZXSystemUtil.dp2px(tabImageSizeDp);
                tab.getCustomView().findViewById(R.id.iv_item_tab).setLayoutParams(params);
                ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setText(myPagerAdapter.getPageTitle(i));

                if (i == 0) {
                    if (selectTextColor != 0) {
                        ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setTextColor(selectTextColor);
                        ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setTextSize(tabTextSelectSizeSp);
                    }
                } else {
                    if (normalTextColor != 0) {
                        ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setTextColor(normalTextColor);
                        ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setTextSize(tabTextSizeSp);
                    }
                }
            }
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    ViewGroup.LayoutParams params = tab.getCustomView().findViewById(R.id.iv_item_tab).getLayoutParams();
                    params.height = ZXSystemUtil.dp2px(tabImageSelectSizeDp);
                    params.width = ZXSystemUtil.dp2px(tabImageSelectSizeDp);
                    tab.getCustomView().findViewById(R.id.iv_item_tab).setLayoutParams(params);
                    tab.getCustomView().findViewById(R.id.iv_item_tab).setSelected(true);
                    tab.getCustomView().findViewById(R.id.tv_item_tab).setSelected(true);
                    if (selectTextColor != 0) {
                        ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setTextColor(selectTextColor);
                        ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setTextSize(tabTextSelectSizeSp);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    ViewGroup.LayoutParams params = tab.getCustomView().findViewById(R.id.iv_item_tab).getLayoutParams();
                    params.height = ZXSystemUtil.dp2px(tabImageSizeDp);
                    params.width = ZXSystemUtil.dp2px(tabImageSizeDp);
                    tab.getCustomView().findViewById(R.id.iv_item_tab).setLayoutParams(params);
                    tab.getCustomView().findViewById(R.id.iv_item_tab).setSelected(false);
                    tab.getCustomView().findViewById(R.id.tv_item_tab).setSelected(false);
                    if (normalTextColor != 0) {
                        ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setTextColor(normalTextColor);
                        ((TextView) tab.getCustomView().findViewById(R.id.tv_item_tab)).setTextSize(tabTextSizeSp);
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
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
            tvDivider = tvDividerTop;
            tabLayoutTop.setVisibility(VISIBLE);
            tabLayoutBottom.setVisibility(GONE);
        } else if (gravity == GRAVITY_BOTTOM) {
            tabLayout = tabLayoutBottom;
            tvDivider = tvDividerBottom;
            tabLayoutBottom.setVisibility(VISIBLE);
            tabLayoutTop.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 展示分隔线
     *
     * @param dividerColor
     * @return
     */
    public ZXTabViewPager showDivider(int dividerColor) {
        tvDivider.setVisibility(VISIBLE);
        tvDivider.setBackgroundColor(dividerColor);
        return this;
    }

    /**
     * 展示分隔线
     *
     * @return
     */
    public ZXTabViewPager showDivider() {
        tvDivider.setVisibility(VISIBLE);
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
        normalTextColor = normalColor;
        selectTextColor = selectedColor;
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
