package com.zx.zxutils.views.PhotoPicker.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.IntDef;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.zx.zxutils.views.PhotoPicker.PhotoPickUtils;
import com.zx.zxutils.views.PhotoPicker.listener.OnDeleteListener;
import com.zx.zxutils.views.PhotoPicker.listener.OnPhotoItemClickListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class ZXPhotoPickerView extends FrameLayout {

    private int maxNum = 9;
    private String viewId = "";

    @IntDef({ACTION_SELECT, ACTION_ONLY_SHOW, ACTION_DELETE})

    //Tell the compiler not to store annotation data in the .class file
    @Retention(RetentionPolicy.SOURCE)

    //Declare the NavigationMode annotation
    public @interface MultiPicAction {
    }


    public static final int ACTION_SELECT = 1;//该组件用于图片选择
    public static final int ACTION_ONLY_SHOW = 2;//该组件仅用于图片显示
    public static final int ACTION_DELETE = 3;//显示删除按钮，但不允许图片选择

    private int action;

    private int maxCount;


    RecyclerView recyclerView;
    public PhotoAdapter photoAdapter;
    ArrayList<String> selectedPhotos;

    public ZXPhotoPickerView(Context context) {
        this(context, null, 0);
    }

    public ZXPhotoPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZXPhotoPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        initData(context, attrs);
        initEvent(context, attrs);
        viewId = UUID.randomUUID().toString();
    }

    public String getViewId() {
        return viewId;
    }

    private void initEvent(Context context, AttributeSet attrs) {

    }

    private void initData(Context context, AttributeSet attrs) {

    }

    public ZXPhotoPickerView setMaxPicNum(int maxNum) {
        if (photoAdapter != null) {
            photoAdapter.maxNum = maxNum;
        } else {
            this.maxNum = maxNum;
        }
        return this;
    }

    private void initView(Context context, AttributeSet attrs) {

        recyclerView = new RecyclerView(context, attrs);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        this.addView(recyclerView);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ZXPhotoPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    public void init(Activity context, @MultiPicAction int action, ArrayList<String> photos) {
        init(context, action, photos, null, null);
    }

    public void init(Activity context, @MultiPicAction int action, ArrayList<String> photos, OnPhotoItemClickListener onPhotoItemClickListener) {
        init(context, action, photos, null, onPhotoItemClickListener);
    }

    public void init(Activity context, @MultiPicAction int action, ArrayList<String> photos, OnDeleteListener deleteListener) {
        init(context, action, photos, deleteListener, null);
    }

    public void init(Activity context, @MultiPicAction int action, ArrayList<String> photos, OnDeleteListener deleteListener, OnPhotoItemClickListener onPhotoItemClickListener) {
        this.action = action;
//        if (action == ZXPhotoPickerView.ACTION_ONLY_SHOW) {//当只用作显示图片时,一行显示3张
//            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
//        }

        if (photos == null) {
            selectedPhotos = new ArrayList<>();
        } else {
            selectedPhotos = photos;
        }

        this.action = action;
//        if (photos != null && photos.size() > 0) {
//            selectedPhotos.addAll(photos);
//        }
//        if (action == ZXPhotoPickerView.ACTION_DELETE) {
        photoAdapter = new PhotoAdapter(context, selectedPhotos, deleteListener, onPhotoItemClickListener, viewId);
        photoAdapter.maxNum = maxNum;
// } else {
//            photoAdapter = new PhotoAdapter(context, selectedPhotos);
//        }
        photoAdapter.setAction(action);
        recyclerView.setAdapter(photoAdapter);
        //recyclerView.setLayoutFrozen(true);

    }

    //设置一行最大个数
    public void setLineMaxNum(int num) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(num, OrientationHelper.VERTICAL));
    }

    //区分拍摄方式
    public void setDivisionShootMethod(boolean divisionShootMethod) {
        photoAdapter.divisionShootMethod = divisionShootMethod;
    }

    public void showPics(List<String> paths) {
        if (paths != null) {
            selectedPhotos.clear();
            selectedPhotos.addAll(paths);
            photoAdapter.notifyDataSetChanged();
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || !viewId.equals(data.getStringExtra("id"))) {
            return;
        }
        if (action == ACTION_SELECT) {
            PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
                @Override
                public void onPickSuccess(ArrayList<String> photos) {
                    photoAdapter.refresh(photos, false);
                }

                @Override
                public void onPreviewBack(ArrayList<String> photos) {
                    photoAdapter.refresh(photos, true);
                }

                @Override
                public void onPickFail(String error) {
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
//                    selectedPhotos.clear();
                    photoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onPickCancle() {
                    //Toast.makeText(getContext(),"取消选择",Toast.LENGTH_LONG).show();
                }
            });
        }

    }


    public ArrayList<String> getPhotos() {
        return selectedPhotos;
    }


}
