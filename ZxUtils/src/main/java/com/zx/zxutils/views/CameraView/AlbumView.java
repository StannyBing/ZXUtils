package com.zx.zxutils.views.CameraView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zx.zxutils.R;
import com.zx.zxutils.views.PhotoPicker.entity.PhotoDirectory;
import com.zx.zxutils.views.PhotoPicker.event.OnPhotoClickListener;
import com.zx.zxutils.views.PhotoPicker.utils.MediaStoreHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2018/6/15.
 * 功能：
 */
public class AlbumView extends LinearLayout {

    private Context context;
    private LinearLayout llReturn;
    private LinearLayout llCommit;
    private RecyclerView rvAlbum;
    private AlbumAdapter albumAdapter;
    private AlbumListener albumListener;

    private Bitmap bitmap;

    //所有photos的路径
    private List<PhotoDirectory> directories;

    public AlbumView(Context context) {
        this(context, null);
    }

    public AlbumView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlbumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_camera_album, this);
        llReturn = findViewById(R.id.ll_album_return);
        llCommit = findViewById(R.id.ll_album_commit);
        rvAlbum = findViewById(R.id.rv_album);

        directories = new ArrayList<>();
        albumAdapter = new AlbumAdapter(context, Glide.with(this), directories);

        Bundle mediaStoreArgs = new Bundle();
        MediaStoreHelper.getPhotoDirs((FragmentActivity) context, mediaStoreArgs,
                new MediaStoreHelper.PhotosResultCallback() {
                    @Override
                    public void onResultCallback(List<PhotoDirectory> dirs) {
                        directories.clear();
                        directories.addAll(dirs);
                        albumAdapter.notifyDataSetChanged();
                    }
                });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvAlbum.setLayoutManager(layoutManager);
        rvAlbum.setAdapter(albumAdapter);
        albumAdapter.setOnPhotoClickListener(new OnPhotoClickListener() {
            @Override
            public void onClick(View v, int position, boolean showCamera) {
                if (albumListener != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(directories.get(0).getPhotoPaths().get(position));
                    albumListener.onPicSelect(bitmap);
                }
            }
        });
        llReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                if (albumListener != null) {
                    albumListener.onReturn();
                }
            }
        });
    }

    public void setAlbumListener(AlbumListener albumListener) {
        this.albumListener = albumListener;
    }

    public interface AlbumListener {
        void onPicSelect(Bitmap bitmap);

        void onReturn();
    }

}
