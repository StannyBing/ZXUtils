package com.zx.zxutils.views.CameraView;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.zx.zxutils.R;
import com.zx.zxutils.views.PhotoPicker.adapter.SelectableAdapter;
import com.zx.zxutils.views.PhotoPicker.entity.Photo;
import com.zx.zxutils.views.PhotoPicker.entity.PhotoDirectory;
import com.zx.zxutils.views.PhotoPicker.event.OnItemCheckListener;
import com.zx.zxutils.views.PhotoPicker.event.OnPhotoClickListener;
import com.zx.zxutils.views.PhotoPicker.utils.MediaStoreHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2018/6/15.
 * 功能：
 */
public class AlbumAdapter extends SelectableAdapter<AlbumAdapter.PhotoViewHolder> {
    private LayoutInflater inflater;
    private RequestManager glide;

    private OnItemCheckListener onItemCheckListener = null;
    private OnPhotoClickListener onPhotoClickListener = null;
    private View.OnClickListener onCameraClickListener = null;

    public final static int ITEM_TYPE_CAMERA = 100;
    public final static int ITEM_TYPE_PHOTO = 101;
    private final static int COL_NUMBER_DEFAULT = 3;

    private boolean hasCamera = false;
    private boolean previewEnable = true;

    private int imageSize;
    private int columnNumber = COL_NUMBER_DEFAULT;


    public AlbumAdapter(Context context, RequestManager requestManager, List<PhotoDirectory> photoDirectories) {
        this.photoDirectories = photoDirectories;
        this.glide = requestManager;
        inflater = LayoutInflater.from(context);
        setColumnNumber(context, columnNumber);
    }

    public AlbumAdapter(Context context, RequestManager requestManager, List<PhotoDirectory> photoDirectories, ArrayList<String> orginalPhotos, int colNum) {
        this(context, requestManager, photoDirectories);
        setColumnNumber(context, colNum);
        setOriginalPhotos(orginalPhotos);
    }

    private void setOriginalPhotos(ArrayList<String> originalPhotos) {
        this.originalPhotos = originalPhotos;
    }

    private void setColumnNumber(Context context, int columnNumber) {
        this.columnNumber = columnNumber;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        imageSize = widthPixels / columnNumber;
    }

    @Override
    public int getItemViewType(int position) {
//        return (showCamera() && position == 0) ? ITEM_TYPE_CAMERA : ITEM_TYPE_PHOTO;
        return ITEM_TYPE_PHOTO;
    }


    @Override
    public AlbumAdapter.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.__picker_item_photo, parent, false);
        AlbumAdapter.PhotoViewHolder holder = new AlbumAdapter.PhotoViewHolder(itemView);
        holder.vSelected.setVisibility(View.GONE);
        holder.ivPhoto.setScaleType(ImageView.ScaleType.CENTER);

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCameraClickListener != null) {
                    onCameraClickListener.onClick(view);
                }
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(final AlbumAdapter.PhotoViewHolder holder, int position) {

        if (getItemViewType(position) == ITEM_TYPE_PHOTO) {

            List<Photo> photos = getCurrentPhotos();
            final Photo photo;

            if (showCamera()) {
                photo = photos.get(position - 1);
            } else {
                photo = photos.get(position);
            }

            glide.load(new File(photo.getPath()))
                    .apply(new RequestOptions()
                            .centerCrop()
                            .dontAnimate()
                            .override(imageSize, imageSize)
                            .placeholder(R.drawable.__picker_default_weixin)
                            .error(R.mipmap.__picker_ic_broken_image_black_48dp)
                    )
                    .thumbnail(0.5f)
                    .into(holder.ivPhoto);

            final boolean isChecked = isSelected(photo);

            holder.vSelected.setSelected(isChecked);
            holder.cover.setSelected(isChecked);

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPhotoClickListener != null) {
                        int pos = holder.getAdapterPosition();
                        if (previewEnable) {
                            onPhotoClickListener.onClick(view, pos, showCamera());
                        } else {
                            holder.vSelected.performClick();
                        }
                    }
                }
            });
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getAdapterPosition();
                    boolean isEnable = true;

                    if (onItemCheckListener != null) {
                        isEnable = onItemCheckListener.OnItemCheck(pos, photo, isChecked,
                                getSelectedPhotos().size());
                    }
                    if (isEnable) {
                        toggleSelection(photo);
                        notifyItemChanged(pos);
                    }
                }
            });

        } else {
            holder.ivPhoto.setImageResource(R.drawable.__picker_camera);
        }
    }


    @Override
    public int getItemCount() {
        int photosCount =
                photoDirectories.size() == 0 ? 0 : getCurrentPhotos().size();
        if (showCamera()) {
            return photosCount + 1;
        }
        return photosCount;
    }


    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vSelected;
        private View cover;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            cover = itemView.findViewById(R.id.cover);
        }
    }


    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }


    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }


    public void setOnCameraClickListener(View.OnClickListener onCameraClickListener) {
        this.onCameraClickListener = onCameraClickListener;
    }


    public ArrayList<String> getSelectedPhotoPaths() {
        ArrayList<String> selectedPhotoPaths = new ArrayList<>(getSelectedItemCount());

        for (Photo photo : selectedPhotos) {
            selectedPhotoPaths.add(photo.getPath());
        }

        return selectedPhotoPaths;
    }


    public void setShowCamera(boolean hasCamera) {
        this.hasCamera = hasCamera;
    }

    public void setPreviewEnable(boolean previewEnable) {
        this.previewEnable = previewEnable;
    }

    public boolean showCamera() {
        return (hasCamera && currentDirectoryIndex == MediaStoreHelper.INDEX_ALL_PHOTOS);
    }

    @Override
    public void onViewRecycled(AlbumAdapter.PhotoViewHolder holder) {
        Glide.with(holder.itemView).clear(holder.ivPhoto);
        super.onViewRecycled(holder);
    }
}
