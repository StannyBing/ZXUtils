package com.stannytestobject.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.stannytestobject.R;
import com.zx.zxutils.views.BubbleView.ZXBubbleView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BubbleTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_text_bubble1, R.id.btn_text_bubble2, R.id.btn_text_bubble3, R.id.btn_text_bubble4, R.id.btn_text_bubble5})
    public void onViewClicked(View view) {
        View bubbleView = LayoutInflater.from(BubbleTestActivity.this).inflate(R.layout.layout_popup_view, null);
        ZXBubbleView bubble = new ZXBubbleView(this);
        switch (view.getId()) {
            case R.id.btn_text_bubble1:
//                bubble.setBubbleView(R.layout.layout_popup_view)
                bubble.setBubbleView(bubbleView)
                        .show(view, Gravity.BOTTOM);
                break;
            case R.id.btn_text_bubble2:
                bubble.setBubbleView(R.layout.layout_popup_view).show(view, Gravity.LEFT);
                break;
            case R.id.btn_text_bubble3:
                bubble.setBubbleView(bubbleView).show(view);
                break;
            case R.id.btn_text_bubble4:
                bubble.setBubbleView(bubbleView).show(view, Gravity.TOP);
                break;
            case R.id.btn_text_bubble5:
                bubble.setBubbleView(bubbleView).show(view);
                break;
        }
    }
}
