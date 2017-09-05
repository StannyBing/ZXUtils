package com.stanny.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.stanny.demo.R;
import com.zx.zxutils.views.ZXPhotoView;

public class TabFragment extends Fragment {
    private ZXPhotoView pv_image;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);
        pv_image = (ZXPhotoView) view.findViewById(R.id.pv_image);
        Glide.with(this)
                .load("http://pic.qiantucdn.com/58pic/15/08/88/56G58PICbMp_1024.jpg!/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center")
                .into(pv_image);
        return view;
    }

    public static TabFragment newInstance(String s) {
        TabFragment detail = new TabFragment();
        return detail;
    }
}
