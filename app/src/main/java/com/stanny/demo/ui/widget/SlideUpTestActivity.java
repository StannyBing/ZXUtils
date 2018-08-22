package com.stanny.demo.ui.widget;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.views.SlideUp.ZXSlideUp;
import com.zx.zxutils.views.SlideUp.ZXSlideUpBuilder;

public class SlideUpTestActivity extends BaseActivity implements ZXSlideUp.Listener.Events {

    private CardView cardView;
    private FrameLayout flBg;

    private ZXSlideUp zxSlideUp;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_up_test);
        cardView = findViewById(R.id.card_slide);
        fab = findViewById(R.id.fab_slide);
        flBg = findViewById(R.id.fm_slide_bg);

        resetSlideUp(Gravity.BOTTOM);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zxSlideUp.isVisible()) {
                    zxSlideUp.hide();
                }else {
                    zxSlideUp.show();
                }
            }
        });
    }

    private void resetSlideUp(int gravity) {
        zxSlideUp = new ZXSlideUpBuilder(cardView)
                .withListeners(this)
                .withStartGravity(gravity)
                .withGesturesEnabled(true)
                .withSlideFromOtherView(flBg)
                .withTouchableAreaDp(200)//可触碰滑动的长度
                .withLoggingEnabled(true)
                .withStartState(ZXSlideUp.State.HIDDEN)
                .build();
    }

    @Override
    public void onSlide(float percent) {
        flBg.setAlpha(1 - (percent / 100));
    }

    @Override
    public void onVisibilityChanged(int visibility) {
//        if (visibility == View.GONE) {
//            fab.show();
//        } else {
//            fab.hide();
//        }
    }
}
