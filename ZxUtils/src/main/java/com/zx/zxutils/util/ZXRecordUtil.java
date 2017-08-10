package com.zx.zxutils.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.zxutils.R;
import com.zx.zxutils.forutil.ZXRecordListener;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.zx.zxutils.ZXApp.getContext;

/**
 * Created by Xiangb on 2017/7/28.
 * 功能：
 */

public class ZXRecordUtil {

    private final int Volume_What_100 = 100;
    private final int Time_What_101 = 101;
    private final int CancelRecordWhat_102 = 102;
    private String mFilePath = null;
    /**
     * 最短录音时间
     **/
    private int MIN_INTERVAL_TIME = 1000;
    /**
     * 最长录音时间
     **/
    private int MAX_INTERVAL_TIME = 1000 * 60;
    private long mStartTime;
    private Dialog mRecordDialog, mPlayDialog;
    private ImageView mIvRecord, mIvPlay;
    private TextView mTvRecord, mTvRecordTime, mTvPlayTime;
    private MediaRecorder mRecorder;
    private ObtainDecibelThread mthread;
    private Handler mVolumeHandler;
    private int CANCLE_LENGTH = -200;// 上滑取消距离
    private View view;
    private Context context;
    private boolean isSetPath = false;
    private int duration;
    private AnimationDrawable animation;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private Handler handler;

    private ZXRecordListener recordListener;

    public ZXRecordUtil(Context context) {
        this.context = context;
    }

    /**
     * 保存路径，为空取默认值
     *
     * @param path
     */
    public void setSavePath(String path) {
        if (!ZXStringUtil.isEmpty(path)) {
            mFilePath = path;
            isSetPath = true;
        } else {
            setDefaultFilePath();
        }

    }

    /****
     * 设置最大时间。15秒-10分钟
     *
     * @param time 单位秒
     */
    public void setMaxIntervalTime(int time) {
        if (time > 15 && time < 10 * 60) {
            MAX_INTERVAL_TIME = time * 1000;
        }
    }

