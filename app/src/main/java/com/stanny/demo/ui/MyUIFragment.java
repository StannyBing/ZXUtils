package com.stanny.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanny.demo.R;
import com.stanny.demo.adapter.MainAdapter;
import com.stanny.demo.model.MainEntity;
import com.stanny.demo.ui.widget.BottomSheetActivity;
import com.stanny.demo.ui.widget.BubbleTestActivity;
import com.stanny.demo.ui.widget.ChartActivity;
import com.stanny.demo.ui.widget.ExpandViewActivity;
import com.stanny.demo.ui.widget.PhotoPickerTestActivity;
import com.stanny.demo.ui.widget.RecordTestActivity;
import com.stanny.demo.ui.widget.RecylerDeleteActivity;
import com.stanny.demo.ui.widget.SeekBarTestActivity;
import com.stanny.demo.ui.widget.SlidingActivity;
import com.stanny.demo.ui.widget.SpinnerTestActivity;
import com.stanny.demo.ui.widget.SwipeRefreshRecylerActivity;
import com.stanny.demo.ui.widget.TabLayoutActivity;
import com.stanny.demo.ui.widget.TableActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Create By Xiangb On 2017/12/26
 * 功能：兼容腾讯QMUI库
 */
public class MyUIFragment extends Fragment {

    private RecyclerView rvMain;
    private MainAdapter mainAdapter;
    private List<MainEntity> dataList = new ArrayList<>();

    public static Fragment newInstance() {
        return new MyUIFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main, null, false);
        rvMain = view.findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mainAdapter = new MainAdapter(getActivity(), dataList);
        rvMain.setAdapter(mainAdapter);

        initData();
        return view;
    }

    private void initData() {
        dataList.add(new MainEntity(BottomSheetActivity.class, "ZXBottomSheet", "底部弹出列表", R.mipmap.ui_bottom));
        dataList.add(new MainEntity(ExpandViewActivity.class, "ZXExpandView", "多级菜单", R.mipmap.ui_expand));
        dataList.add(new MainEntity(PhotoPickerTestActivity.class, "ZXPhotoPickerView", "图片选择器", R.mipmap.ui_photopicker));
        dataList.add(new MainEntity(RecylerDeleteActivity.class, "ZXRecylerDelete", "滑动删除", R.mipmap.ui_redelete));
        dataList.add(new MainEntity(SlidingActivity.class, "ZXSlidingNav", "侧边栏", R.mipmap.ui_sliding));
        dataList.add(new MainEntity(TableActivity.class, "ZXTableView", "表格", R.mipmap.ui_table));
        dataList.add(new MainEntity(SwipeRefreshRecylerActivity.class, "ZXSwipeRecyler", "刷新列表", R.mipmap.ui_refresh));
        dataList.add(new MainEntity(ChartActivity.class, "ZXChart", "统计", R.mipmap.ui_chart));
        dataList.add(new MainEntity(TabLayoutActivity.class, "ZXTabViewPager", "viewpager", R.mipmap.ui_tabview));
        dataList.add(new MainEntity(SpinnerTestActivity.class, "ZXSpinner", "下拉框", R.mipmap.ui_spinner));
        dataList.add(new MainEntity(SeekBarTestActivity.class, "ZXSeekBar", "刻度条", R.mipmap.ui_seekbar));
        dataList.add(new MainEntity(BubbleTestActivity.class, "ZXBubbleView", "气泡", R.mipmap.ui_bubble));
        dataList.add(new MainEntity(RecordTestActivity.class, "ZXRecordUtil", "录音", R.mipmap.ui_record));
        mainAdapter.notifyDataSetChanged();
    }
}
