package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.stanny.demo.R;
import com.stanny.demo.ui.BaseActivity;
import com.stanny.demo.view.BtnBarView;
import com.zx.zxutils.other.ThreadPool.ZXRunnable;
import com.zx.zxutils.other.ThreadPool.ZXThreadPool;
import com.zx.zxutils.util.ZXLogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThreadPoolTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        ButterKnife.bind(this);

        btnBarView.addBtn("开启线程")
                .addBtn("暂停线程")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://开启线程
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
            case 1://暂停线程
                if (ZXThreadPool.isPaused()) {
                    ZXThreadPool.resume();
                } else {
                    ZXThreadPool.pause();
                }
                break;
        }
    }
}
