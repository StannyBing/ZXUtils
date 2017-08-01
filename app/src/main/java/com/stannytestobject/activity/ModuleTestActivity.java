package com.stannytestobject.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.stannytestobject.R;
import com.stannytestobject.other.MyService;
import com.stannytestobject.util.ApiData;
import com.zx.zxutils.entity.KeyValueEntity;
import com.zx.zxutils.forutil.ZXUnZipRarListener;
import com.zx.zxutils.http.ZXBaseResult;
import com.zx.zxutils.http.ZXHttpListener;
import com.zx.zxutils.other.ThreadPool.ZXRunnable;
import com.zx.zxutils.other.ThreadPool.ZXThreadPool;
import com.zx.zxutils.other.ZXBroadCastManager;
import com.zx.zxutils.util.ZXAnimUtil;
import com.zx.zxutils.util.ZXBitmapUtil;
import com.zx.zxutils.util.ZXDialogUtil;
import com.zx.zxutils.util.ZXImageLoaderUtil;
import com.zx.zxutils.util.ZXLogUtil;
import com.zx.zxutils.util.ZXNotifyUtil;
import com.zx.zxutils.util.ZXSharedPrefUtil;
import com.zx.zxutils.util.ZXSystemUtil;
import com.zx.zxutils.util.ZXTimeUtil;
import com.zx.zxutils.util.ZXToastUtil;
import com.zx.zxutils.util.ZXUnZipRarUtil;
import com.zx.zxutils.views.BubSeekBar.ZXSeekBar;
import com.zx.zxutils.views.BubbleView.ZXBubbleView;
import com.zx.zxutils.views.PhotoPicker.PhotoPickUtils;
import com.zx.zxutils.views.PhotoPicker.ZXPhotoPreview;
import com.zx.zxutils.views.PhotoPicker.listener.OnPhotoItemClickListener;
import com.zx.zxutils.views.PhotoPicker.widget.ZXPhotoPickerView;
import com.zx.zxutils.views.SwipeBack.ZXSwipeBackHelper;
import com.zx.zxutils.views.ZXSpinner;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ModuleTestActivity extends AppCompatActivity implements View.OnClickListener, ZXHttpListener {

    private ZXPhotoPickerView mprv_photo;
    private Handler handler = new Handler();
    private ArrayAdapter adapter;
    private int num = 0;
    private CoordinatorLayout coordinatorLayout;
    private ZXSpinner mSpinner;
    private ProgressBar downBar, upBar;
    private ZXSeekBar sb_bub;
    private TextView tvmac;
    private ImageView ivLoader;
    private ArrayList<String> photoList = new ArrayList<>();
    private ApiData downloadApi = new ApiData(3);
    private ApiData uploadApi = new ApiData(2);
    private ApiData loginApi1 = new ApiData(1);
    private ApiData loginApi2 = new ApiData(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXSwipeBackHelper.onCreate(this)
                .setSwipeBackEnable(true)
                .setSwipeRelateEnable(true);
        setContentView(R.layout.activity_module_test);
        findViewById(R.id.btn_openSimpleDilog).setOnClickListener(this);
        findViewById(R.id.btn_openProgressDilog).setOnClickListener(this);
        findViewById(R.id.btn_openInfoDilog).setOnClickListener(this);
        findViewById(R.id.btn_openyesnoDilog).setOnClickListener(this);
        findViewById(R.id.btn_openOtherBtnDilog).setOnClickListener(this);
        findViewById(R.id.btn_openCheckListDilog).setOnClickListener(this);
        findViewById(R.id.btn_openListDilog).setOnClickListener(this);
        findViewById(R.id.btn_openViewDilog).setOnClickListener(this);
        findViewById(R.id.btn_openToast).setOnClickListener(this);
        findViewById(R.id.btn_openImgToast).setOnClickListener(this);
        findViewById(R.id.btn_openSnackBar).setOnClickListener(this);
        findViewById(R.id.btn_openLog).setOnClickListener(this);
        findViewById(R.id.btn_openSingleLineNotify).setOnClickListener(this);
        findViewById(R.id.btn_openMessageNotify).setOnClickListener(this);
        findViewById(R.id.btn_openprogressNotify).setOnClickListener(this);
        findViewById(R.id.btn_openCustomNotify).setOnClickListener(this);
        findViewById(R.id.btn_openSharedPrefrences).setOnClickListener(this);
        findViewById(R.id.btn_openbitmap).setOnClickListener(this);
        findViewById(R.id.btn_openDate).setOnClickListener(this);
        findViewById(R.id.btn_openShortCutIcon).setOnClickListener(this);
        findViewById(R.id.btn_openAnimTest).setOnClickListener(this);
        findViewById(R.id.btn_openTabLayout).setOnClickListener(this);
        findViewById(R.id.btn_openChart).setOnClickListener(this);
        findViewById(R.id.btn_openTable).setOnClickListener(this);
        findViewById(R.id.btn_openFloat).setOnClickListener(this);
        findViewById(R.id.btn_openThread).setOnClickListener(this);
        findViewById(R.id.btn_openPause).setOnClickListener(this);
        findViewById(R.id.btn_openSliding).setOnClickListener(this);
        findViewById(R.id.btn_openEdittext).setOnClickListener(this);
        findViewById(R.id.btn_openBroadCast).setOnClickListener(this);
        findViewById(R.id.btn_openSRLayout).setOnClickListener(this);
        findViewById(R.id.btn_testStartService).setOnClickListener(this);
        findViewById(R.id.btn_testStopService).setOnClickListener(this);
        findViewById(R.id.btn_down_pause).setOnClickListener(this);
        findViewById(R.id.btn_down_start).setOnClickListener(this);
        findViewById(R.id.btn_down_cancel).setOnClickListener(this);
        findViewById(R.id.btn_upload_start).setOnClickListener(this);
        findViewById(R.id.btn_upload_cancel).setOnClickListener(this);
        findViewById(R.id.btn_testGet).setOnClickListener(this);
        findViewById(R.id.btn_testPost).setOnClickListener(this);
        findViewById(R.id.btn_testMac1).setOnClickListener(this);
        findViewById(R.id.btn_testMac2).setOnClickListener(this);
        findViewById(R.id.btn_openBubbleLayout).setOnClickListener(this);
        findViewById(R.id.btn_unzip).setOnClickListener(this);

        sb_bub = (ZXSeekBar) findViewById(R.id.sb_bub);
        ivLoader = (ImageView) findViewById(R.id.iv_iamgeloader);
        tvmac = (TextView) findViewById(R.id.tv_mac);
        downBar = (ProgressBar) findViewById(R.id.pb_down);
        upBar = (ProgressBar) findViewById(R.id.pb_upload);
        loginApi1.setHttpListener(this);
        loginApi2.setHttpListener(this);
        uploadApi.setHttpListener(this);
        downloadApi.setHttpListener(this);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coorContent);
        mprv_photo = (ZXPhotoPickerView) findViewById(R.id.mprv_photo);
        mSpinner = (ZXSpinner) findViewById(R.id.sp_myspinner);

        //图片选择器
        photoList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3563933861,850005699&fm=117&gp=0.jpg");
        photoList.add("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=40913571,471421714&fm=58");
        photoList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1735278598,1624226652&fm=117&gp=0.jpg");
        photoList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=468291096,4071036468&fm=117&gp=0.jpg");
        photoList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3390905199,613944351&fm=117&gp=0.jpg");
        mprv_photo.init(this, ZXPhotoPickerView.ACTION_SELECT, photoList, new OnPhotoItemClickListener() {
            @Override
            public void onPhotoItemClick(int position) {
                if (position == photoList.size()) {
                    PhotoPickUtils.startPick(ModuleTestActivity.this, false, 9, photoList);
                } else {
                    ZXPhotoPreview.builder()
                            .setPhotos(photoList)
                            .setAction(ZXPhotoPickerView.ACTION_SELECT)
                            .setCurrentItem(position)
                            .start(ModuleTestActivity.this);
                }
            }
        });
//        mprv_photo.init(this, ZXPhotoPickerView.ACTION_SELECT, null);

        //广播
        ZXBroadCastManager.getInstance(this).getAction("com.zx.default_action", new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.zx.default_action")) {
                    ZXToastUtil.showToast(intent.getStringExtra("boolean"));

                }
            }
        });

        //下拉框spinner
        mSpinner.addData(new KeyValueEntity("0sdfgsdf", "0123"))
                .addData(new KeyValueEntity("qdfasdf", "qwer"))
                .addData(new KeyValueEntity("gdfas", "ghjk"))
