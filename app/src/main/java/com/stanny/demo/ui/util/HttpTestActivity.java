package com.stanny.demo.ui.util;

import android.os.Bundle;

import com.google.gson.Gson;
import com.stanny.demo.R;
import com.stanny.demo.func.BtnBarView;
import com.stanny.demo.ui.BaseActivity;
import com.zx.zxutils.http.ZXHttpListener;
import com.zx.zxutils.http.ZXHttpTool;
import com.zx.zxutils.util.ZXDialogUtil;
import com.zx.zxutils.util.ZXSystemUtil;
import com.zx.zxutils.util.ZXToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HttpTestActivity extends BaseActivity {

    @BindView(R.id.btnbar_view)
    BtnBarView btnBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        ButterKnife.bind(this);

        btnBarView.addBtn("Get请求")
                .addBtn("Post请求")
                .addBtn("文件上传")
                .addBtn("文件下载")
                .setItemClickListener(this)
                .build();
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0://Get请求
                Map<String, String> map = new HashMap<>();
                map.put("city", "重庆");
                ZXHttpTool.getHttp("https://www.apiopen.top/weatherApi", map, new ZXHttpListener<String>() {
                    @Override
                    public void onResult(String s) {
                        btnBarView.printInfo(s);
                    }

                    @Override
                    public void onError(String msg) {
                        btnBarView.printInfo(msg);
                    }
                });
                break;
            case 1://Post请求
                Map<String, String> map1 = new HashMap<>();
                map1.put("type", "1");
                map1.put("page", "1");
                ZXHttpTool.postHttp("https://www.apiopen.top/satinApi", map1, new ZXHttpListener<TestBean>() {
                    @Override
                    public void onResult(TestBean bean) {
                        btnBarView.printInfo(new Gson().toJson(bean));
                    }

                    @Override
                    public void onError(String msg) {
                        btnBarView.printInfo(msg);
                    }
                });
                break;
            case 2://文件上传
                btnBarView.printInfo("暂无上传对象");
                List<File> files = new ArrayList<>();
                ZXHttpTool.uploadFile("", files, null, new ZXHttpListener<String>() {

                    @Override
                    public void onStart() {
                        ZXDialogUtil.showLoadingDialog(HttpTestActivity.this, "开始上传", 0);
                    }

                    @Override
                    public void onResult(String s) {
                        ZXToastUtil.showToast("上传成功");
                        ZXDialogUtil.dismissLoadingDialog();
                    }

                    @Override
                    public void onProgress(int progress) {
                        ZXDialogUtil.showLoadingDialog(HttpTestActivity.this, "开始上传", progress);
                    }

                    @Override
                    public void onError(String msg) {
                        btnBarView.printInfo(msg);
                    }
                });
                break;
            case 3://文件下载
                File file = new File(ZXSystemUtil.getSDCardPath() + "豌豆荚.apk");
                ZXHttpTool.downloadFile("https://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk", file, new ZXHttpListener<File>() {
                    @Override
                    public void onStart() {
                        ZXDialogUtil.showLoadingDialog(HttpTestActivity.this, "开始下载", 0);
                    }

                    @Override
                    public void onResult(File file) {
                        ZXToastUtil.showToast("下载成功");
                        ZXDialogUtil.dismissLoadingDialog();
                    }

                    @Override
                    public void onProgress(int progress) {
                        ZXDialogUtil.showLoadingDialog(HttpTestActivity.this, "开始下载", progress);
                    }

                    @Override
                    public void onError(String msg) {
                        btnBarView.printInfo(msg);
                    }
                });
                break;
        }
    }

    private class TestBean {
        private int code;
        private String msg;
        private Object data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
