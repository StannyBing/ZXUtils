package com.stanny.demo.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stanny.demo.R;
import com.stanny.demo.adapter.MainAdapter;
import com.stanny.demo.model.MainEntity;
import com.stanny.demo.ui.util.AnimationTestActivity;
import com.stanny.demo.ui.util.BitmapTestActivity;
import com.stanny.demo.ui.util.BroadCastTestActivity;
import com.stanny.demo.ui.util.ClipboardTestActivity;
import com.stanny.demo.ui.util.DialogTestActivity;
import com.stanny.demo.ui.util.FormatCheckTestActivity;
import com.stanny.demo.ui.util.FragmentTestActivity;
import com.stanny.demo.ui.util.HttpTestActivity;
import com.stanny.demo.ui.util.ImageLoaderTestActivity;
import com.stanny.demo.ui.util.LightTestActivity;
import com.stanny.demo.ui.util.LocationTestActivity;
import com.stanny.demo.ui.util.LogTestActivity;
import com.stanny.demo.ui.util.MD5TestActivity;
import com.stanny.demo.ui.util.NetWorkTestActivity;
import com.stanny.demo.ui.util.NotifyTestActivity;
import com.stanny.demo.ui.util.QuickAdapterTestActivity;
import com.stanny.demo.ui.util.ScreenTestActivity;
import com.stanny.demo.ui.util.SharedPrefTestActivity;
import com.stanny.demo.ui.util.ThreadPoolTestActivity;
import com.stanny.demo.ui.util.TimeTestActivity;
import com.stanny.demo.ui.util.ToastTestActivity;
import com.stanny.demo.ui.util.UnZipOrRarTestActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2018/1/19.
 * 功能：
 */

public class MyUtilFragment extends Fragment {

    private RecyclerView rvMain;
    private MainAdapter mainAdapter;
    private List<MainEntity> dataList = new ArrayList<>();

    public static Fragment newInstance() {
        return new MyUtilFragment();
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
        dataList.add(new MainEntity(HttpTestActivity.class, "ZXHttpTool", "网络请求相关", R.mipmap.util_http));
        dataList.add(new MainEntity(LocationTestActivity.class, "ZXLocationUtil", "定位相关工具", R.mipmap.util_location));
        dataList.add(new MainEntity(FragmentTestActivity.class, "ZXFragmentUtil", "Fragment工具", R.mipmap.util_fragment));
        dataList.add(new MainEntity(ClipboardTestActivity.class, "ZXClipboardUtil", "剪切板工具", R.mipmap.util_clipboard));
        dataList.add(new MainEntity(MD5TestActivity.class, "ZXMD5Util", "加密相关（不仅MD5）", R.mipmap.util_md5));
        dataList.add(new MainEntity(LightTestActivity.class, "ZXLightUtil", "亮度相关工具", R.mipmap.util_light));
        dataList.add(new MainEntity(QuickAdapterTestActivity.class, "ZXQuickAdapter", "快速Recycleview适配器", R.mipmap.util_adapter));
        dataList.add(new MainEntity(TimeTestActivity.class, "ZXTimeUtil", "时间工具", R.mipmap.util_time));
        dataList.add(new MainEntity(SharedPrefTestActivity.class, "ZXSharedPrefUtil", "共享存储工具", R.mipmap.util_sharedpref));
        dataList.add(new MainEntity(ScreenTestActivity.class, "ZXScreenUtil", "屏幕工具", R.mipmap.util_screen));
        dataList.add(new MainEntity(NetWorkTestActivity.class, "ZXNetWorkUtil", "网络相关", R.mipmap.util_network));
        dataList.add(new MainEntity(FormatCheckTestActivity.class, "ZXFormatCheckUtil", "格式检测", R.mipmap.util_format));
        dataList.add(new MainEntity(DialogTestActivity.class, "ZXDialogUtil", "弹框", R.mipmap.util_dialog));
        dataList.add(new MainEntity(ToastTestActivity.class, "ZXToastUtil", "吐司", R.mipmap.util_toast));
        dataList.add(new MainEntity(NotifyTestActivity.class, "ZXNotifyUtil", "提示", R.mipmap.util_notify));
        dataList.add(new MainEntity(BroadCastTestActivity.class, "ZXBroadCastUtil", "广播", R.mipmap.util_broad));
        dataList.add(new MainEntity(UnZipOrRarTestActivity.class, "ZXUnZipOrRarUtil", "解压", R.mipmap.util_rar));
        dataList.add(new MainEntity(BitmapTestActivity.class, "ZXBitmapUtil", "bitmap", R.mipmap.util_bitmap));
        dataList.add(new MainEntity(AnimationTestActivity.class, "ZXAnimUtil", "动画", R.mipmap.util_anim));
        dataList.add(new MainEntity(LogTestActivity.class, "ZXLogUtil", "log", R.mipmap.util_log));
        dataList.add(new MainEntity(ImageLoaderTestActivity.class, "ZXImageLoaderUtil", "图片加载器", R.mipmap.util_image));
        dataList.add(new MainEntity(ThreadPoolTestActivity.class, "ZXThreadPool", "线程池", R.mipmap.util_thread));
        mainAdapter.notifyDataSetChanged();
    }

}