    private void setDefaultFilePath() {
        File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".amr");
        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mFilePath = file.getAbsolutePath();
    }

    //录音监听
    public void setOnRecordListener(ZXRecordListener recordListener) {
        this.recordListener = recordListener;
    }

    private void init() {
        mVolumeHandler = new ShowVolumeHandler();
    }

    int startY = 0;

    public void bindView(View view) {
        init();
        this.view = view;
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        setSavePath(recordListener.onInitPath());
                        startY = (int) event.getY();
                        initDialogAndStartRecord();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mRecordDialog.isShowing()) {
                            int endY = (int) event.getY();
                            if (startY < 0)
                                return true;
                            if (endY - startY < CANCLE_LENGTH) {
                                cancelRecord();
                            } else {
                                finishRecord();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempNowY = (int) event.getY();
                        if (startY < 0)
                            return true;
                        if (tempNowY - startY < CANCLE_LENGTH) {
                            mTvRecord.setText("手指上滑，取消发送");
                        } else {
                            mTvRecord.setText("松开手指，取消发送");
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        cancelRecord();
                        break;
                }

                return true;
            }
        });
    }

    //初始化录音dialog
    private void initDialogAndStartRecord() {
        CANCLE_LENGTH = -view.getMeasuredHeight();
        //
        if (!isSetPath)
            setDefaultFilePath();
        mStartTime = System.currentTimeMillis();
        mRecordDialog = new Dialog(context, R.style.recordbutton_alert_dialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_recordbutton_alert_dialog, null);
        mIvRecord = (ImageView) contentView.findViewById(R.id.zeffect_recordbutton_dialog_imageview);
        mTvRecordTime = (TextView) contentView.findViewById(R.id.zeffect_recordbutton_dialog_time_tv);
        mTvRecord = (TextView) contentView.findViewById(R.id.zeffect_recordbutton_dialog_title_tv);
        mRecordDialog.setContentView(contentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mRecordDialog.setOnDismissListener(onDismiss);
        startRecording();
        mRecordDialog.show();
    }

    private void finishRecord() {
        stopRecording();
        mRecordDialog.dismiss();
        long intervalTime = System.currentTimeMillis() - mStartTime;
        if (intervalTime < MIN_INTERVAL_TIME) {
            ZXToastUtil.showToast("时间太短");
            File file = new File(mFilePath);
            if (file.exists())
                file.delete();
            return;
        }
        File file = new File(mFilePath);
        if (recordListener != null && file.exists()) {
            recordListener.onSuccess(file);
        }
    }

    private void cancelRecord() {
        stopRecording();
        mRecordDialog.dismiss();
        File file = new File(mFilePath);
        if (file.exists())
            file.delete();
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile(mFilePath);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
        mthread = new ObtainDecibelThread();
        mthread.start();

    }

    private void stopRecording() {
        if (mthread != null) {
            mthread.exit();
            mthread = null;
        }
        if (mRecorder != null) {
            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ObtainDecibelThread extends Thread {

        private volatile boolean running = true;

        public void exit() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mRecorder == null || !running) {
                    break;
                }
                if (System.currentTimeMillis() - mStartTime >= MAX_INTERVAL_TIME) {
                    // 如果超过最长录音时间
                    mVolumeHandler.sendEmptyMessage(CancelRecordWhat_102);
                    return;
                }
                //发送时间
                mVolumeHandler.sendEmptyMessage(Time_What_101);
                //
                int x = mRecorder.getMaxAmplitude();
                if (x != 0) {
                    int f = (int) (20 * Math.log(x) / Math.log(10));
                    Message msg = new Message();
                    msg.obj = f;
                    msg.what = Volume_What_100;
                    mVolumeHandler.sendMessage(msg);
                }

            }
        }

    }

    private DialogInterface.OnDismissListener onDismiss = new DialogInterface.OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface dialog) {
            stopRecording();
        }
    };

    class ShowVolumeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Volume_What_100:
                    int tempVolumeMax = (int) msg.obj;
                    setLevel(tempVolumeMax);
                    break;
                case Time_What_101:
                    long nowTime = System.currentTimeMillis();
                    int time = ((int) (nowTime - mStartTime) / 1000);
                    int second = time % 60;
                    int mil = time / 60;
                    if (mil < 10) {
                        if (second < 10)
                            mTvRecordTime.setText("0" + mil + ":0" + second);
                        else
                            mTvRecordTime.setText("0" + mil + ":" + second);
                    } else if (mil >= 10 && mil < 60) {
                        if (second < 10)
                            mTvRecordTime.setText(mil + ":0" + second);
                        else
                            mTvRecordTime.setText(mil + ":" + second);
                    }
                    break;
                case CancelRecordWhat_102:
                    finishRecord();
                    break;
            }
        }
    }

    private void setLevel(int level) {
        if (mIvRecord != null)
            mIvRecord.getDrawable().setLevel(1000 + 6000 * level / 60);
    }

    public void playMedia(File file) {
        if (file != null && file.exists()) {
            playMedia(Uri.fromFile(file));
        }
    }

    public void playMedia(String path) {
        if (path != null && ZXFileUtil.isFileExists(path)) {
            playMedia(Uri.fromFile(new File(path)));
        }

    }

    public void playMedia(Uri uri) {
        mediaPlayer = MediaPlayer.create(context, uri);
        duration = mediaPlayer.getDuration() / 1000;
        initPlayingDialog();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mPlayDialog.dismiss();
                animation.stop();
                timer.cancel();
            }
        });
    }

    //初始化播放dialog
    private void initPlayingDialog() {
        mPlayDialog = new Dialog(context, R.style.recordbutton_alert_dialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_playing_dialog, null);
        mIvPlay = (ImageView) contentView.findViewById(R.id.zeffect_playing_image);
        mTvPlayTime = (TextView) contentView.findViewById(R.id.zdffect_playing_time);
        mPlayDialog.setContentView(contentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mIvPlay.setImageResource(R.drawable.playing_anim);
        animation = (AnimationDrawable) mIvPlay.getDrawable();
        animation.start();
        startPlaying();
        mPlayDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        });
        mPlayDialog.show();
    }


    //开始播放
    private void startPlaying() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mTvPlayTime.setText(msg.obj.toString());
            }
        };
        try {
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (duration == 0) {
                    timer.cancel();
                    return;
                }
                String time = "";
                int secend = duration % 60;
                int minute = duration / 60;
                if (minute < 10) {
                    if (secend < 10)
                        time = "0" + minute + ":0" + secend;
                    else
                        time = "0" + minute + ":" + secend;
                } else if (minute >= 10 && minute < 60) {
                    if (secend < 10)
                        time = minute + ":0" + secend;
                    else
                        time = minute + ":" + secend;
                }
                Message message = new Message();
                message.obj = time;
                handler.sendMessage(message);
                duration--;
            }
        }, 0, 1000);
    }
}