//                .setUnderineColor(ContextCompat.getColor(this, R.color.gray))
                .setItemStyle(ZXSpinner.Style.normal)
                .build();
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ZXToastUtil.showToast(mSpinner.getSelectedValue().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //图片加载器
        ZXImageLoaderUtil.displayRound(ivLoader, "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2247692397,1189743173&fm=5");

        //带刻度的seekbar
        sb_bub.setRange(0, 50)//设置范围
                .setProgress(35)//设置当前刻度
                .setSectionMark(10, true)//设置分段
                .setTrackColor(R.color.violet, R.color.__picker_item_selected_cover)//设置选择和未选的颜色
                .setText(10, R.color.tan, 1);//设置刻度值的字体

        //沉浸式状态栏
//        ZXStatusBarCompat.translucentStatusBar(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mprv_photo.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_unzip:
                ZXUnZipRarUtil.unRar(ZXSystemUtil.getSDCardPath() + "rartest.rar", ZXSystemUtil.getSDCardPath(), new ZXUnZipRarListener() {
                    @Override
                    public void onStart() {
                        ZXLogUtil.loge("解压开始");
                    }

                    @Override
                    public void onPregress(int progress) {
                        ZXLogUtil.loge("解压：" + progress);
                    }

                    @Override
                    public void onComplete(String outputPath) {
                        ZXLogUtil.loge("解压结束");
                    }

                    @Override
                    public void onError(String message) {
                        ZXLogUtil.loge("解压错误" + message);
                    }
                });
                break;
            case R.id.btn_openBubbleLayout://弹出气泡
                View bubbleView = LayoutInflater.from(ModuleTestActivity.this).inflate(R.layout.layout_popup_view, null);
                ZXBubbleView bubble = new ZXBubbleView(this);
//                bubble.setBubbleView(R.layout.layout_popup_view)
                bubble.setBubbleView(bubbleView)
                        .show(v, Gravity.BOTTOM);
                break;
            case R.id.btn_testMac1:
                tvmac.setText(getMacAddress(this));
                break;
            case R.id.btn_testMac2:
                tvmac.setText(getMachineHardwareAddress().toLowerCase());
                break;
            case R.id.btn_testPost://post测试
                loginApi2.loadData("login");
                break;
            case R.id.btn_testGet://get测试
                loginApi1.loadData("login");
                break;
            case R.id.btn_upload_start://上传开始
                File file = new File(ZXSystemUtil.getSDCardPath() + "GAMarket.jar");
//                if (!file.exists()) {
//                    return;
//                }
                uploadApi.loadData(file);
                break;
            case R.id.btn_upload_cancel://上传取消
                uploadApi.cancel();
                break;
            case R.id.btn_down_cancel://下载取消
                downloadApi.cancel();
                break;
            case R.id.btn_down_pause://下载暂停
                downloadApi.pauseDownload();
                break;
            case R.id.btn_down_start://下载开始
                downloadApi.loadData(ZXSystemUtil.getSDCardPath() + "APIdata.jar", true);
                break;
            case R.id.btn_testStopService://停止服务
                Intent serviceIntent1 = new Intent(this, MyService.class);
                serviceIntent1.putExtra("info", "infoinfoinfofinfofinfo");
                stopService(serviceIntent1);
                break;
            case R.id.btn_testStartService://开启服务
                Intent serviceIntent2 = new Intent(this, MyService.class);
                serviceIntent2.putExtra("info", "infoinfoinfofinfofinfo");
                startService(serviceIntent2);
                break;
            case R.id.btn_openSRLayout://打开下拉刷新activity
                startActivity(new Intent(this, SwipeRefreshRecylerActivity.class));
                break;
            case R.id.btn_openBroadCast://发送广播
                ZXBroadCastManager.getInstance(this)
                        .setAction("com.zx.default_action")
                        .addExtra("string", "字符串类型")
                        .addExtra("boolean", "布尔类型")
                        .send();
                break;
            case R.id.btn_openEdittext://打开滑动删除recylerview
                startActivity(new Intent(this, RecylerDeleteActivity.class));
                break;
            case R.id.btn_openSliding://打开侧边栏activity
                startActivity(new Intent(this, SlidingActivity.class));
                break;
            case R.id.btn_openPause://线程暂停
                if (ZXThreadPool.isPaused()) {
                    ZXThreadPool.resume();
                } else {
                    ZXThreadPool.pause();
                }
                break;
            case R.id.btn_openThread://线程开启
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
            case R.id.btn_openFloat://顶部悬浮scrollview
                startActivity(new Intent(this, FloatScrollActivity.class));
                break;
            case R.id.btn_openTable://打开tableactivity
                startActivity(new Intent(this, TableActivity.class));
                break;
            case R.id.btn_openChart://打开表格
                startActivity(new Intent(this, ChartActivity.class));
                break;
            case R.id.btn_openTabLayout://打开tablayout+viewpager
                startActivity(new Intent(this, TabLayoutActivity.class));
                break;
            case R.id.btn_openAnimTest://动画测试
                ((ImageView) findViewById(R.id.btn_openAnimTest)).startAnimation(ZXAnimUtil.getRotateAnimationByCenter(2000, null));
                break;
            case R.id.btn_openShortCutIcon://打电话给10086
                ZXSystemUtil.callTo(this, "10086");
                break;
            case R.id.btn_openDate://时间测试
                ZXLogUtil.loge(ZXTimeUtil.getTimeDifference(System.currentTimeMillis() - 200000000, System.currentTimeMillis()));
                break;
            case R.id.btn_openbitmap://圆形边框bitmap
                Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.test);
                Bitmap bit = ZXBitmapUtil.drawableToBitmap(drawable);
                bit = ZXBitmapUtil.getRoundBitmap(bit, 40);
                ImageView ima = new ImageView(this);
                ima.setImageBitmap(bit);
                ZXDialogUtil.showCustomViewDialog(this, "测试", ima, null);
                break;
            case R.id.btn_openSharedPrefrences://共享参数
                ZXSharedPrefUtil sharedPrefUtil = new ZXSharedPrefUtil();
                sharedPrefUtil.putString("name", "向冰");
                ZXLogUtil.loge("name:" + sharedPrefUtil.getString("name"));
                ZXLogUtil.loge("是否包含：" + sharedPrefUtil.contains("name"));
                sharedPrefUtil.remove("name");
                ZXLogUtil.loge("name:" + sharedPrefUtil.getString("name"));
                ZXLogUtil.loge("是否包含：" + sharedPrefUtil.contains("name"));
                break;
            case R.id.btn_openCustomNotify://notification
                Intent intentc = new Intent(this, ModuleTestActivity.class);
                RemoteViews remoteViews = new RemoteViews(getPackageName(),
                        R.layout.layout_custom);
                remoteViews.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
                remoteViews.setTextViewText(R.id.title, "垃圾安装包太多");
                remoteViews.setTextViewText(R.id.text, "3 个无用安装包，清理释放的空间");
                ZXNotifyUtil.showCustomNotify(intentc, remoteViews);
                break;
            case R.id.btn_openprogressNotify://notification
                Intent intentp = new Intent(this, ModuleTestActivity.class);
                ZXNotifyUtil.showProgressNotify(intentp, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容", 60);
                break;
            case R.id.btn_openMessageNotify://notification
                Intent intentbi = new Intent(this, ModuleTestActivity.class);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                ZXNotifyUtil.showBigImgNotify(intentbi, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容", bitmap);
                break;
            case R.id.btn_openSingleLineNotify://notification
                Intent intent = new Intent(this, ModuleTestActivity.class);
                ZXNotifyUtil.showMulLineNotify(intent, R.mipmap.ic_launcher, "标题", "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
                break;
            case R.id.btn_openLog://打log
//                ZXLogUtil.closePrintLog();
                ZXLogUtil.loge("123123");
                break;
            case R.id.btn_openSnackBar://sanckbar的使用
                ZXToastUtil.showSnackBar(coordinatorLayout, "snackbar", "do", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ZXToastUtil.showToast("123");
                    }
                });
                break;
            case R.id.btn_openSimpleDilog://简单dialog
//                if (ZXDialogUtil.isLoadingDialogShow()) {
//                    ZXDialogUtil.dismissLoadingDialog();
//                } else {
//                    ZXDialogUtil.showLoadingDialog(this, "正在加载中");
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            ZXDialogUtil.dismissLoadingDialog();
//                        }
//                    }, 2000);
//                }
                ZXDialogUtil.showLoadingDialog(this, "无进度条");
                ZXDialogUtil.showLoadingDialog(this, "有进度条", 30);
                break;
            case R.id.btn_openProgressDilog://进度条dialog
                if (ZXDialogUtil.isLoadingDialogShow()) {
                    ZXDialogUtil.dismissLoadingDialog();
                } else {
                    for (int i = 0; i < 10; i++) {
                        ZXDialogUtil.showLoadingDialog(ModuleTestActivity.this, "正在加载中", num * 10);
                        num++;
                    }

                }
                break;
            case R.id.btn_openInfoDilog://信息dialog
                ZXDialogUtil.showInfoDialog(this, "提示", "确定么", null);
                break;
            case R.id.btn_openyesnoDilog://确认dialog
                ZXDialogUtil.showYesNoDialog(this, "提示", "确定么", null);
                break;
            case R.id.btn_openOtherBtnDilog://带第三按钮的dialog
                ZXDialogUtil.showWithOtherBtnDialog(this, "提示", "are you sure", "查看详情", null, null);
                break;
            case R.id.btn_openCheckListDilog://列表diaog
                ZXDialogUtil.showCheckListDialog(this, "提示", new String[]{"11", "22", "33"}, new boolean[]{false, true, true}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        ZXDialogUtil.showInfoDialog(ModuleTestActivity.this, "提示", "确定么", null);
                        Toast.makeText(ModuleTestActivity.this, "第" + which + "个" + (isChecked ? "被选中" : "被取消"), Toast.LENGTH_SHORT).show();
                    }
                }, null);
                break;
            case R.id.btn_openListDilog://列表dialog
                ZXDialogUtil.showListDialog(this, "提示", new String[]{"确认", "缺不缺人", "的防辐射服", "还是会", "哈哈哈"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ModuleTestActivity.this, "第" + which + "个", Toast.LENGTH_SHORT).show();
                    }
                }, null);
                break;
            case R.id.btn_openViewDilog://整个view的dialog
                ImageView iv = new ImageView(this);
                iv.setBackground(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ModuleTestActivity.this, "点击viewl", Toast.LENGTH_SHORT).show();
                    }
                });
                ZXDialogUtil.showCustomViewDialog(this, "提示", iv, null);
                break;
            case R.id.btn_openToast://toast
                ZXToastUtil.showToast("123456");
                break;
            case R.id.btn_openImgToast://带图片的toast
                ZXToastUtil.showImgToast("图片toast", ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
                break;
            default:
                break;
        }
    }

    @Override
    public void OnHttpStart(int apiType) {

    }

    @Override
    public void OnHttpError(int apiType, String errorMsg) {
        ZXToastUtil.showToast(errorMsg);
    }

    @Override
    public void OnHttpSuccess(int apiType, ZXBaseResult responseInfo) {
        ZXToastUtil.showToast(responseInfo.getEntry().toString());
    }

    @Override
    public void OnHttpProgress(int apiType, int progress) {
        if (apiType == 2) {
            Log.e("progress", progress + "");
            upBar.setProgress(progress);
        } else {
            downBar.setProgress(progress);
        }
    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    private static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = null;
        NetworkInterface iF = null;
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if (hardWareAddress == null)
                    continue;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    private static String getMacAddress(Context c) {
        WifiManager wifi = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return "";
        }
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress() == null ? "" : info.getMacAddress();
    }
}
