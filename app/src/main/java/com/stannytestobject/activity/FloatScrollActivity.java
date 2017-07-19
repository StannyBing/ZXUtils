package com.stannytestobject.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.stannytestobject.R;
import com.zx.zxutils.views.ZXFloatScrollView;

public class FloatScrollActivity extends AppCompatActivity {

    private ZXFloatScrollView sv_float;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_scroll);
        sv_float = (ZXFloatScrollView) findViewById(R.id.sv_float);

        sv_float.setTopView(getTextView(1))
                .setBottomView(getTextView(2))
                .setFloatview(getTextView(3));
    }

    public TextView getTextView(int type) {
        final TextView textView = new TextView(this);
        if (type == 1) {
            textView.setText("顶部布局");
            textView.setHeight(200);
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.tomato));
        } else if (type == 2) {
            textView.setText("底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n底部布局\n");
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.orchid));
        } else {
            textView.setText("浮动布局");
            textView.setHeight(100);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView.setText(Math.random() * 100 + "");
                }
            });
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.skyblue));
        }
        return textView;
    }
}
