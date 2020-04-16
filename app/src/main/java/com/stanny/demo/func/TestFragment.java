package com.stanny.demo.func;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stanny.demo.R;

public class TestFragment extends Fragment {


    TextView textView;

    public static TestFragment newInstance(@ColorRes int color, String text) {
        Bundle args = new Bundle();
        args.putInt("color", color);
        args.putString("text", text);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        textView = view.findViewById(R.id.test_text);
        textView.setText(getArguments().getString("text"));
        textView.setBackgroundResource(getArguments().getInt("color"));
        return view;
    }

}
