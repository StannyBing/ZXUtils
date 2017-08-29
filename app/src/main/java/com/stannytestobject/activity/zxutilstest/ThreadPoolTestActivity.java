package com.stannytestobject.activity.zxutilstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stannytestobject.R;
import com.zx.zxutils.other.ThreadPool.ZXRunnable;
import com.zx.zxutils.other.ThreadPool.ZXThreadPool;
import com.zx.zxutils.util.ZXLogUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThreadPoolTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_test);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test_openThread, R.id.btn_test_pauseThread})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_openThread:
                for (int i = 0; i < 20; i++) {
                    final int finalI = i;
                    ZXThreadPool.execute(new ZXRunnable() {
                        @Override
                        public void IRun() {
                            try {
                                Thread.sleep(((int) (Math.random() * 1500)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String threadName = Thread.currentThread().getName();
                            ZXLogUtil.loge(threadName + ":" + finalI);
                        }
                    });
                }
                break;
            case R.id.btn_test_pauseThread:
                if (ZXThreadPool.isPaused()) {
                    ZXThreadPool.resume();
                } else {
                    ZXThreadPool.pause();
                }
                break;
        }
    }
}
