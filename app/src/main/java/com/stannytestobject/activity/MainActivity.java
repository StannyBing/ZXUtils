package com.stannytestobject.activity;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zx.zxutils.util.ZXPermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    List<Class<?>> classList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    String size = 18 + "cm";

    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.CHANGE_WIFI_STATE
            , Manifest.permission.CALL_PHONE
            , Manifest.permission.INTERNET
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.ACCESS_WIFI_STATE
            , Manifest.permission.ACCESS_NETWORK_STATE
            , Manifest.permission.RECEIVE_BOOT_COMPLETED
            , Manifest.permission.RESTART_PACKAGES
            , Manifest.permission.BROADCAST_STICKY
            , Manifest.permission.WRITE_SETTINGS
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.WAKE_LOCK
            , Manifest.permission.KILL_BACKGROUND_PROCESSES
            , Manifest.permission.READ_LOGS
            , Manifest.permission.VIBRATE
            , Manifest.permission.BLUETOOTH
            , Manifest.permission.BATTERY_STATS
            , Manifest.permission.GET_TASKS
            , Manifest.permission.CAMERA
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addActivity(ModuleTestActivity.class, "依赖库相关测试");
        addActivity(CodeTestActivity.class, "个人代码测试");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titleList);
        setListAdapter(adapter);
        if (!ZXPermissionUtil.checkPermissionsByArray(permissions)) {
            ZXPermissionUtil.requestPermissionsByArray(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void addActivity(Class<?> classFile, String className) {
        classList.add(classFile);
        titleList.add(className);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(this, classList.get(position)));
    }
}
